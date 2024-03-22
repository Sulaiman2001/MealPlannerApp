package uk.ac.aston.cs3mdd.mealplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import uk.ac.aston.cs3mdd.mealplanner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top-level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.meals, R.id.favourites, R.id.meal_plan, R.id.shopping_list)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Hides the nav bar from the log in and register page
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

        // Manually handle the item selection to update the visual state
        navView.setOnNavigationItemSelectedListener(item -> {
            item.setChecked(true); // Update the checked state of the item
            return NavigationUI.onNavDestinationSelected(item, navController); // Navigate to the selected destination
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_sign_out) {
            // Display confirmation dialog before signing out
            showSignOutConfirmationDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showSignOutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked the Yes button, proceed with sign out
                        signOut();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    private void signOut() {
        SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // Clear all stored user data
        editor.apply();

        // Navigate back to the login screen
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_global_loginFragment); // Assuming action_global_loginFragment is the action to navigate to the login fragment
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}