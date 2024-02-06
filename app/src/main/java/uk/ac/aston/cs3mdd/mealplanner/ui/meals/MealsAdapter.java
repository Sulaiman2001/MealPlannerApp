package uk.ac.aston.cs3mdd.mealplanner.ui.meals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealViewHolder> {
    private List<Meal> meals;

    public MealsAdapter(List<Meal> meals){
        this.meals = meals;
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
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
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}
