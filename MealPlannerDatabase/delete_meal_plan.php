<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include "conn.php";

header('Content-Type: application/json');

// Retrieve user_id and meal_id from the request
$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : null;
$meal_id = isset($_POST['meal_id']) ? $_POST['meal_id'] : null;

// Check if 'user_id' and 'meal_id' are set in the request
if ($user_id === null || $meal_id === null) {
    $error = array('error' => 'user_id or meal_id is not set in the request.');
    echo json_encode($error);
    exit;
}

$sql = "DELETE FROM meal_plan WHERE user_id = ? AND meal_id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ii", $user_id, $meal_id);
$stmt->execute();

if ($stmt->error) {
    $error = array('error' => 'Error in executing SQL statement: ' . $stmt->error);
    echo json_encode($error);
    exit;
}

// Send a success response
$response = array('status' => 'success', 'message' => 'Meal deleted from favorites successfully.');
echo json_encode($response);

$stmt->close();
$conn->close();
?>
