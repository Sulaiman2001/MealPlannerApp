package uk.ac.aston.cs3mdd.mealplanner.ui.meals;

public class Meal {
    private String title;
    private String imagePath;
    private String isVegan;
    private String isVegetarian;


    public Meal(String title, String imagePath, String isVegetarian, String isVegan) {
        this.title = title;
        this.imagePath = imagePath;
        this.isVegan = isVegan;
        this.isVegetarian = isVegetarian;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getIsVegan() {
        return isVegan;
    }

    public String getIsVegetarian() {
        return isVegetarian;
    }


}
