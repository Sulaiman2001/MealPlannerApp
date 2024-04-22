package uk.ac.aston.cs3mdd.mealplanner.ui.favourites;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.MealViewHolder> {
    private List<Meal> meals;
    private FragmentManager fragmentManager;
    private Context context;
    private static final String TAG = "FavouritesAdapter";

    // Constructor for the FavouritesAdapter class
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

        // Set cooking time
        holder.cookingTime.setText(formatCookingTime(meal.getCookingTime()));

        // Set title
        holder.title.setText(meal.getTitle());

        // Set favourite number
        holder.favouriteCount.setText(String.valueOf(meal.getFavouriteCount()));

        // Checks if the meal is vegan and then sets the tag as visible/invisible
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

        // Checks if the meal is vegetarian and then sets the tag as visible/invisible
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
        TextView mealType;
        ImageView imageView;
        TextView vegan;
        TextView vegetarian;
        Button mealDetails;
        TextView cookingTime;
        TextView recipe;
        TextView ingredients;
        TextView serves;
        TextView calories;
        Button delete;
        TextView favouriteCount;

        // subclass that initialises the views
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
            calories = itemView.findViewById(R.id.servesTextView);
            favouriteCount = itemView.findViewById(R.id.favouriteCount);

            // delete button
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

            // Create a confirmation dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Delete Meal from favourites");
            builder.setMessage("Are you sure you want to delete this meal from your favourites?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Call the server to delete the meal from favourites
                    RequestQueue queue = Volley.newRequestQueue(context);
                    String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/delete_favourite_meal.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Handle the response
                                    Log.d(TAG, "Response from server after deletion: " + response);
                                    // Remove the meal from the  list
                                    meals.remove(position);
                                    notifyItemRemoved(position);
                                    // Show a toast or provide feedback to the user
                                    Toast.makeText(itemView.getContext(), "Meal deleted from favourites", Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Handle the error
                                    Log.e(TAG, "Error deleting meal from favourites: " + error.getMessage(), error);
                                    // Show a toast or provide feedback to the user
                                    Toast.makeText(itemView.getContext(), "Error deleting meal from favourites", Toast.LENGTH_SHORT).show();
                                }
                            }) {

                        // hash map for the data to be sent to the database
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
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss the dialog if no is clicked
                    dialog.dismiss();
                }
            });

            // Show the confirmation dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        // Retrieve the correct user details
        private String getUserIDFromSharedPreferences(Context context){
            SharedPreferences preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
            return preferences.getString("user_id", "");
        }
    }

    // Store the meal details
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
        bundle.putInt("calories", meal.getCalories());
        bundle.putInt("favourite_count", meal.getFavouriteCount());

        Navigation.findNavController(view).navigate(R.id.action_favourites_to_mealDetails, bundle);

    }

    // Show the cooking time in hrs and mins
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
