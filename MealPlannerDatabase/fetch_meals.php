<?php
include "conn.php";

$sql = "SELECT title, imagePath, vegan, vegetarian FROM meal"; 
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $mealsArray = array();

    while ($row = $result->fetch_assoc()) {
        $title = $row['title'];
        $imageName = $row['imagePath'];
        
        $imagePath = "http://192.168.1.82/FinalYearApp/Application/MealPlannerApp/images/$imageName";

        $vegan = $row['vegan'];

        $vegetarian = $row['vegetarian'];

        $mealsArray[] = array('title' => $title, 'imagePath' => $imagePath, 'vegan' => $vegan, 'vegetarian' => $vegetarian);
    }

    echo json_encode($mealsArray);
} else {
    echo json_encode(array('message' => 'No meals found'));
}

$conn->close();
?>
