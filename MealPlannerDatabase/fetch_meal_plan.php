<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include "conn.php";

header('Content-Type: application/json');

// Retrieve user_id from the request
$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : null;

// Check if 'user_id' is set in the request
if ($user_id === null) {
    $error = array('error' => 'user_id is not set in the request.');
    echo json_encode($error);
    exit;
}

$sql = "SELECT mp.date, m.meal_id, m.title, m.imagePath, m.vegan, m.vegetarian, m.time_to_cook, m.recipe, m.ingredients, m.serves 
        FROM meal_plan mp
        JOIN meal m ON mp.meal_id = m.meal_id
        WHERE mp.user_id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();

if ($stmt->error) {
    $error = array('error' => 'Error in executing SQL statement: ' . $stmt->error);
    echo json_encode($error);
    exit;
}

$result = $stmt->get_result();

$mealsArray = array();

while ($row = $result->fetch_assoc()) {
    $meal_id = $row['meal_id'];
    $title = $row['title'];
    $imageName = $row['imagePath'];
    $imagePath = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/images/$imageName";
    $vegan = $row['vegan'];
    $vegetarian = $row['vegetarian'];
    $time_to_cook = $row['time_to_cook'];
    $ingredients = $row['ingredients'];
    $recipe = $row['recipe'];
    $serves = $row['serves'];
    $date = $row['date'];

    // Create a MealPlan object
    $mealPlan = array(
        'meal_id' => $meal_id,
        'title' => $title,
        'imagePath' => $imagePath,
        'vegan' => $vegan,
        'vegetarian' => $vegetarian,
        'time_to_cook' => $time_to_cook,
        'recipe' => $recipe,
        'ingredients' => $ingredients,
        'serves' => $serves,
        'date' => $date
    );

    // Add the MealPlan object to the mealsArray
    $mealsArray[] = $mealPlan;
}

// Send a response object
$response = array('mealPlans' => $mealsArray); // Use 'mealPlans' instead of 'meals'

// Send an empty array if no meals are found
echo json_encode($response);

$stmt->close();
$conn->close();
?>
