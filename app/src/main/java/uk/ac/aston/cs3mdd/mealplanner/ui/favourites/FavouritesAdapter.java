package uk.ac.aston.cs3mdd.mealplanner.ui.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.ac.aston.cs3mdd.mealplanner.R;
import uk.ac.aston.cs3mdd.mealplanner.ui.meals.Meal;
import uk.ac.aston.cs3mdd.mealplanner.ui.meals.MealsAdapter;

// FavouritesAdapter.java
public class FavouritesAdapter extends RecyclerView.Adapter<MealsAdapter.MealViewHolder> {
    private List<Meal> meals;
    private FragmentManager fragmentManager;

    public FavouritesAdapter(List<Meal> meals, FragmentManager fragmentManager) {
        this.meals = meals;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MealsAdapter.MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_meal_item, parent, false);
        return new MealsAdapter.MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.MealViewHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.cookingTime.setText(meal.getCookingTime());

        // Set title
        holder.title.setText(meal.getTitle());

        if ("1".equals(meal.getIsVegan())) {
            holder.vegan.setVisibility(View.VISIBLE);
        } else {
            holder.vegan.setVisibility(View.GONE);
        }

        if (meal.getIsVegan()){
            holder.vegan.setVisibility(View.VISIBLE);
        } else {
            holder.vegan.setVisibility(View.GONE);
        }

        if (meal.getIsVegetarian()){
            holder.vegetarian.setVisibility(View.VISIBLE);
        } else {
            holder.vegetarian.setVisibility(View.GONE);
        }

        Picasso.get().load(meal.getImagePath()).into(holder.imageView);

        // Set onClickListener for the mealDetails button
        holder.mealDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Meal clickedMeal = meals.get(position);
                    openMealDetailsFragment(clickedMeal, view);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealID;
        TextView title;
        ImageView imageView;
        TextView vegan;
        TextView vegetarian;
        Button mealDetails;
        TextView cookingTime;
        TextView recipe;
        TextView ingredients;
        TextView serves;


        public MealViewHolder(View itemView) {
            super(itemView);
            //mealID = itemView.findViewById(R.id.mealIDTextView);
            title = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.imageView);
            vegan = itemView.findViewById(R.id.vegan);
            vegetarian = itemView.findViewById(R.id.vegetarian);
            cookingTime = itemView.findViewById(R.id.cookingTimeTextView);
            mealDetails = itemView.findViewById(R.id.mealDetails);
            recipe = itemView.findViewById(R.id.mealInformationTextView);
            ingredients = itemView.findViewById(R.id.mealInformationTextView);
            serves = itemView.findViewById(R.id.servesTextView);
        }
    }

    private void openMealDetailsFragment(Meal meal, View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("mealID", meal.getMealID());
        bundle.putString("title", meal.getTitle());
        bundle.putString("imagePath", meal.getImagePath());
        bundle.putBoolean("isVegetarian", meal.getIsVegetarian());
        bundle.putBoolean("isVegan", meal.getIsVegan());
        bundle.putString("time_to_cook", meal.getCookingTime());
        bundle.putString("recipe", meal.getRecipe());
        bundle.putString("ingredients", meal.getIngredients());
        bundle.putInt("serves", meal.getServes());

        Navigation.findNavController(view).navigate(R.id.action_favourites_to_mealDetails, bundle);

    }
}
