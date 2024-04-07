package uk.ac.aston.cs3mdd.mealplanner.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class LogInFragment extends Fragment {

    public static final String LogInTest = "LogInFragment";

    private EditText editUsername, editPassword;
    private Button registerButton, logInButton;
    private TextView logInErrorMsg, emptyInputFieldsMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log_in, container, false);

        editUsername = rootView.findViewById(R.id.editUsername);
        editPassword = rootView.findViewById(R.id.editPassword);
        registerButton = rootView.findViewById(R.id.registerButton);
        logInButton = rootView.findViewById(R.id.logInButton);
        logInErrorMsg = rootView.findViewById(R.id.logInErrorMsg);
        emptyInputFieldsMsg = rootView.findViewById(R.id.emptyInputFieldsMsg);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to RegisterFragment
                Navigation.findNavController(view).navigate(R.id.action_log_in_to_register);
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });

        return rootView;
    }

    private void logIn(){
        final String username = editUsername.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/login.php";

        if (username.isEmpty() || password.isEmpty()) {
            emptyInputFieldsMsg.setVisibility(TextView.VISIBLE);
            return;
        }

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleLogInResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleLogInError(error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return createLogInParams();
            }
        };

        // Add the request to the RequestQueue
        queue.add(postRequest);
    }

    private void handleLogInResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            String status = jsonResponse.getString("status");
            String message = jsonResponse.getString("message");

            if ("success".equals(status)) {
                String user_id = jsonResponse.getString("user_id");

                // Save user_id in shared preferences
                SharedPreferences preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user_id", user_id);
                editor.apply();

                Log.i(LogInTest, "Retrieved user_id: " + user_id);
                Log.i(LogInTest, "Log in successful. Message: " + message);
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).navigate(R.id.action_log_in_to_meals);
            } else {
                Log.e(LogInTest, "Log in failed. Message: " + message);
                logInErrorMsg.setVisibility(TextView.VISIBLE);
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
            Log.e(LogInTest, "Server Response: " + response);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LogInTest, "JSON parsing error: " + e.getMessage());
        }
    }

    private void handleLogInError(VolleyError error) {
        Log.e(LogInTest, "Volley error: " + error.getMessage(), error);

        if (error.networkResponse != null) {
            Log.e(LogInTest, "Status Code: " + error.networkResponse.statusCode);
            Log.e(LogInTest, "Response Data: " + new String(error.networkResponse.data));
        } else {
            Log.e(LogInTest, "No network response");
        }

        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private Map<String, String> createLogInParams() {
        Map<String, String> data = new HashMap<>();
        data.put("username", editUsername.getText().toString());
        data.put("password", editPassword.getText().toString());
        return data;
    }


}