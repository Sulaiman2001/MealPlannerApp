package uk.ac.aston.cs3mdd.mealplanner;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import uk.ac.aston.cs3mdd.mealplanner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top-level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.meals, R.id.favourites, R.id.meal_plan, R.id.shopping_list)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //hides the nav bar from the log in and register page
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.log_in || destination.getId() == R.id.register) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                }
            } else {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().show();
                }
            }

            // Hide the BottomNavigationView on the login and register pages
            if (destination.getId() == R.id.log_in || destination.getId() == R.id.register) {
                navView.setVisibility(View.GONE);
            } else {
                navView.setVisibility(View.VISIBLE);
            }
        });

        NavigationUI.setupWithNavController(binding.navView, navController);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button press here
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            if (!navController.popBackStack()) {
                // If there is no back stack, finish the activity
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}