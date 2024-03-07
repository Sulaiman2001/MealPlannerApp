package uk.ac.aston.cs3mdd.mealplanner.ui.mealPlan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class MealPlanHistoryFragment extends Fragment {
    private static final String TAG = "MealPlanFragment";
    private List<MealPlan> mealPlans;
    private RecyclerView recyclerView;
    private MealPlanAdapter mealPlanAdapter;

    public MealPlanHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_plan_history, container, false);

        mealPlans = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.mealPlanHistoryRecyclerView);

        // Pass the FragmentManager to MealPlanAdapter
        mealPlanAdapter = new MealPlanAdapter(mealPlans, getChildFragmentManager(), requireContext(), false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mealPlanAdapter);

        loadMealPlans();

        return rootView;
    }

    private void loadMealPlans() {
        // Retrieve user_id from shared preferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String user_id = preferences.getString("user_id", "");

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/fetch_meal_plan.php";

        // Pass user_id to the server to fetch the meal plans for the logged-in user
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response from server: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("mealPlans");

                            handleFetchMealPlansResponse(jsonArray);
                        } catch (JSONException e) {
                            handleFetchMealPlansError(new VolleyError("Invalid JSON response", e));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleFetchMealPlansError(error);
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

    private void handleFetchMealPlansResponse(JSONArray response) {
        try {
            if (response.length() > 0) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject mealJson = response.getJSONObject(i);
                    String date = mealJson.getString("date");

                    // Skip meals with dates that have passed
                    if (isDateBeforeToday(date)) {
                        continue;
                    }

                    Integer mealID = mealJson.getInt("meal_id");
                    String title = mealJson.getString("title");
                    String imagePath = mealJson.getString("imagePath");
                    Boolean isVegan = mealJson.getInt("vegan") == 1;
                    Boolean isVegetarian = mealJson.getInt("vegetarian") == 1;
                    String cookingTime = mealJson.getString("time_to_cook");
                    String recipe = mealJson.getString("recipe");
                    String ingredients = mealJson.getString("ingredients");
                    Integer serves = mealJson.getInt("serves");

                    MealPlan mealPlan = new MealPlan(mealID, title, imagePath, isVegetarian, isVegan, cookingTime, recipe, ingredients, serves, date);

                    mealPlans.add(mealPlan);
                }
                mealPlanAdapter.notifyDataSetChanged();
            } else if (response.length() == 0) {
                // Handle the case when no meal plans are found
                Log.d(TAG, "No meal plans found");
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

    private void handleFetchMealPlansError(VolleyError error) {
        Log.e(TAG, "Volley error: " + error.getMessage(), error);

        if (error.networkResponse != null) {
            Log.e(TAG, "Status Code: " + error.networkResponse.statusCode);
            Log.e(TAG, "Response Data: " + new String(error.networkResponse.data));
        } else {
            Log.e(TAG, "No network response");
        }

        Toast.makeText(getActivity(), "Error fetching meal plan data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private boolean isDateBeforeToday(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date mealDate = sdf.parse(date);
            Date currentDate = new Date();

            // Compare dates without considering the time
            return !mealDate.after(removeTime(currentDate));
        } catch (ParseException e) {
            // Handle parsing exception if needed
            e.printStackTrace();
            return false; // Assume date hasn't passed in case of an error
        }
    }

    private Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
