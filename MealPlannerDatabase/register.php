<?php
// register.php
include "conn.php";

if (isset($_POST['username']) && isset($_POST['password'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];

    // Hash the password before storing it in the database
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

    // SQL query to insert data into the users table
    $query = "INSERT INTO user (username, password) VALUES ('$username', '$hashedPassword')";

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
