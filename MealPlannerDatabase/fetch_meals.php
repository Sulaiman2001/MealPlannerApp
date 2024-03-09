<?php
include "conn.php";

$sql = "SELECT meal_id, title, imagePath, vegan, vegetarian, time_to_cook, recipe, ingredients, serves, favourite_count FROM meal"; 
$result = $conn->query($sql);



if ($result->num_rows > 0) {
    $mealsArray = array();

    while ($row = $result->fetch_assoc()) {
        $meal_id = $row['meal_id'];

        $title = $row['title'];
        $imageName = $row['imagePath'];
        
        $imagePath = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/images/$imageName";

        $vegan = $row['vegan'];

        $vegetarian = $row['vegetarian'];

        $time_to_cook = $row['time_to_cook'];

        $ingredients = $row['ingredients'];

        $recipe = $row['recipe'];

        $serves = $row['serves'];

        $favourite_count = $row['favourite_count'];


        $mealsArray[] = array('meal_id' => $meal_id, 'title' => $title, 'imagePath' => $imagePath, 'vegan' => $vegan, 'vegetarian' => $vegetarian, 'time_to_cook' => $time_to_cook, 'recipe' => $recipe, 'ingredients' => $ingredients, 'serves' => $serves, 'favourite_count' => $favourite_count);
    }

    echo json_encode($mealsArray);
} else {
    echo json_encode(array('message' => 'No meals found'));
}

$conn->close();
?>

