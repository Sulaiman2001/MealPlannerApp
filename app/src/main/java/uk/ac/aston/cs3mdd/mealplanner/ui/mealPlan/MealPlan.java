package uk.ac.aston.cs3mdd.mealplanner.ui.mealPlan;

import uk.ac.aston.cs3mdd.mealplanner.ui.meals.Meal;

public class MealPlan extends Meal {

    private Integer mealPlanID;
    private String date;

    // Store the inherited attributes from the meal class
    public MealPlan(Integer mealPlanID, Integer mealID, String title, String mealType, String imagePath, Boolean isVegetarian, Boolean isVegan, Integer cookingTime, String recipe, String ingredients, Integer serves, String date, Integer favouriteCount, Integer calories) {
        super(mealID, title, mealType, imagePath, isVegetarian, isVegan, cookingTime, recipe, ingredients, serves, favouriteCount, calories);
        // Store the new parameters
        this.mealPlanID = mealPlanID;
        this.date = date;
    }

    public Integer getMealPlanID() {
        return mealPlanID;
    }
    public String getDate() {
        return date;
    }
}
