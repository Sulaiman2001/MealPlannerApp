<?php
// login.php
include "conn.php";

if (isset($_POST['username']) && isset($_POST['password'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];

    // SQL query to retrieve user data based on the provided username
    $query = "SELECT * FROM user WHERE username = '$username' LIMIT 1";
    $result = $conn->query($query);

    // Check if a user with the provided username exists
    if ($result->num_rows > 0) {
        $user = $result->fetch_assoc();

        
        // Use password_verify() to check the password
        if (password_verify($password, $user['password'])) {
            // Password is correct, return user data
            echo json_encode(array("status" => "success", "message" => "Login successful", "user_id" => $user['user_id'], "user" => $user));
        } else {
            // Password is incorrect
            echo json_encode(array("status" => "error", "message" => "Incorrect password"));
        }
    } else {
        // User with the provided username doesn't exist
        echo json_encode(array("status" => "error", "message" => "User not found"));
    }
} else {
    // Invalid parameters
    echo json_encode(array("status" => "error", "message" => "Invalid parameters"));
}

// Close the database
$conn->close();
?>
