package uk.ac.aston.cs3mdd.mealplanner.ui.meals;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class MealViewHolder extends RecyclerView.ViewHolder {
    TextView titleTextView;
    ImageView imageView;
    TextView vegan;
    TextView vegetarian;

    public MealViewHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        imageView = itemView.findViewById(R.id.imageView);
        vegan = itemView.findViewById(R.id.vegan);
        vegetarian = itemView.findViewById(R.id.vegetarian);
    }
}
