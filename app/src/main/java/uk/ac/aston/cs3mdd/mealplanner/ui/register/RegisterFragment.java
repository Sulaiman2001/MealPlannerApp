package uk.ac.aston.cs3mdd.mealplanner.ui.register;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment"; // Define a tag for logging

    private EditText registerUsername, registerPassword, confirmPassword;
    private Button registerButton, returnToLoginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        registerUsername = rootView.findViewById(R.id.registerUsername);
        registerPassword = rootView.findViewById(R.id.registerPassword);
        confirmPassword = rootView.findViewById(R.id.confirmPassword);
        registerButton = rootView.findViewById(R.id.register);
        returnToLoginButton = rootView.findViewById(R.id.returnToLogIn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        returnToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_register_to_log_in);
            }
        });

        return rootView;
    }

    private void registerUser() {
        final String username = registerUsername.getText().toString().trim();
        final String password = registerPassword.getText().toString().trim();
        String confirmedPassword = confirmPassword.getText().toString().trim();

        if (!password.equals(confirmedPassword)) {
            Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Passwords do not match");
            return;
        }

        Log.i(TAG, "Passwords match. Sending registration request...");

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/MealPlannerDatabase/register.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleRegistrationResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleRegistrationError(error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return createRegistrationParams();
            }
        };

        // Add the request to the RequestQueue
        queue.add(postRequest);
    }

    private void handleRegistrationResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            String status = jsonResponse.getString("status");
            String message = jsonResponse.getString("message");

            if ("success".equals(status)) {
                Log.i(TAG, "Registration successful. Message: " + message);
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Registration failed. Message: " + message);
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
            Log.e(TAG, "Server Response: " + response);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "JSON parsing error: " + e.getMessage());
        }
    }

    private void handleRegistrationError(VolleyError error) {
        Log.e(TAG, "Volley error: " + error.getMessage(), error);

        if (error.networkResponse != null) {
            Log.e(TAG, "Status Code: " + error.networkResponse.statusCode);
            Log.e(TAG, "Response Data: " + new String(error.networkResponse.data));
        } else {
            Log.e(TAG, "No network response");
        }

        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private Map<String, String> createRegistrationParams() {
        Map<String, String> data = new HashMap<>();
        data.put("username", registerUsername.getText().toString());
        data.put("password", registerPassword.getText().toString());
        data.put("confirmedPassword", confirmPassword.getText().toString());
        // Add other parameters as needed

        return data;
    }
}
