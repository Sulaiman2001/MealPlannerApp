<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include "conn.php";

header('Content-Type: application/json');

// Retrieve user_id and meal_id from the request
$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : null;
$meal_id = isset($_POST['meal_id']) ? $_POST['meal_id'] : null;
$meal_plan_id = isset($_POST['meal_plan_id']) ? $_POST['meal_plan_id'] : null;


// Check if 'user_id' and 'meal_id' are set in the request
if ($user_id === null || $meal_id === null) {
    $error = array('error' => 'user_id or meal_id is not set in the request.');
    echo json_encode($error);
    exit;
}

// Delete ingredients from shopping_list
$sql_delete_ingredients = "DELETE FROM shopping_list WHERE user_id = ? AND shopping_list_meal_id = ?";
$stmt_delete_ingredients = $conn->prepare($sql_delete_ingredients);
$stmt_delete_ingredients->bind_param("ii", $user_id, $meal_plan_id);
$stmt_delete_ingredients->execute();

if ($stmt_delete_ingredients->error) {
    $error = array('error' => 'Error in executing SQL statement (shopping_list): ' . $stmt_delete_ingredients->error);
    echo json_encode($error);
    exit;
}

// Delete meal from meal_plan
$sql_delete_meal = "DELETE FROM meal_plan WHERE user_id = ? AND meal_plan_id = ?";
$stmt_delete_meal = $conn->prepare($sql_delete_meal);
$stmt_delete_meal->bind_param("ii", $user_id, $meal_plan_id);
$stmt_delete_meal->execute();

if ($stmt_delete_meal->error) {
    $error = array('error' => 'Error in executing SQL statement (meal_plan): ' . $stmt_delete_meal->error);
    echo json_encode($error);
    exit;
}

// Send a success response
$response = array('status' => 'success', 'message' => 'Meal and corresponding ingredients deleted successfully.');
echo json_encode($response);

$stmt_delete_ingredients->close();
$stmt_delete_meal->close();
$conn->close();
?>
