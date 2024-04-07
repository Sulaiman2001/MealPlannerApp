<?php
// register.php
include "conn.php";

if (isset($_POST['email']) && isset($_POST['username']) && isset($_POST['password'])) {
    $email = $_POST['email'];
    $username = $_POST['username'];
    $password = $_POST['password'];

    // Check if email already exists in the database
    $check_query = "SELECT * FROM user WHERE email='$email'";
    $check_result = $conn->query($check_query);

    if ($check_result->num_rows > 0) {
        echo json_encode(array("status" => "error", "message" => "Email already exists"));
        exit(); // Exit the script if email already exists
    }

    // Hash the password before storing it in the database
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

    // SQL query to insert data into the users table
    $query = "INSERT INTO user (email, username, password) VALUES ('$email', '$username', '$hashedPassword')";

    // Execute the query and check for success
    if ($conn->query($query) === TRUE) {
        echo json_encode(array("status" => "success", "message" => "Registration successful"));
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
