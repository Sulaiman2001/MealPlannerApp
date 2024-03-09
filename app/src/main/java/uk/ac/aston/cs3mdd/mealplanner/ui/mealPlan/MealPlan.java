package uk.ac.aston.cs3mdd.mealplanner.ui.mealPlan;

import uk.ac.aston.cs3mdd.mealplanner.ui.meals.Meal;

public class MealPlan extends Meal {

    private String date;

    public MealPlan(Integer mealID, String title, String imagePath, Boolean isVegetarian, Boolean isVegan, Integer cookingTime, String recipe, String ingredients, Integer serves, String date, Integer favouriteCount) {
        super(mealID, title, imagePath, isVegetarian, isVegan, cookingTime, recipe, ingredients, serves, favouriteCount);
        this.date = date;
    }


    public String getDate() {
        return date;
    }
}
