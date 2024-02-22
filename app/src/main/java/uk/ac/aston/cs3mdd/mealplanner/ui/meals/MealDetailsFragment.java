package uk.ac.aston.cs3mdd.mealplanner.ui.meals;

import static uk.ac.aston.cs3mdd.mealplanner.ui.login.LogInFragment.LogInTest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.ac.aston.cs3mdd.mealplanner.R;
import uk.ac.aston.cs3mdd.mealplanner.ui.login.LogInFragment;

public class MealDetailsFragment extends Fragment {

    private static final String MealDetailsTest = "MealDetailsFragment";


    public MealDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_details, container, false);

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    // Handle back button press here
                    Navigation.findNavController(requireView()).navigate(R.id.action_mealDetails_to_meals);
                    return true;
                }
                return false;
            }
        });

        // Retrieve user_id from shared preferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String user_id = preferences.getString("user_id", "");

        // Retrieve data from arguments
        Bundle bundle = getArguments();
        if (bundle != null) {

            Integer mealID = bundle.getInt("mealID");
            String title = bundle.getString("title");
            String imagePath = bundle.getString("imagePath");
            Boolean isVegetarian = bundle.getBoolean("isVegetarian");
            Boolean isVegan = bundle.getBoolean("isVegan");
            String recipe = bundle.getString("recipe");
            String ingredients = bundle.getString("ingredients");
            Integer serves = bundle.getInt("serves");

            //TextView mealIDTextView = rootView.findViewById(R.id.mealIDTextView);
            TextView titleTextView = rootView.findViewById(R.id.titleTextView);
            ImageView imageView = rootView.findViewById(R.id.imageView);
            TextView vegetarianTextView = rootView.findViewById(R.id.vegetarianTextView);
            TextView veganTextView = rootView.findViewById(R.id.veganTextView);
            RadioButton ingredientsButton = rootView.findViewById(R.id.ingredientsButton);
            RadioButton recipeButton = rootView.findViewById(R.id.recipeButton);
            TextView recipeTextView = rootView.findViewById(R.id.mealInformationTextView);
            TextView ingredientsTextView = rootView.findViewById(R.id.mealInformationTextView);
            TextView servesTextView = rootView.findViewById(R.id.servesTextView);
            Button favouriteButton = rootView.findViewById(R.id.favouriteButton);
            Button addToMealPlan = rootView.findViewById(R.id.addToMealPlan);


            //mealIDTextView.setText("MealID: " + mealID);
            titleTextView.setText(title);
            Picasso.get().load(imagePath).into(imageView);
            //vegetarianTextView.setText(isVegetarian);
            //veganTextView.setText(isVegan);
            // Set visibility for vegetarianTextView based on isVegetarian
            if (isVegetarian) {
                vegetarianTextView.setVisibility(View.VISIBLE);
            } else {
                vegetarianTextView.setVisibility(View.GONE);
            }

            // Set visibility for veganTextView based on isVegan
            if (isVegan) {
                veganTextView.setVisibility(View.VISIBLE);
            } else {
                veganTextView.setVisibility(View.GONE);
            }

            favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("MealDetailsFragment", "user_id: " + user_id);
                    Log.d("MealDetailsFragment", "meal_id: " + mealID);
                    Log.d("MealDetailsFragment", "is_favourited: " + "1"); // Assuming it's always set to 1

                    markAsFavourite();
                }
            });

            addToMealPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(requireContext(), "Add to meal plan!", Toast.LENGTH_SHORT).show();
                }
            });

            ingredientsTextView.setText(ingredients);
            ingredientsButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // Load and display ingredients from the database
                        ingredientsTextView.setText(ingredients);
                        // Ensure the other button is unchecked
                        recipeButton.setChecked(false);
                    } else {
                        recipeTextView.setText(recipe);
                    }
                }
            });
            servesTextView.setText("Serves " + serves);

        }
        return rootView;
    }

    private void markAsFavourite() {
        // Retrieve mealID from the arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            Integer mealID = bundle.getInt("mealID");

            // Retrieve user_id from shared preferences
            SharedPreferences preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
            String user_id = preferences.getString("user_id", "");

            // Call sendFavouriteRequest with user_id and mealID
            sendFavouriteRequest(user_id, String.valueOf(mealID));
        }
    }


    private void sendFavouriteRequest(String user_id, String mealID) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/favourite.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleFavouriteResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleFavouriteError(error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = createFavouriteParams(user_id, mealID);
                Log.d(MealDetailsTest, "Request Parameters: " + params.toString());
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(postRequest);
    }



    private void handleFavouriteResponse(String response) {
        Log.d(MealDetailsTest, "Raw Server Response: " + response);

        try {
            JSONObject jsonResponse = new JSONObject(response);
            String status = jsonResponse.getString("status");
            String message = jsonResponse.getString("message");

            if ("success".equals(status)) {
                // Handle success (e.g., update UI)
                Toast.makeText(requireContext(), "Meal favourited!", Toast.LENGTH_SHORT).show();
            } else {
                // Handle failure (e.g., show error message)
                Log.e(MealDetailsTest, "Server Response: " + response);
                Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(MealDetailsTest, "JSON parsing error: " + e.getMessage());
            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private void handleFavouriteError(VolleyError error) {
        Log.e(MealDetailsTest, "Volley error: " + error.getMessage(), error);

        if (error.networkResponse != null) {
            Log.e(MealDetailsTest, "Status Code: " + error.networkResponse.statusCode);
            Log.e(MealDetailsTest, "Response Data: " + new String(error.networkResponse.data));
        } else {
            Log.e(MealDetailsTest, "No network response");
        }

        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }


    private Map<String, String> createFavouriteParams(String user_id, String mealID) {
        Map<String, String> data = new HashMap<>();
        data.put("user_id", user_id);
        data.put("meal_id", mealID);
        data.put("is_favourited", "1"); // Assuming you want to set is_favourited to 1 when marking as a Favourite

        return data;
    }


}