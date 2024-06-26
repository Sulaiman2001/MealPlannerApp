package uk.ac.aston.cs3mdd.mealplanner.ui.meals;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class MealsFragment extends Fragment {

    private static final String TAG = "MealsFragment";
    private List<Meal> meals;
    private List<Meal> filteredMeals;

    private RecyclerView recyclerView;
    private MealsAdapter mealsAdapter;
    private EditText searchEditText;

    public MealsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        // Determine the current destination
        NavDestination currentDestination = navController.getCurrentDestination();

        // Check if the current destination is the MealsFragment
        if (currentDestination != null && currentDestination.getId() == R.id.meals) {
            // Get the BottomNavigationView
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);

            // Set the selected tab to the Meals tab
            bottomNavigationView.setSelectedItemId(R.id.meals);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Update UI with filtered meals
        mealsAdapter.setMeals(filteredMeals);
        mealsAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meals, container, false);

        meals = new ArrayList<>();
        filteredMeals = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        searchEditText = rootView.findViewById(R.id.searchEditText);

        mealsAdapter = new MealsAdapter(meals, getChildFragmentManager());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mealsAdapter);

        Button allFilterButton = rootView.findViewById(R.id.allFilter);
        allFilterButton.setBackgroundResource(R.drawable.active_button_background);

        Button veganFilterButton = rootView.findViewById(R.id.veganFilter);
        veganFilterButton.setBackgroundResource(R.drawable.inactive_button_background);

        Button vegetarianFilterButton = rootView.findViewById(R.id.vegetarianFilter);
        vegetarianFilterButton.setBackgroundResource(R.drawable.inactive_button_background);

        Button timeFilterButton = rootView.findViewById(R.id.timeFilter);
        timeFilterButton.setBackgroundResource(R.drawable.inactive_button_background);

        Button breakfastFilterButton = rootView.findViewById(R.id.breakfastFilter);
        breakfastFilterButton.setBackgroundResource(R.drawable.inactive_button_background);

        Button lunchFilterButton = rootView.findViewById(R.id.lunchFilter);
        lunchFilterButton.setBackgroundResource(R.drawable.inactive_button_background);

        Button dinnerFilterButton = rootView.findViewById(R.id.dinnerFilter);
        dinnerFilterButton.setBackgroundResource(R.drawable.inactive_button_background);



        allFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMealData();
                allFilterButton.setBackgroundResource(R.drawable.active_button_background);
                veganFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                vegetarianFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                timeFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                breakfastFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                lunchFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                dinnerFilterButton.setBackgroundResource(R.drawable.inactive_button_background);

                searchEditText.setText("");

            }
        });

        veganFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch only vegan meals
                fetchVeganMeals();
                allFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                veganFilterButton.setBackgroundResource(R.drawable.active_button_background);
                vegetarianFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                timeFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                breakfastFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                lunchFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                dinnerFilterButton.setBackgroundResource(R.drawable.inactive_button_background);

                searchEditText.setText("");

            }
        });
        vegetarianFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch only vegetarian meals
                fetchVegetarianMeals();
                allFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                veganFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                vegetarianFilterButton.setBackgroundResource(R.drawable.active_button_background);
                timeFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                breakfastFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                lunchFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                dinnerFilterButton.setBackgroundResource(R.drawable.inactive_button_background);

                searchEditText.setText("");

            }
        });

        timeFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch only vegetarian meals
                fetchMealsLessThan60mins();
                allFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                veganFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                vegetarianFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                timeFilterButton.setBackgroundResource(R.drawable.active_button_background);
                breakfastFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                lunchFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                dinnerFilterButton.setBackgroundResource(R.drawable.inactive_button_background);

                searchEditText.setText("");

            }
        });

        breakfastFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch only breakfast meals
                fetchMealsByType("breakfast");
                // Update button backgrounds
                allFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                veganFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                vegetarianFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                timeFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                breakfastFilterButton.setBackgroundResource(R.drawable.active_button_background);
                lunchFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                dinnerFilterButton.setBackgroundResource(R.drawable.inactive_button_background);

                searchEditText.setText("");

            }
        });

        lunchFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch only lunch meals
                fetchMealsByType("lunch");
                allFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                veganFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                vegetarianFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                timeFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                breakfastFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                lunchFilterButton.setBackgroundResource(R.drawable.active_button_background);
                dinnerFilterButton.setBackgroundResource(R.drawable.inactive_button_background);

                searchEditText.setText("");

            }
        });

        dinnerFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch only dinner meals
                fetchMealsByType("dinner");
                allFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                veganFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                vegetarianFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                timeFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                breakfastFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                lunchFilterButton.setBackgroundResource(R.drawable.inactive_button_background);
                dinnerFilterButton.setBackgroundResource(R.drawable.active_button_background);

                searchEditText.setText("");

            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the list of meals based on user input
                filterMeals(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // fetch all meals
        fetchMealData();
        return rootView;
    }

    private void filterMeals(String searchText) {
        filteredMeals.clear();
        if (TextUtils.isEmpty(searchText)) {
            // If the search field is empty show all meals
            filteredMeals.addAll(meals);
        } else {
            // Filter meals based on the search text
            for (Meal meal : meals) {
                if (meal.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredMeals.add(meal);
                }
            }
        }
        // Update RecyclerView with filtered meals
        mealsAdapter.setMeals(filteredMeals);
        mealsAdapter.notifyDataSetChanged();
    }

    // Request to retrieve all meals
    private void fetchMealData(){
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/fetch_meals.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        meals.clear();
//                        filteredMeals.clear();
                        handleFetchMealDataResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleFetchMealDataError(error);
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);

    }
    // Request to retrieve vegan meals
    private void fetchVeganMeals() {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/fetch_meals.php?vegan=1";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Clear the existing list of meals
                        meals.clear();
                        filteredMeals.clear();

                        // Handle the response to populate the list with vegan meals
                        handleFetchMealDataResponse(response);
                        filteredMeals.addAll(meals);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleFetchMealDataError(error);
                    }
                });


        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }

    // Request to retrieve vegetarian meals
    private void fetchVegetarianMeals() {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/fetch_meals.php?vegetarian=1";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Clear the existing list of meals
                        meals.clear();
                        filteredMeals.clear();
                        // Handle the response to populate the list with vegan meals
                        handleFetchMealDataResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleFetchMealDataError(error);
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }

    // Request to retrieve meals that take less than 60 mins
    private void fetchMealsLessThan60mins() {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/fetch_meals.php?cooking_time=1";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Clear the existing list of meals
                        meals.clear();
                        filteredMeals.clear();
                        // Handle the response to populate the list with vegan meals
                        handleFetchMealDataResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleFetchMealDataError(error);
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }

    // Request to retrieve meals by type
    private void fetchMealsByType(String mealType) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/fetch_meals.php?meal_type=" + mealType;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Clear the existing list of meals
                        meals.clear();
                        filteredMeals.clear();
                        // Handle the response to populate the list with meals of specified type
                        handleFetchMealDataResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleFetchMealDataError(error);
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }

    private void handleFetchMealDataResponse(JSONArray response) {
        try {
            if (response.length() > 0) {
                // Clear the existing list of meals
                meals.clear();
                filteredMeals.clear();

                for (int i = 0; i < response.length(); i++) {
                    JSONObject mealJson = response.getJSONObject(i);
                    // Log the meal information
                    Log.d(TAG, "Received meal JSON: " + mealJson.toString());

                    Integer mealID = mealJson.getInt("meal_id");
                    String title = mealJson.getString("title");
                    String mealType = mealJson.getString("meal_type");
                    String imagePath = mealJson.getString("imagePath");
                    Boolean isVegan = mealJson.getInt("vegan") == 1;
                    Boolean isVegetarian = mealJson.getInt("vegetarian") == 1;
                    Integer cookingTime = mealJson.getInt("time_to_cook");
                    String recipe = mealJson.getString("recipe");
                    String ingredients = mealJson.getString("ingredients");
                    Integer serves = mealJson.getInt("serves");
                    Integer favouriteCount = mealJson.getInt("favourite_count");
                    Integer calories = mealJson.getInt("calories");
                    Meal meal = new Meal(mealID, title, mealType, imagePath, isVegetarian, isVegan, cookingTime, recipe, ingredients, serves, favouriteCount, calories);

                    meals.add(meal);
                    filteredMeals.add(meal);
                }
                mealsAdapter.notifyDataSetChanged();
            } else {
                Log.e(TAG, "Empty or invalid JSON array");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "JSON parsing error: " + e.getMessage());
        }
    }
    private void handleFetchMealDataError(VolleyError error){
        Log.e(TAG, "Volley error: " + error.getMessage(), error);

        if (error.networkResponse != null){
            Log.e(TAG, "Statues Code: " + error.networkResponse.statusCode);
            Log.e(TAG, "Response Data: " + new String(error.networkResponse.data));
        } else {
            Log.e(TAG, "No network response");
        }

        Toast.makeText(getActivity(), "Error fetching meal data: " + error.getMessage(), Toast.LENGTH_SHORT). show();
    }

}