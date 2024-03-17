<?php
include "conn.php";

// Check if the request contains a parameter to filter vegan meals
$filterByVegan = isset($_GET['vegan']) ? $_GET['vegan'] : null;
$filterByVegetarian = isset($_GET['vegetarian']) ? $_GET['vegetarian'] : null;
$filterByCookingTime = isset($_GET['cooking_time']) ? $_GET['cooking_time'] : null;
$filterByMealType = isset($_GET['meal_type']) ? $_GET['meal_type'] : null;

$sql = "SELECT meal_id, title, meal_type, imagePath, vegan, vegetarian, time_to_cook, recipe, ingredients, serves, favourite_count FROM meal";

// If the filterByVegan parameter is provided and is set to 1, filter vegan meals
if ($filterByVegan == 1) {
    $sql .= " WHERE vegan = 1";
}

// If the filterByVegetarian parameter is provided and is set to 1, filter vegan meals
if ($filterByVegetarian == 1) {
    $sql .= " WHERE vegetarian = 1";
}

if ($filterByCookingTime == 1) {
    $sql .= " WHERE time_to_cook < 60";
}

if (!empty($filterByMealType)) {
    $sql .= " WHERE meal_type = '$filterByMealType'";
}

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $mealsArray = array();

    while ($row = $result->fetch_assoc()) {
        $meal_id = $row['meal_id'];

        $title = $row['title'];

        $mealType = $row['meal_type'];
        
        $imageName = $row['imagePath'];

        $imagePath = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/images/$imageName";

        $vegan = $row['vegan'];

        $vegetarian = $row['vegetarian'];

        $time_to_cook = $row['time_to_cook'];

        $ingredients = $row['ingredients'];

        $recipe = $row['recipe'];

        $serves = $row['serves'];

        $favourite_count = $row['favourite_count'];

        $mealsArray[] = array('meal_id' => $meal_id, 'title' => $title, 'meal_type' => $mealType, 'imagePath' => $imagePath, 'vegan' => $vegan, 'vegetarian' => $vegetarian, 'time_to_cook' => $time_to_cook, 'recipe' => $recipe, 'ingredients' => $ingredients, 'serves' => $serves, 'favourite_count' => $favourite_count);
    }

    echo json_encode($mealsArray);
} else {
    echo json_encode(array('message' => 'No meals found'));
}

$conn->close();
?>
