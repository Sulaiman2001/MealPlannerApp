<?php

include "conn.php";

$user_id = $_POST['user_id'];

// Assuming you have a column in your shopping_list table named 'ingredient_id'
$sql = "SELECT ingredients.ingredient_id, ingredients.ingredient_name, SUM(ingredients.value) AS total_value, ingredients.unit
        FROM shopping_list
        INNER JOIN ingredients ON shopping_list.ingredient_id = ingredients.ingredient_id
        WHERE shopping_list.user_id = '$user_id'
        GROUP BY ingredients.ingredient_id, ingredients.unit
        ORDER BY ingredients.ingredient_name ASC";




$result = $conn->query($sql);

$response = array();

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $ingredientName = $row['ingredient_name'];
        $value = $row['total_value'];
        $unit = $row['unit'];


        // Add the ingredient to the response array
        $response[] = array(
            "ingredientName" => $ingredientName,
            "total_value" => $value,
            "unit" => $unit
        );
        
    }

    // Send JSON response
    header('Content-Type: application/json');
    echo json_encode($response);
} else {
    // No ingredients found
    $response['status'] = 'error';
    $response['message'] = 'No ingredients found in the shopping list for the user';

    // Send JSON response
    header('Content-Type: application/json');
    echo json_encode($response);
}

$conn->close();

?>
