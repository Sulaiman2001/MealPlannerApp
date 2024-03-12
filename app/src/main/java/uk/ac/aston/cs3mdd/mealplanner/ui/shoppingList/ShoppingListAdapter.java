package uk.ac.aston.cs3mdd.mealplanner.ui.shoppingList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<Ingredients> shoppingItemList;

    // Constructor and methods

    public ShoppingListAdapter(List<Ingredients> shoppingItemList) {
        this.shoppingItemList = shoppingItemList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientNameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.ingredientNameTextView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredients item = shoppingItemList.get(position);
        holder.ingredientNameTextView.setText(item.getIngredientName());
    }

    @Override
    public int getItemCount() {
        return shoppingItemList.size();
    }
}

