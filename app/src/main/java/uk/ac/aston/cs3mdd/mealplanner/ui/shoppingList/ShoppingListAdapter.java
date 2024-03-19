package uk.ac.aston.cs3mdd.mealplanner.ui.shoppingList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<Ingredients> shoppingItemList;
    private Context context;

    // Constructor and methods

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredients item = shoppingItemList.get(position);
        holder.ingredientNameTextView.setText(item.getIngredientName());
        String valueWithSpace = item.getValue() + " " + item.getUnit(); // Concatenate value and unit with a space
        holder.valueTextView.setText(valueWithSpace);

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

        // Set message with ingredient details and associated meal titles
        String message = "Ingredient Name: " + ingredient.getIngredientName() + "\n"
                + "Total Value: " + ingredient.getValue() + " " + ingredient.getUnit() + "\n\n"
                + "Meals:\n" + mealsString;
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}