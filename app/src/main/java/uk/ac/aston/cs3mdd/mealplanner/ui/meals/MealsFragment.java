package uk.ac.aston.cs3mdd.mealplanner.ui.meals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class MealsFragment extends Fragment {

    private static final String TAG = "MealsFragment";
    private List<Meal> meals;
    private RecyclerView recyclerView;
    private MealsAdapter mealsAdapter;

    public MealsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meals, container, false);

        meals = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.recyclerView);

        // Pass the FragmentManager to MealsAdapter
        mealsAdapter = new MealsAdapter(meals, getChildFragmentManager());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mealsAdapter);

        fetchMealData();

        return rootView;
    }


    private void fetchMealData(){
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/fetch_meals.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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

                    meals.add(meal);
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