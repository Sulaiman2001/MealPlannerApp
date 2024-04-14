<?php


header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['user_id'], $_POST['meal_id'])) {
    $user_favourite_id = $_POST['user_id'];
    $meal_favourite_id = $_POST['meal_id'];


    include "conn.php";

    // Check if the record already exists
    $checkQuery = "SELECT * FROM favourites WHERE user_favourite_id = ? AND meal_favourite_id = ?";
    $checkStmt = mysqli_prepare($conn, $checkQuery);

    if ($checkStmt) {
        mysqli_stmt_bind_param($checkStmt, "ii", $user_favourite_id, $meal_favourite_id);
        mysqli_stmt_execute($checkStmt);
        mysqli_stmt_store_result($checkStmt);

        if (mysqli_stmt_num_rows($checkStmt) > 0) {
            // The record already exists
            $response['status'] = 'error';
            $response['message'] = 'Meal is already in favorites';
        } else {
            // The record doesn't exist insert the data into the table
            $insertQuery = "INSERT INTO favourites (user_favourite_id, meal_favourite_id) VALUES (?, ?)";
            $insertStmt = mysqli_prepare($conn, $insertQuery);

            if ($insertStmt) {
                mysqli_stmt_bind_param($insertStmt, "ii", $user_favourite_id, $meal_favourite_id);
                $result = mysqli_stmt_execute($insertStmt);

                if ($result) {
                    // Increment the favourite_count in the meal table
                    $updateQuery = "UPDATE meal SET favourite_count = favourite_count + 1 WHERE meal_id = ?";
                    $updateStmt = mysqli_prepare($conn, $updateQuery);

                    if ($updateStmt) {
                        mysqli_stmt_bind_param($updateStmt, "i", $meal_favourite_id);
                        mysqli_stmt_execute($updateStmt);
                        mysqli_stmt_close($updateStmt);
                    } else {
                        // Handle update statement preparation error
                        $response['status'] = 'error';
                        $response['message'] = 'Failed to prepare update statement';
                        echo json_encode($response);
                        exit;
                    }

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
