package uk.ac.aston.cs3mdd.mealplanner.ui.meals;

public class Meal {
    private Integer mealID;
    private String title;
    private String mealType;
    private String imagePath;
    private Boolean isVegan;
    private Boolean isVegetarian;
    private Integer cookingTime;
    private String recipe;
    private String ingredients;
    private Integer serves;
    private Integer favouriteCount;
    private Integer calories;



    public Meal(Integer mealID, String title,String mealType, String imagePath, Boolean isVegetarian, Boolean isVegan, Integer cookingTime, String recipe, String ingredients, Integer serves, Integer favouriteCount, Integer calories) {
        this.mealID = mealID;
        this.title = title;
        this.mealType = mealType;
        this.imagePath = imagePath;
        this.isVegan = isVegan;
        this.isVegetarian = isVegetarian;
        this.cookingTime = cookingTime;
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.serves = serves;
        this.favouriteCount = favouriteCount;
        this.calories = calories;
    }

    public Integer getMealID() {
        return mealID;
    }
    public String getTitle() {
        return title;
    }
    public String getMealType() {return mealType;}
    public String getImagePath() {
        return imagePath;
    }
    public Boolean getIsVegan() {
        return isVegan;
    }
    public Boolean getIsVegetarian() {
        return isVegetarian;
    }
    public Integer getCookingTime() {
        return cookingTime;
    }
    public String getRecipe() {
        return recipe;
    }
    public String getIngredients() {
        return ingredients;
    }
    public Integer getServes() { return serves; }
    public Integer getFavouriteCount(){ return favouriteCount;}
    public Integer getCalories() {
        return calories;
    }

}
