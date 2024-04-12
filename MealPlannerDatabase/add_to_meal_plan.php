<?php

include "conn.php";

$user_id = $_POST['user_id'];
$meal_id = $_POST['meal_id'];
$date = $_POST['date'];

$sql_meal_plan = "INSERT INTO meal_plan (user_id, meal_id, date) VALUES ('$user_id', '$meal_id', '$date')";
if ($conn->query($sql_meal_plan) === TRUE) {
    // Retrieve the last inserted meal_plan_id
    $meal_plan_id = $conn->insert_id;

    // Retrieve ingredients for the meal selected
    $sql_get_ingredients = "SELECT * FROM ingredients WHERE meal_ingredient_id = '$meal_id'";
    $result_ingredients = $conn->query($sql_get_ingredients);

    if ($result_ingredients->num_rows > 0) {
        $conn->begin_transaction();

        try {
            while ($row = $result_ingredients->fetch_assoc()) {
                $ingredient_id = $row['ingredient_id'];
                $quantity = $row['value']; 

                // Populate the shopping_list table
                $sql_shopping_list = "INSERT INTO shopping_list (user_id, ingredient_id, shopping_list_meal_id) VALUES ('$user_id', '$ingredient_id', '$meal_plan_id')";
                $conn->query($sql_shopping_list);
            }

            // Commit the transaction
            $conn->commit();

            $response['status'] = 'success';
            $response['message'] = 'Meal and ingredients added to the plan and shopping list successfully';
            $response['meal_plan_id'] = $meal_plan_id; 
        } catch (Exception $e) {
            $conn->rollback();

            $response['status'] = 'error';
            $response['message'] = 'Error inserting ingredients: ' . $e->getMessage();
        }
    } else {
        $response['status'] = 'error';
        $response['message'] = 'No ingredients found for the selected meal';
    }
} else {
    $response['status'] = 'error';
    $response['message'] = 'Error inserting meal plan: ' . $conn->error;
}

$conn->close();

// Send the JSON response
header('Content-Type: application/json');
echo json_encode($response);

?>
