<?php

include "conn.php";
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);



// Set content type header to ensure proper JSON handling
header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['user_id'], $_POST['meal_id'], $_POST['is_favourited'])) {
    $user_favourite_id = $_POST['user_id'];
    $meal_favourite_id = $_POST['meal_id'];
    $is_favourited = $_POST['is_favourited'];

    $is_favourited = $is_favourited == 1 ? 1 : 0;

    // Use prepared statement to prevent SQL injection
    $query = "INSERT INTO favourites (user_favourite_id, meal_favourite_id, is_favourited) VALUES (?, ?, ?)";
    $stmt = mysqli_prepare($conn, $query);

    // Check for a valid prepared statement 
    if ($stmt) {
        mysqli_stmt_bind_param($stmt, "iii", $user_favourite_id, $meal_favourite_id, $is_favourited);
        $result = mysqli_stmt_execute($stmt);

        if ($result) {
            $response['status'] = 'success';
            $response['message'] = 'Meal added to favorites successfully';
        } else {
            $response['status'] = 'error';
            $response['message'] = 'Failed to add meal to favorites';
            $response['error'] = mysqli_error($connection);
        }

        mysqli_stmt_close($stmt);
    } else {
        $response['status'] = 'error';
        $response['message'] = 'Failed to prepare statement';
    }
} else {
    $response['status'] = 'error';
    $response['message'] = 'Invalid or missing parameters';
}

// Log received parameters and detailed error information
error_log("Received Parameters: " . print_r($_POST, true));
error_log("Response: " . json_encode($response));

// Echo the JSON response
echo json_encode($response);

?>
