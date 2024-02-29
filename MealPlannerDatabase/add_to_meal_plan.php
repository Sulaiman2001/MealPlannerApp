<?php

include "conn.php";

$user_id = $_POST['user_id'];
$meal_id = $_POST['meal_id'];
$date = $_POST['date'];

$sql = "INSERT into meal_plan (user_id, meal_id, date) VALUES ('$user_id', '$meal_id', '$date')";

if ($conn->query($sql) === TRUE) {
    $response['status'] = 'success';
    $response['message'] = 'Meal added to the plan successfully';
} else {
    $response['status'] = 'error';
    $response['message'] = 'Error: ' . $conn->error;
}

$conn->close();

// Send the JSON response
header('Content-Type: application/json');
echo json_encode($response);

?>