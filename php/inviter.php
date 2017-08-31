<?php
 
require_once 'invitationfunctions.php';
header("Access-Control-Allow-Origin: *"); 

$db = new InvitationFunctions() ;

// json response array
$response = array("error" => FALSE);
if (isset($_POST['circle_code']) && isset($_POST['user_id'])) {
 
    // receiving the post params
    $circle_code = $_POST['circle_code'];
    $user_id = $_POST['user_id'];

        $invitation = $db->addMember($circle_code, $user_id);
       
        if ($invitation) {
             $response["error"] = FALSE;
            echo json_encode($response);
                        }
         else{
                 $response["error"] = TRUE;
                 $response["error_msg"] = "You are already a member in this circle!";
                 echo json_encode($response);
            }
            
        }
       else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Write the code !";
    echo json_encode($response);
}
?>