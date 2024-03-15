<?php

include "conn.php";

$user_id = $_POST['user_id'];
$meal_id = $_POST['meal_id'];
$date = $_POST['date'];

$sql_meal_plan = "INSERT INTO meal_plan (user_id, meal_id, date) VALUES ('$user_id', '$meal_id', '$date')";

if ($conn->query($sql_meal_plan) === TRUE) {
    // Retrieve ingredients for the chosen meal
    $sql_get_ingredients = "SELECT * FROM ingredients WHERE meal_ingredient_id = '$meal_id'";
    $result_ingredients = $conn->query($sql_get_ingredients);

    if ($result_ingredients->num_rows > 0) {
        while ($row = $result_ingredients->fetch_assoc()) {
            $ingredient_id = $row['ingredient_id'];
            $quantity = $row['value']; // Adjust as needed

            // Insert into shopping_list with the correct meal ID
            $sql_shopping_list = "INSERT INTO shopping_list (user_id, ingredient_id, shopping_list_meal_id) VALUES ('$user_id', '$ingredient_id', '$meal_id')";
            $conn->query($sql_shopping_list);
        }

        $response['status'] = 'success';
        $response['message'] = 'Meal and ingredients added to the plan and shopping list successfully';
    } else {
        $response['status'] = 'error';
        $response['message'] = 'No ingredients found for the selected meal';
    }
} else {
    $response['status'] = 'error';
    $response['message'] = 'Error: ' . $conn->error;
}

$conn->close();

// Send the JSON response
header('Content-Type: application/json');
echo json_encode($response);

?>
