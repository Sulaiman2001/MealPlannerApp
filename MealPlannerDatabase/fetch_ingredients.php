<?php

include "conn.php";

$user_id = $_POST['user_id'];

$response = array(); // Initialize the response array

// Fetch standard ingredients
$sql_standard = "SELECT ingredients.ingredient_id, ingredients.ingredient_name, SUM(ingredients.value) AS total_value, ingredients.unit
                 FROM shopping_list
                 INNER JOIN ingredients ON shopping_list.ingredient_id = ingredients.ingredient_id
                 WHERE shopping_list.user_id = '$user_id'
                 GROUP BY ingredients.ingredient_id, ingredients.unit";

$result_standard = $conn->query($sql_standard);

// Fetch custom ingredients for the user
$sql_custom = "SELECT custom_ingredients.custom_ingredient_id, custom_ingredients.ingredient_name, custom_ingredients.amount AS unit
               FROM custom_ingredients
               WHERE custom_ingredients.custom_user_id = '$user_id'";

$result_custom = $conn->query($sql_custom);

// Combine standard and custom ingredients into a single array
$allIngredients = array();

if ($result_standard->num_rows > 0) {
    while ($row = $result_standard->fetch_assoc()) {
        $ingredientId = $row['ingredient_id']; // This line should be inside the loop
        $ingredientName = $row['ingredient_name'];
        $value = $row['total_value'];
        $unit = $row['unit'];

        // Fetch the meals associated with the ingredient
        $ingredientId = $row['ingredient_id'];
        $mealsSql = "SELECT meal.title
                     FROM meal
                     INNER JOIN ingredients ON meal.meal_id = ingredients.meal_ingredient_id
                     WHERE ingredients.ingredient_id = '$ingredientId'";

        $mealsResult = $conn->query($mealsSql);
        $meals = array();

        if ($mealsResult->num_rows > 0) {
            while ($mealRow = $mealsResult->fetch_assoc()) {
                $meals[] = $mealRow['title'];
            }
        }

        // Add the standard ingredient to the combined array
        $allIngredients[] = array(
            "ingredientId" => $ingredientId,
            "ingredientName" => $ingredientName,
            "total_value" => $value,
            "unit" => $unit,
            "meals" => $meals,
            "isCustom" => false // Standard ingredients are not custom
        );
    }
}

if ($result_custom->num_rows > 0) {
    while ($row = $result_custom->fetch_assoc()) {
        $customIngredientId = $row['custom_ingredient_id']; // Get the custom ingredient ID
        $ingredientName = $row['ingredient_name'];
        $unit = $row['unit']; // Store the amount in the "unit" field

        // Add the custom ingredient to the combined array
        $allIngredients[] = array(
            "ingredientId" => $customIngredientId, // Use custom_ingredient_id as the ingredient ID
            "ingredientName" => $ingredientName,
            "total_value" => "", // Leave total_value blank for custom ingredients
            "unit" => $unit, // Store the amount in the "unit" field
            "meals" => array(), // Custom ingredients may not be associated with meals
            "isCustom" => true // Custom ingredients are marked as custom
        );
    }
}


// Sort the combined array alphabetically by ingredient name
usort($allIngredients, function($a, $b) {
    return strcmp($a['ingredientName'], $b['ingredientName']);
});

// Send JSON response
header('Content-Type: application/json');
echo json_encode($allIngredients);

$conn->close();

?>
