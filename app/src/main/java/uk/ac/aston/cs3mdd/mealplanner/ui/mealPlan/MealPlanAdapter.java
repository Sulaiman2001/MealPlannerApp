package uk.ac.aston.cs3mdd.mealplanner.ui.mealPlan;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

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

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.MealViewHolder> {
    private List<MealPlan> mealPlans;
    private FragmentManager fragmentManager;
    private Context context;
    private boolean showDeleteButton;

    public MealPlanAdapter(List<MealPlan> mealPlans, FragmentManager fragmentManager, Context context, boolean showDeleteButton) {
        this.mealPlans = mealPlans;
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.showDeleteButton = showDeleteButton;
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_plan_item, parent, false);
        return new MealViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealPlan mealPlan = mealPlans.get(position);

        holder.delete.setVisibility(showDeleteButton ? View.VISIBLE : View.GONE);

        holder.date.setText(mealPlan.getDate());
        holder.cookingTime.setText(formatCookingTime(mealPlan.getCookingTime()));
        holder.title.setText(mealPlan.getTitle());

        holder.favouriteCount.setText(String.valueOf(mealPlan.getFavouriteCount()));


        if ("1".equals(mealPlan.getIsVegan())) {
            holder.vegan.setVisibility(View.VISIBLE);
        } else {
            holder.vegan.setVisibility(View.GONE);
        }

        if (mealPlan.getIsVegan()) {
            holder.vegan.setVisibility(View.VISIBLE);
        } else {
            holder.vegan.setVisibility(View.GONE);
        }

        if (mealPlan.getIsVegetarian()) {
            holder.vegetarian.setVisibility(View.VISIBLE);
        } else {
            holder.vegetarian.setVisibility(View.GONE);
        }

        Picasso.get().load(mealPlan.getImagePath()).into(holder.imageView);

        // Set onClickListener for the mealDetails button
        holder.mealDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MealPlan clickedMeal = mealPlans.get(position);
                    openMealDetailsFragment(clickedMeal, view);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealID;
        public TextView title;
        TextView mealType;
        ImageView imageView;
        TextView vegan;
        TextView vegetarian;
        Button mealDetails;
        TextView cookingTime;
        TextView recipe;
        TextView ingredients;
        TextView serves;
        TextView date;
        Button delete;
        TextView favouriteCount;

        public MealViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.dateTextView);
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

            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(view -> {
                Log.d(TAG, "Delete button clicked");
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MealPlan clickedMeal = mealPlans.get(position);
                    showDeleteConfirmationDialog(clickedMeal, position);
                }
            });

        }
        private void deleteMealFromMealPlan(MealPlan mealPlan, int position) {
            Log.d(TAG, "deletMealFromMealPlan called");

            Log.d(TAG, "Deleting meal with ID: " + mealPlan.getMealID());

            // Call the server to delete the meal from favourites
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/delete_meal_plan.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle the response if needed
                            Log.d(TAG, "Response from server after deletion: " + response);
                            // Remove the meal from the local list
                            mealPlans.remove(position);
                            notifyItemRemoved(position);
                            // Show a toast or provide feedback to the user
                            Toast.makeText(itemView.getContext(), "Meal deleted from meal plan", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle the error if needed
                            Log.e(TAG, "Error deleting meal from favourites: " + error.getMessage(), error);
                            // Show a toast or provide feedback to the user
                            Toast.makeText(itemView.getContext(), "Error deleting meal from meal plan", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", getUserIDFromSharedPreferences(context));
                    params.put("meal_id", String.valueOf(mealPlan.getMealID()));
                    params.put("meal_plan_id", String.valueOf(mealPlan.getMealPlanID()));

                    return params;
                }
            };

            queue.add(stringRequest);
            Log.d(TAG, "deleteMealFromMealPlan completed");
        }

        private void showDeleteConfirmationDialog(MealPlan mealPlan, int position) {
            // Create a confirmation dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Meal from Meal Plan");
            builder.setMessage("Are you sure you want to delete this meal from your meal plan?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Call the method to delete the meal from the meal plan
                    deleteMealFromMealPlan(mealPlan, position);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss the dialog if "No" is clicked
                    dialog.dismiss();
                }
            });

            // Show the confirmation dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }


        private String getUserIDFromSharedPreferences(Context context){
            SharedPreferences preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
            return preferences.getString("user_id", "");
        }

    }

    private void openMealDetailsFragment(MealPlan meal, View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("mealPlanID", meal.getMealPlanID());
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
        bundle.putString("date", meal.getDate());
        bundle.putInt("favourite_count", meal.getFavouriteCount());

        if (showDeleteButton == false){
            Navigation.findNavController(view).navigate(R.id.action_meal_plan_history_to_mealDetails, bundle);

        } else {
            Navigation.findNavController(view).navigate(R.id.action_meal_plan_to_mealDetails, bundle);
        }

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
