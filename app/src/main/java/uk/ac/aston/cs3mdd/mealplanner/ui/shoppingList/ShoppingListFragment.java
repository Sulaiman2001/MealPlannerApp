package uk.ac.aston.cs3mdd.mealplanner.ui.shoppingList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    public static ShoppingListFragment newInstance() {
        return new ShoppingListFragment();
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

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnCheckboxClickListener(new ShoppingListAdapter.OnCheckboxClickListener() {
            @Override
            public void onCheckboxClick(int position, boolean isChecked) {
                Ingredients item = shoppingItemList.get(position);
                item.setSelected(isChecked);
            }
        });

        FloatingActionButton deleteButton = rootView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedItems();
            }
        });

        fetchShoppingListData();

        return rootView;
    }

    private void fetchShoppingListData() {
        SharedPreferences preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String user_id = preferences.getString("user_id", "");

        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/fetch_ingredients.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        updateShoppingList(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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

    private void updateShoppingList(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            shoppingItemList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Integer ingredientID = jsonObject.getInt("ingredientId"); // Add this line to fetch ingredient ID
                String ingredientName = jsonObject.getString("ingredientName");
                Integer value = jsonObject.getInt("total_value");
                String unit = jsonObject.getString("unit");
                JSONArray mealsArray = jsonObject.getJSONArray("meals");
                List<String> meals = new ArrayList<>();
                for (int j = 0; j < mealsArray.length(); j++) {
                    meals.add(mealsArray.getString(j));
                }

                // Log ingredient details including ID
                Log.d("IngredientDetails", "Ingredient ID: " + ingredientID);
                Log.d("IngredientDetails", "Ingredient Name: " + ingredientName);
                Log.d("IngredientDetails", "Value: " + value);
                Log.d("IngredientDetails", "Unit: " + unit);
                Log.d("IngredientDetails", "Meals: " + meals);

                boolean found = false;
                for (Ingredients item : shoppingItemList) {
                    if (item.getIngredientName().equals(ingredientName)) {
                        item.setValue(item.getValue() + value);
                        item.getMeals().addAll(meals);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Ingredients ingredients = new Ingredients();
                    ingredients.setIngredientID(ingredientID); // Set ingredient ID
                    ingredients.setIngredientName(ingredientName);
                    ingredients.setValue(value);
                    ingredients.setUnit(unit);
                    ingredients.setMeals(meals);
                    shoppingItemList.add(ingredients);
                }
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void deleteSelectedItems() {
        Iterator<Ingredients> iterator = shoppingItemList.iterator();
        while (iterator.hasNext()) {
            Ingredients item = iterator.next();
            if (item.isSelected()) {
                iterator.remove();
                adapter.notifyDataSetChanged(); // Notify adapter after removing item
                deleteItemFromDatabase(item.getIngredientID()); // Use the actual ingredient ID
            }
        }
    }


    private void deleteItemFromDatabase(Integer ingredientID) {
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/delete_shopping_list_items.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle successful deletion
                        try {
                            Log.d("IngredientID", "Deleting ingredient with ID: " + ingredientID );

                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");
                            if (status.equals("success")) {
                                // Item deleted successfully
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            } else {
                                // Error in deletion
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle deletion error
                        Toast.makeText(requireContext(), "Error deleting item: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", getUserIDFromSharedPreferences());
                params.put("ingredient_id", String.valueOf(ingredientID));
                return params;
            }
        };

        queue.add(stringRequest);
    }


    private String getUserIDFromSharedPreferences() {
        SharedPreferences preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        return preferences.getString("user_id", "");
    }
}
