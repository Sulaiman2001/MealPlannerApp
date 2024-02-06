package uk.ac.aston.cs3mdd.mealplanner.ui.meals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uk.ac.aston.cs3mdd.mealplanner.R;

public class MealDetailsFragment extends Fragment {


    public MealDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_details, container, false);

        // Retrieve data from arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("title");
            String imagePath = bundle.getString("imagePath");
            String isVegetarian = bundle.getString("isVegetarian");
            String isVegan = bundle.getString("isVegan");

            // Set the data to the views in your fragment
            TextView titleTextView = rootView.findViewById(R.id.titleTextView);
            ImageView imageView = rootView.findViewById(R.id.imageView);
            TextView vegetarianTextView = rootView.findViewById(R.id.vegetarianTextView);
            TextView veganTextView = rootView.findViewById(R.id.veganTextView);

            titleTextView.setText(title);
            Picasso.get().load(imagePath).into(imageView);
            vegetarianTextView.setText(isVegetarian);
            veganTextView.setText(isVegan);
        }

        return rootView;
    }
}