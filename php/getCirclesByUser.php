<?php
 
require_once 'invitationfunctions.php';

$db = new InvitationFunctions() ;


// json response array
$response = array("error" => FALSE);
if (isset($_POST['user_id'])) {
 
    // receiving the post params
    $user_id = $_POST['user_id'];

        $invitation = $db->getCirclesByUser($user_id);
}
?>