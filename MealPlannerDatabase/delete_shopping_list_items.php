<?php

include "conn.php";

$user_id = $_POST['user_id'];

// Check if ingredient ID is provided for deletion
if(isset($_POST['ingredient_id'])) {
    $ingredient_id = $_POST['ingredient_id'];

    // SQL query to delete the ingredient from the shopping list
    $deleteSql = "DELETE FROM shopping_list WHERE user_id = '$user_id' AND ingredient_id = '$ingredient_id'";
    
    // Execute the delete query
    if ($conn->query($deleteSql) === TRUE) {
        $response['status'] = 'success';
        $response['message'] = 'Ingredient deleted successfully';
    } else {
        $response['status'] = 'error';
        $response['message'] = 'Error deleting ingredient: ' . $conn->error;
    }

    // Send JSON response
    header('Content-Type: application/json');
    echo json_encode($response);
    exit; // Stop further execution
}
?>