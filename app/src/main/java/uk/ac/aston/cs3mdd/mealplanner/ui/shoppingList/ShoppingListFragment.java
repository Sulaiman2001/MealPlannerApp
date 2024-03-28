package uk.ac.aston.cs3mdd.mealplanner.ui.shoppingList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private SharedPreferences sharedPreferences; // Add this line

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

        sharedPreferences = requireActivity().getSharedPreferences("checkbox_state", Context.MODE_PRIVATE);

        adapter.setOnCheckboxClickListener(new ShoppingListAdapter.OnCheckboxClickListener() {
            @Override
            public void onCheckboxClick(int position, boolean isChecked) {
                Ingredients item = shoppingItemList.get(position);
                item.setChecked(isChecked); // Update the isChecked field in the data model
            }
        });

        FloatingActionButton deleteButton = rootView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedItems();
            }
        });

        FloatingActionButton addIngredientButton = rootView.findViewById(R.id.addIngredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddIngredientDialog();
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
                Integer ingredientID = null; // Initialize to null
                if (!jsonObject.isNull("ingredientId")) { // Check if the value is not null
                    ingredientID = jsonObject.getInt("ingredientId"); // Convert to integer only if not null
                }
                String ingredientName = jsonObject.getString("ingredientName");
                String value = jsonObject.getString("total_value");
                String unit = jsonObject.getString("unit");
                JSONArray mealsArray = jsonObject.getJSONArray("meals");
                List<String> meals = new ArrayList<>();
                for (int j = 0; j < mealsArray.length(); j++) {
                    meals.add(mealsArray.getString(j));
                }
                boolean isCustom = jsonObject.getBoolean("isCustom");

                // Log ingredient details including ID
                Log.d("IngredientDetails", "Ingredient ID: " + ingredientID);
                Log.d("IngredientDetails", "Ingredient Name: " + ingredientName);
                Log.d("IngredientDetails", "Value: " + value);
                Log.d("IngredientDetails", "Unit: " + unit);
                Log.d("IngredientDetails", "Meals: " + meals);
                Log.d("IngredientDetails", "isCustom: " + isCustom);

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
                    ingredients.setCustom(isCustom);
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
            if (item.isChecked()) {
                iterator.remove();
                deleteItemFromDatabase(item.getIngredientID(), item.isCustom()); // Pass isCustom parameter
            }
        }
        // After deletion, save the updated checkbox states
        saveCheckboxStates();
        adapter.notifyDataSetChanged();
    }

    private void deleteItemFromDatabase(Integer ingredientID, boolean isCustom) { // Modify method signature
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
                params.put("is_custom", String.valueOf(isCustom)); // Pass isCustom parameter
                return params;
            }
        };

        queue.add(stringRequest);
    }



    private void addCustomIngredient(String ingredientName, String amount) {
        // Get user ID from SharedPreferences
        String userID = getUserIDFromSharedPreferences();
        Log.d("AddIngredient", "Ingredient Name: " + ingredientName);

        // Request URL
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/add_custom_ingredient.php";

        // Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        // POST parameters
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userID);
        params.put("ingredient_name", ingredientName);
        params.put("amount", amount);

        // Create a new StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response from the server
                        Log.d("AddIngredientResponse", "Response: " + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            if (status.equals("success")) {
                                // Ingredient added successfully
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                                // Refresh shopping list data after adding the ingredient
                                fetchShoppingListData();
                            } else {
                                // Error adding ingredient
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Toast.makeText(requireContext(), "Error adding ingredient: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }


    private void showAddIngredientDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_ingredient_dialog, null);

        final EditText editTextIngredientName = view.findViewById(R.id.editTextIngredientName);
        final EditText editTextAmount = view.findViewById(R.id.editTextAmount);

        builder.setView(view)
                .setTitle("Add Ingredient")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String ingredientName = editTextIngredientName.getText().toString();
                        String amount = editTextAmount.getText().toString();
                        // Handle the addition of the ingredient here
                        // For example, you can add it to your shopping list data or send it to the server
                        Toast.makeText(requireContext(), "Added ingredient to the shopping list" + ingredientName + amount, Toast.LENGTH_SHORT).show();
                        addCustomIngredient(ingredientName, amount);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Restore checkbox states when the fragment is resumed
        restoreCheckboxStates();
    }

    private void restoreCheckboxStates() {
        for (int i = 0; i < shoppingItemList.size(); i++) {
            boolean isChecked = sharedPreferences.getBoolean("checkbox_" + i, false);
            shoppingItemList.get(i).setChecked(isChecked);
        }
        adapter.notifyDataSetChanged();
    }

    private void saveCheckboxStates() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < shoppingItemList.size(); i++) {
            editor.putBoolean("checkbox_" + i, shoppingItemList.get(i).isChecked());
        }
        editor.apply();
    }

    private String getUserIDFromSharedPreferences() {
        SharedPreferences preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        return preferences.getString("user_id", "");
    }
}