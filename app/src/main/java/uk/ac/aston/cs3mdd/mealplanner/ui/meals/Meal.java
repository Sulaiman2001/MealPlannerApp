package uk.ac.aston.cs3mdd.mealplanner.ui.meals;

public class Meal {
    private Integer mealID;
    private String title;
    private String imagePath;
    private Boolean isVegan;
    private Boolean isVegetarian;
    private Integer cookingTime;
    private String recipe;
    private String ingredients;
    private Integer serves;
    private Integer favouriteCount;



    public Meal(Integer mealID, String title, String imagePath, Boolean isVegetarian, Boolean isVegan, Integer cookingTime, String recipe, String ingredients, Integer serves, Integer favouriteCount) {
        this.mealID = mealID;
        this.title = title;
        this.imagePath = imagePath;
        this.isVegan = isVegan;
        this.isVegetarian = isVegetarian;
        this.cookingTime = cookingTime;
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.serves = serves;
        this.favouriteCount = favouriteCount;
    }

    public Integer getMealID() {
        return mealID;
    }
    public String getTitle() {
        return title;
    }
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

}
