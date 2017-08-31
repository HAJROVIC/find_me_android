<?php
 header("Access-Control-Allow-Origin: *"); 

require_once 'include/DB_Functions.php';

$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

 
    // receiving the post params
   $circle_id = $_POST['circle_id'];
   

        $invitation = $db->getUserByCircle($circle_id);

?>