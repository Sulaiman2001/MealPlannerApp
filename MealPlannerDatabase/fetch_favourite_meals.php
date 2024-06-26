<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include "conn.php";

header('Content-Type: application/json');

// Retrieve user_id from the request
$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : null;
// Add this line to log the received data
error_log("Received user_id: " . print_r($user_id, true));
error_log("Received data: " . print_r($_POST, true));

// Check if 'user_id' is set in the request
if ($user_id === null) {
    $error = array('error' => 'user_id is not set in the request.');
    echo json_encode($error);
    exit;
}

$sql = "SELECT m.meal_id, m.title, m.meal_type, m.imagePath, m.vegan, m.vegetarian, m.time_to_cook, m.recipe, m.ingredients, m.serves, m.favourite_count, m.calories 
        FROM meal m
        JOIN favourites f ON m.meal_id = f.meal_favourite_id
        WHERE f.user_favourite_id = ?";
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
    $calories = $row['calories'];


    $mealsArray[] = array(
        'meal_id' => $meal_id,
        'title' => $title,
        'meal_type' => $mealType,
        'imagePath' => $imagePath,
        'vegan' => $vegan,
        'vegetarian' => $vegetarian,
        'time_to_cook' => $time_to_cook,
        'recipe' => $recipe,
        'ingredients' => $ingredients,
        'serves' => $serves,
        'favourite_count' => $favourite_count,
        'calories' => $calories

    );
}

// Send a response object
$response = array('meals' => $mealsArray);

// Send an empty array if no meals are found
echo json_encode($response);

$stmt->close();
$conn->close();
?>
