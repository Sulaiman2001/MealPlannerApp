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

        // Set title
        holder.titleTextView.setText(meal.getTitle());

        if ("1".equals(meal.getIsVegan())) {
            holder.vegan.setVisibility(View.VISIBLE);
        } else {
            holder.vegan.setVisibility(View.GONE);
        }

        // Check if it's vegetarian and set visibility accordingly
        if ("1".equals(meal.getIsVegetarian())) {
            holder.vegetarian.setVisibility(View.VISIBLE);
        } else {
            holder.vegetarian.setVisibility(View.GONE);
        }

        // Load image using a library like Picasso or Glide
        // Example using Picasso:
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

    class MealViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;
        TextView vegan;
        TextView vegetarian;
        Button mealDetails;

        public MealViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.imageView);
            vegan = itemView.findViewById(R.id.vegan);
            vegetarian = itemView.findViewById(R.id.vegetarian);
            mealDetails = itemView.findViewById(R.id.mealDetails);
        }
    }

    private void openMealDetailsFragment(Meal meal, View view) {
        Bundle bundle = new Bundle();
        bundle.putString("title", meal.getTitle());
        bundle.putString("imagePath", meal.getImagePath());
        bundle.putString("isVegetarian", meal.getIsVegetarian());
        bundle.putString("isVegan", meal.getIsVegan());

        Navigation.findNavController(view).navigate(R.id.action_meals_to_mealDetails, bundle);

    }
}
