<?php
include "conn.php";

// Check if the data has been set
if (isset($_POST['email']) && isset($_POST['username']) && isset($_POST['password'])) {
    // Stores the data in a variable
    $email = $_POST['email'];
    $username = $_POST['username'];
    $password = $_POST['password'];

    // Check if email already exists in the database
    $check_email_query = "SELECT * FROM user WHERE email='$email'";
    $check_email_result = $conn->query($check_email_query);

    // Check if username already exists in the database
    $check_username_query = "SELECT * FROM user WHERE username='$username'";
    $check_username_result = $conn->query($check_username_query);

    // Check for email existence
    if ($check_email_result->num_rows > 0) {
        echo json_encode(array("status" => "error", "message" => "Email already exists"));
        exit(); 
    }

    // Check for username existence
    if ($check_username_result->num_rows > 0) {
        echo json_encode(array("status" => "error", "message" => "Username already exists"));
        exit(); 
    }

    // Hash the password
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

    // SQL query to insert data into the users table
    $query = "INSERT INTO user (email, username, password) VALUES ('$email', '$username', '$hashedPassword')";

    // Execute the query and check for success
    if ($conn->query($query) === TRUE) {
        $user_id = $conn->insert_id; // Retrieve the newly inserted user's ID
        echo json_encode(array("status" => "success", "message" => "Registration successful", "user_id" => $user_id));
    } else {
        echo json_encode(array("status" => "error", "message" => "Error in registration query: " . $conn->error));
        error_log("Error in registration query: " . $conn->error);
    }
} else {
    echo json_encode(array("status" => "error", "message" => "Invalid parameters"));
}

// Close the database connection
$conn->close();
?>
