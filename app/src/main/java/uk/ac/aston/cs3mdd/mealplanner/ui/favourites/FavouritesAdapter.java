package uk.ac.aston.cs3mdd.mealplanner.ui.favourites;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.aston.cs3mdd.mealplanner.R;
import uk.ac.aston.cs3mdd.mealplanner.ui.meals.Meal;
import uk.ac.aston.cs3mdd.mealplanner.ui.meals.MealsAdapter;

// FavouritesAdapter.java
public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.MealViewHolder> {
    private List<Meal> meals;
    private FragmentManager fragmentManager;
    private Context context;
    private static final String TAG = "FavouritesAdapter";


    public FavouritesAdapter(List<Meal> meals, FragmentManager fragmentManager, Context context) {
        this.meals = meals;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @Override
    public FavouritesAdapter.MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.cookingTime.setText(String.valueOf(meal.getCookingTime()));

        // Set title
        holder.title.setText(meal.getTitle());

        holder.favouriteCount.setText(String.valueOf(meal.getFavouriteCount()));

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
        Button delete;
        TextView favouriteCount;

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
            favouriteCount = itemView.findViewById(R.id.favouriteCount);

            delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(view -> {
                Log.d(TAG, "Delete button clicked");
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Meal clickedMeal = meals.get(position);
                    deleteMealFromFavourites(clickedMeal, position);
                }
            });
        }


        private void deleteMealFromFavourites(Meal meal, int position) {
            Log.d(TAG, "deleteMealFromFavourites called");

            Log.d(TAG, "Deleting meal with ID: " + meal.getMealID());

            // Call the server to delete the meal from favourites
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/delete_favourite_meal.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle the response if needed
                            Log.d(TAG, "Response from server after deletion: " + response);
                            // Remove the meal from the local list
                            meals.remove(position);
                            notifyItemRemoved(position);
                            // Show a toast or provide feedback to the user
                            Toast.makeText(itemView.getContext(), "Meal deleted from favourites", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle the error if needed
                            Log.e(TAG, "Error deleting meal from favourites: " + error.getMessage(), error);
                            // Show a toast or provide feedback to the user
                            Toast.makeText(itemView.getContext(), "Error deleting meal from favourites", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", getUserIDFromSharedPreferences(context));
                    params.put("meal_id", String.valueOf(meal.getMealID()));
                    return params;
                }
            };

            queue.add(stringRequest);
            Log.d(TAG, "deleteMealFromFavourites completed");
        }

        private String getUserIDFromSharedPreferences(Context context){
            SharedPreferences preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
            return preferences.getString("user_id", "");
        }
    }



    private void openMealDetailsFragment(Meal meal, View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("mealID", meal.getMealID());
        bundle.putString("title", meal.getTitle());
        bundle.putString("imagePath", meal.getImagePath());
        bundle.putBoolean("isVegetarian", meal.getIsVegetarian());
        bundle.putBoolean("isVegan", meal.getIsVegan());
        bundle.putInt("time_to_cook", meal.getCookingTime());
        bundle.putString("recipe", meal.getRecipe());
        bundle.putString("ingredients", meal.getIngredients());
        bundle.putInt("serves", meal.getServes());
        bundle.putInt("favourite_count", meal.getFavouriteCount());

        Navigation.findNavController(view).navigate(R.id.action_favourites_to_mealDetails, bundle);

    }
}
