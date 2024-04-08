package uk.ac.aston.cs3mdd.mealplanner.ui.meals;

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

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealViewHolder> {
    private List<Meal> meals;
    private FragmentManager fragmentManager;
    private List<Meal> searchedMeals;

    public MealsAdapter(List<Meal> meals, FragmentManager fragmentManager) {
        this.meals = meals;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);

        // Set cooking time
        holder.cookingTime.setText(formatCookingTime(meal.getCookingTime()));

        // Set title
        holder.title.setText(meal.getTitle());

        holder.favouriteCount.setText(String.valueOf(meal.getFavouriteCount()));

        // Check if the meal is vegan and update the visibility of the vegan TextView accordingly
        if (meal.getIsVegan()) {
            holder.vegan.setVisibility(View.VISIBLE);
        } else {
            holder.vegan.setVisibility(View.GONE);
        }

        // Check if the meal is vegetarian and update the visibility of the vegetarian TextView accordingly
        if (meal.getIsVegetarian()) {
            holder.vegetarian.setVisibility(View.VISIBLE);
        } else {
            holder.vegetarian.setVisibility(View.GONE);
        }

        Picasso.get().load(meal.getImagePath()).into(holder.imageView);

        // onClickListener for the find out more button
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

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealID;
        TextView title;
        TextView mealType;
        ImageView imageView;
        TextView vegan;
        TextView vegetarian;
        Button mealDetails;
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
            mealDetails = itemView.findViewById(R.id.mealDetails);
            recipe = itemView.findViewById(R.id.mealInformationTextView);
            ingredients = itemView.findViewById(R.id.mealInformationTextView);
            serves = itemView.findViewById(R.id.servesTextView);
            favouriteCount = itemView.findViewById(R.id.favouriteCount);
            //calories = itemView.findViewById(R.id.caloriesTextView);
        }
    }


    private void openMealDetailsFragment(Meal meal, View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("mealID", meal.getMealID());
        bundle.putString("title", meal.getTitle());
        bundle.putString("meal_type", meal.getMealType());
        bundle.putString("imagePath", meal.getImagePath());
        bundle.putBoolean("isVegetarian", meal.getIsVegetarian());
        bundle.putBoolean("isVegan", meal.getIsVegan());
        bundle.putInt("time_to_cook", meal.getCookingTime());
        bundle.putString("recipe", meal.getRecipe());
        bundle.putString("ingredients", meal.getIngredients());
        bundle.putInt("serves", meal.getServes());
        bundle.putInt("favourite_count", meal.getFavouriteCount());
        bundle.putInt("calories", meal.getCalories());

        Navigation.findNavController(view).navigate(R.id.action_meals_to_mealDetails, bundle);

    }

    private String formatCookingTime(int cookingTimeInMinutes) {
        int hours = cookingTimeInMinutes / 60;
        int minutes = cookingTimeInMinutes % 60;

        if (hours > 0) {
            if (minutes > 0) {
                return hours + " hrs " + minutes + " mins";
            } else {
                return hours + " hrs";
            }
        } else {
            return minutes + " mins";
        }
    }

}
