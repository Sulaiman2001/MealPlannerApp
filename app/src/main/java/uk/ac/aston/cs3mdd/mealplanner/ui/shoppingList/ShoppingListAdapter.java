package uk.ac.aston.cs3mdd.mealplanner.ui.shoppingList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<Ingredients> shoppingItemList;
    private Context context;
    private OnCheckboxClickListener listener;

    public interface OnCheckboxClickListener {
        void onCheckboxClick(int position, boolean isChecked);
    }

    public void setOnCheckboxClickListener(OnCheckboxClickListener listener) {
        this.listener = listener;
    }

    public ShoppingListAdapter(List<Ingredients> shoppingItemList, Context context) {
        this.shoppingItemList = shoppingItemList;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientNameTextView;
        TextView valueTextView;
        CheckBox checkBox;
        FloatingActionButton detailsFAB;

        ViewHolder(View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.ingredientNameTextView);
            valueTextView = itemView.findViewById(R.id.valueTextView);
            checkBox = itemView.findViewById(R.id.checkBox);
            detailsFAB = itemView.findViewById(R.id.details);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Ingredients item = shoppingItemList.get(position);
        holder.ingredientNameTextView.setText(item.getIngredientName());
        String valueWithSpace = item.getValue() + " " + item.getUnit();
        holder.valueTextView.setText(valueWithSpace);

        // Use the actual ingredient ID from the Ingredients object
        Integer ingredientID = item.getIngredientID();

        holder.checkBox.setOnCheckedChangeListener(null); // Reset listener to avoid recycled view issues
        holder.checkBox.setChecked(item.isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.onCheckboxClick(position, isChecked);
                }
            }
        });

        // Set click listener for details button
        holder.detailsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to show ingredient details dialog
                // You can call a method in the fragment to handle this
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingItemList.size();
    }
}
