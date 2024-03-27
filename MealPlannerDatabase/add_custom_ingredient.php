<?php

include "conn.php";

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// Initialize response array
$response = array();

// Check if all required parameters are provided
if(isset($_POST['user_id']) && isset($_POST['ingredient_name']) && isset($_POST['amount'])) {
    // Extract parameters from POST request
    $custom_user_id = $_POST['user_id'];
    $ingredient_name = $_POST['ingredient_name'];
    $amount = $_POST['amount'];

    // Insert the new ingredient into the custom_ingredients table
    $query = "INSERT INTO custom_ingredients (custom_user_id, ingredient_name, amount) VALUES ('$custom_user_id', '$ingredient_name', '$amount')";
    $result = mysqli_query($conn, $query);
    
    if ($result) {
        // Successfully inserted
        $response['status'] = 'success';
        $response['message'] = 'Ingredient added successfully';
    } else {
        // Failed to insert
        $response['status'] = 'error';
        $response['message'] = 'Failed to add ingredient';
    }
} else {
    // Required parameters not provided
    $response['status'] = 'error';
    $response['message'] = 'Missing parameters';
}

// Write detailed error message to the error log
error_log(json_encode($response), 0);

// Send response back to the app
echo json_encode($response);

?>
