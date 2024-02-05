package uk.ac.aston.cs3mdd.mealplanner.ui.register;

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

public class RegisterFragment extends Fragment {
    private static final String RegisterTest = "RegisterFragment";

    private EditText registerUsername, registerPassword, confirmPassword;
    private Button registerButton, returnToLoginButton;

    private TextView  passwordLengthMsg, passwordCapitalLetterMsg, passwordMatchMsg, passwordNumberMsg, emptyInputFieldsMsg;

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
        passwordLengthMsg = rootView.findViewById(R.id.passwordLengthMsg);
        passwordCapitalLetterMsg = rootView.findViewById(R.id.passwordCapitalLetterMsg);
        passwordMatchMsg = rootView.findViewById(R.id.passwordMatchMsg);
        passwordNumberMsg = rootView.findViewById(R.id.passwordNumberMsg);
        emptyInputFieldsMsg = rootView.findViewById(R.id.emptyInputFieldsMsg);


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

        boolean hasError = false; // Variable to track if any error occurred

        // Check if username, password, and confirmed password are not empty
        if (username.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()) {
            Toast.makeText(getActivity(), "All fields must be filled", Toast.LENGTH_SHORT).show();
            emptyInputFieldsMsg.setVisibility(TextView.VISIBLE);
            return;
        }

        // Check the password is at least 8 characters
        if (password.length() < 8) {
            passwordLengthMsg.setVisibility(TextView.VISIBLE);
            hasError = true;
        }

        // Check the password contains a number
        if (!password.matches(".*[1-9].*")) {
            passwordNumberMsg.setVisibility(TextView.VISIBLE);
            hasError = true;
        }

        // Check the password contains a capital character
        if (!password.matches(".*[A-Z].*")) {
            passwordCapitalLetterMsg.setVisibility(TextView.VISIBLE);
            hasError = true;
        }

        // Check if passwords match
        if (!password.equals(confirmedPassword)) {
            passwordMatchMsg.setVisibility(TextView.VISIBLE);
            hasError = true;
        }

        if (hasError) {
            return;
        }

        Log.i(RegisterTest, "Passwords match. Sending registration request...");



        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/MealPlannerDatabase/register.php";

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
                Log.i(RegisterTest, "Registration successful. Message: " + message);
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).navigate(R.id.action_register_to_meals);
            } else {

                Log.e(RegisterTest, "Registration failed. Message: " + message);
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
            Log.e(RegisterTest, "Server Response: " + response);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(RegisterTest, "JSON parsing error: " + e.getMessage());
        }
    }

    private void handleRegistrationError(VolleyError error) {
        Log.e(RegisterTest, "Volley error: " + error.getMessage(), error);

        if (error.networkResponse != null) {
            Log.e(RegisterTest, "Status Code: " + error.networkResponse.statusCode);
            Log.e(RegisterTest, "Response Data: " + new String(error.networkResponse.data));
        } else {
            Log.e(RegisterTest, "No network response");
        }

        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private Map<String, String> createRegistrationParams() {
        Map<String, String> data = new HashMap<>();
        data.put("username", registerUsername.getText().toString());
        data.put("password", registerPassword.getText().toString());
        data.put("confirmedPassword", confirmPassword.getText().toString());

        return data;
    }
}
