<?php

include "conn.php";

$user_id = $_POST['user_id'];

// Check if ingredient ID is provided for deletion
if(isset($_POST['ingredient_id'])) {
    $ingredient_id = $_POST['ingredient_id'];
    $is_custom = $_POST['is_custom']; // Add this line to get the is_custom parameter

    // Check if the ingredient is custom or standard
    if ($is_custom == 'true') {
        // Delete the ingredient from the custom_ingredients table
        $deleteCustomSql = "DELETE FROM custom_ingredients WHERE custom_ingredient_id = '$ingredient_id' AND custom_user_id = '$user_id'";
        
        // Execute the delete query
        if ($conn->query($deleteCustomSql) === TRUE) {
            $response['status'] = 'success';
            $response['message'] = 'Custom ingredient deleted successfully';
        } else {
            $response['status'] = 'error';
            $response['message'] = 'Error deleting custom ingredient: ' . $conn->error;
        }
    } else {
        // Delete the ingredient from the shopping_list table
        $deleteShoppingListSql = "DELETE FROM shopping_list WHERE user_id = '$user_id' AND ingredient_id = '$ingredient_id'";
        
        // Execute the delete query
        if ($conn->query($deleteShoppingListSql) === TRUE) {
            $response['status'] = 'success';
            $response['message'] = 'Standard ingredient deleted successfully';
        } else {
            $response['status'] = 'error';
            $response['message'] = 'Error deleting standard ingredient: ' . $conn->error;
        }
    }

    // Send JSON response
    header('Content-Type: application/json');
    echo json_encode($response);
    exit; // Stop further execution
}

?>
