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

    // Check if the record already exists
    $checkQuery = "SELECT * FROM favourites WHERE user_favourite_id = ? AND meal_favourite_id = ?";
    $checkStmt = mysqli_prepare($conn, $checkQuery);

    if ($checkStmt) {
        mysqli_stmt_bind_param($checkStmt, "ii", $user_favourite_id, $meal_favourite_id);
        mysqli_stmt_execute($checkStmt);
        mysqli_stmt_store_result($checkStmt);

        if (mysqli_stmt_num_rows($checkStmt) > 0) {
            // The record already exists, handle accordingly (e.g., set error response)
            $response['status'] = 'error';
            $response['message'] = 'Meal is already in favorites';
        } else {
            // The record doesn't exist, proceed with the insertion
            $insertQuery = "INSERT INTO favourites (user_favourite_id, meal_favourite_id, is_favourited) VALUES (?, ?, ?)";
            $insertStmt = mysqli_prepare($conn, $insertQuery);

            if ($insertStmt) {
                mysqli_stmt_bind_param($insertStmt, "iii", $user_favourite_id, $meal_favourite_id, $is_favourited);
                $result = mysqli_stmt_execute($insertStmt);

                if ($result) {
                    $response['status'] = 'success';
                    $response['message'] = 'Meal added to favorites successfully';
                } else {
                    $response['status'] = 'error';
                    $response['message'] = 'Failed to add meal to favorites';
                    $response['error'] = mysqli_error($conn);
                }

                mysqli_stmt_close($insertStmt);
            } else {
                $response['status'] = 'error';
                $response['message'] = 'Failed to prepare insert statement';
            }
        }

        mysqli_stmt_close($checkStmt);
    } else {
        $response['status'] = 'error';
        $response['message'] = 'Failed to prepare check statement';
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
