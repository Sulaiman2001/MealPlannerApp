package uk.ac.aston.cs3mdd.mealplanner.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uk.ac.aston.cs3mdd.mealplanner.R;


public class RegisterFragment extends Fragment {



    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        Button returnToLogIn = rootView.findViewById(R.id.returnToLogIn);
        returnToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to RegisterFragment
                Navigation.findNavController(view).navigate(R.id.action_register_to_log_in);
            }
        });

        return rootView;
    }
}