package uk.ac.aston.cs3mdd.mealplanner.ui.meals;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class MealViewHolder extends RecyclerView.ViewHolder {
    TextView mealID;
    TextView title;
    TextView mealType;
    ImageView imageView;
    TextView vegan;
    TextView vegetarian;
    TextView cookingTime;
    TextView recipe;
    TextView ingredients;
    TextView serves;
    TextView favouriteCount;
    TextView calories;


    public MealViewHolder(View itemView) {
        super(itemView);
        //mealID = itemView.findViewById(R.id.mealIDTextView);
        title = itemView.findViewById(R.id.titleTextView);
        mealType = itemView.findViewById(R.id.mealTypeTextView);
        imageView = itemView.findViewById(R.id.imageView);
        vegan = itemView.findViewById(R.id.vegan);
        vegetarian = itemView.findViewById(R.id.vegetarian);
        cookingTime = itemView.findViewById(R.id.cookingTimeTextView);
        recipe = itemView.findViewById(R.id.mealInformationTextView);
        ingredients = itemView.findViewById(R.id.mealInformationTextView);
        serves = itemView.findViewById(R.id.servesTextView);
        favouriteCount = itemView.findViewById(R.id.favouriteCount);
        calories = itemView.findViewById(R.id.caloriesTextView);
    }
}
