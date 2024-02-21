package uk.ac.aston.cs3mdd.mealplanner.ui.favourites;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.aston.cs3mdd.mealplanner.R;
import uk.ac.aston.cs3mdd.mealplanner.ui.meals.Meal;

public class FavouritesFragment extends Fragment {

    private static final String TAG = "FavouritesFragment";
    private List<Meal> favouriteMeals;
    private RecyclerView recyclerView;
    private FavouritesAdapter favouritesAdapter;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);

        favouriteMeals = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.favouritesRecyclerView);

        // Pass the FragmentManager to FavouritesAdapter
        favouritesAdapter = new FavouritesAdapter(favouriteMeals, getChildFragmentManager());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(favouritesAdapter);

        loadFavouriteMeals();

        return rootView;
    }

    private void loadFavouriteMeals() {
        // Retrieve user_id from shared preferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String user_id = preferences.getString("user_id", "");

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/fetch_favourite_meals.php";

        // Pass user_id to the server to fetch only the favorite meals for the logged-in user
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response from server: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("meals");
                            handleFetchFavouriteMealsResponse(jsonArray);
                        } catch (JSONException e) {
                            handleFetchFavouriteMealsError(new VolleyError("Invalid JSON response", e));
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleFetchFavouriteMealsError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                return params;
            }
        };


// Add the request to the RequestQueue
        queue.add(stringRequest);
    }


    private void handleFetchFavouriteMealsResponse(JSONArray response) {
        try {
            if (response.length() > 0) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject mealJson = response.getJSONObject(i);
                    Integer mealID = mealJson.getInt("meal_id");
                    String title = mealJson.getString("title");
                    String imagePath = mealJson.getString("imagePath");
                    Boolean isVegan = mealJson.getInt("vegan") == 1;
                    Boolean isVegetarian = mealJson.getInt("vegetarian") == 1;
                    String cookingTime = mealJson.getString("time_to_cook");
                    String recipe = mealJson.getString("recipe");
                    String ingredients = mealJson.getString("ingredients");
                    Integer serves = mealJson.getInt("serves");

                    Meal meal = new Meal(mealID, title, imagePath, isVegetarian, isVegan, cookingTime, recipe, ingredients, serves);

                    favouriteMeals.add(meal);
                }
                favouritesAdapter.notifyDataSetChanged();
            } else if (response.length() == 0) {
                // Handle the case when no meals are found
                Log.d(TAG, "No meals found");
            } else {
                Log.e(TAG, "Empty or invalid JSON array");
            }
        } catch (JSONException e) {
            try {
                // Check if the response is a JSON object
                JSONObject jsonObject = response.getJSONObject(0);
                String message = jsonObject.getString("message");

                // Handle the case when the response is a JSON object
                Log.d(TAG, "Message: " + message);
            } catch (JSONException ex) {
                // Handle any other JSON parsing errors
                e.printStackTrace();
                Log.e(TAG, "JSON parsing error: " + e.getMessage());
            }
        }
    }


    private void handleFetchFavouriteMealsError(VolleyError error){
        Log.e(TAG, "Volley error: " + error.getMessage(), error);

        if (error.networkResponse != null){
            Log.e(TAG, "Status Code: " + error.networkResponse.statusCode);
            Log.e(TAG, "Response Data: " + new String(error.networkResponse.data));
        } else {
            Log.e(TAG, "No network response");
        }

        Toast.makeText(getActivity(), "Error fetching favorite meals data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
