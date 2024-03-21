package uk.ac.aston.cs3mdd.mealplanner.ui.shoppingList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class ShoppingListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private List<Ingredients> shoppingItemList;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    public static ShoppingListFragment newInstance(String param1, String param2) {
        ShoppingListFragment fragment = new ShoppingListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        shoppingItemList = new ArrayList<>();
        adapter = new ShoppingListAdapter(shoppingItemList, requireContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        fetchShoppingListData();

        return rootView;
    }

    private void fetchShoppingListData(){
        SharedPreferences preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String user_id = preferences.getString("user_id", "");

        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/fetch_ingredients.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        // Make a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the JSON response and update shoppingItemList
                        updateShoppingList(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void updateShoppingList(String response){
        try {
            JSONArray jsonArray = new JSONArray(response);

            shoppingItemList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String ingredientName = jsonObject.getString("ingredientName");
                Integer value = jsonObject.getInt("total_value");
                String unit = jsonObject.getString("unit");

                // Fetch the meals associated with the ingredient
                JSONArray mealsArray = jsonObject.getJSONArray("meals");
                List<String> meals = new ArrayList<>();
                for (int j = 0; j < mealsArray.length(); j++) {
                    meals.add(mealsArray.getString(j));
                }

                // Check if the ingredient already exists in the list
                boolean found = false;
                for (Ingredients item : shoppingItemList) {
                    if (item.getIngredientName().equals(ingredientName)) {
                        // Update the value of the existing ingredient
                        item.setValue(item.getValue() + value);
                        item.getMeals().addAll(meals);
                        found = true;
                        break;
                    }
                }

                // If the ingredient is not found, add it to the list
                if (!found) {
                    Ingredients ingredients = new Ingredients();
                    ingredients.setIngredientName(ingredientName);
                    ingredients.setValue(value);
                    ingredients.setUnit(unit);
                    ingredients.setMeals(meals); // Set meals for the new ingredient
                    shoppingItemList.add(ingredients);
                }
            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e){
            e.printStackTrace();
        }
    }



}