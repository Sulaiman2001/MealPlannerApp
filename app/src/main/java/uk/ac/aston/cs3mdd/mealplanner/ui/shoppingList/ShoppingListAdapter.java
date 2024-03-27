package uk.ac.aston.cs3mdd.mealplanner.ui.shoppingList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
    private SharedPreferences sharedPreferences;

    public interface OnCheckboxClickListener {
        void onCheckboxClick(int position, boolean isChecked);
    }

    public void setOnCheckboxClickListener(OnCheckboxClickListener listener) {
        this.listener = listener;
    }

    public ShoppingListAdapter(List<Ingredients> shoppingItemList, Context context) {
        this.shoppingItemList = shoppingItemList;
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("checkbox_state", Context.MODE_PRIVATE);
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

        // Set the checked state of the checkbox
        boolean isChecked = sharedPreferences.getBoolean("checkbox_" + position, false);
        holder.checkBox.setChecked(isChecked);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the checked state of the current item
                item.setChecked(isChecked);
                // Save the checkbox state to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("checkbox_" + position, isChecked);
                editor.apply();
                // Notify the listener if set
                if (listener != null) {
                    listener.onCheckboxClick(position, isChecked);
                }
            }
        });

        // Set click listener for details button
        holder.detailsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIngredientDetailsDialog(item); // Pass ingredient object to dialog
            }
        });
    }


    @Override
    public int getItemCount() {
        return shoppingItemList.size();
    }


    private void showIngredientDetailsDialog(Ingredients ingredient) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ingredient Details");

        // Create a string with the list of meal titles
        StringBuilder mealsStringBuilder = new StringBuilder();
        for (String meal : ingredient.getMeals()) {
            mealsStringBuilder.append("- ").append(meal).append("\n");
        }
        String mealsString = mealsStringBuilder.toString().trim();
        // Check if the ingredient is custom
        if (ingredient.isCustom()) {
            String message = "Custom ingredient Name: " + ingredient.getIngredientName() + "\n"
                    + "Note for the meal " + ingredient.getUnit() + "\n\n";
            builder.setMessage(message);
        } else {
            String message = "Ingredient Name: " + ingredient.getIngredientName() + "\n"
                    + "Total required for the meal: " + ingredient.getValue() + " " + ingredient.getUnit() + "\n\n"
                    + "Meals this ingredient is used in:\n" + mealsString;
            builder.setMessage(message);

        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}