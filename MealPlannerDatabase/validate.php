<?php
//validation fucntion
function validate($data){
    //gets rid of any spaces from user input - uses 'trim'
    $data = trim($data);

    //avoid exploits and hackers 
    $data = htmlspecialchars($data);

    return $data;
}
?>