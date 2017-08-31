<?php 

require_once 'circlefunctions.php';
header("Access-Control-Allow-Origin: *"); 

$db = new CircleFunctions();

// json response array
$response = array();

if (isset($_POST['title']) && isset($_POST['description']) && isset($_POST['code']) && isset($_POST['creator'])) {
 
    // receiving the post params
    $title = $_POST['title'];
    $description = $_POST['description'];
    $code = $_POST['code'];
    $creator = $_POST['creator'];
           

  if ($db->CodeExist($code))
    {
        $response["error"] = TRUE;
        $response["error_msg"]="This code does exist please try another one for your circle security";
        echo json_encode($response);
    }
        else{
        $circle = $db->storeCircle($title, $description, $code ,$creator);
  

            $response["error"] = FALSE;
            $response["error_msg"] = "Your circle has been created";
            $response["circle"]["id"]= $circle["id"];
            $response["circle"]["title"] = $circle["title"];
            $response["circle"]["description"] = $circle["description"];
            $response["circle"]["created_at"] = $circle["created_at"];
            $response["circle"]["updated_at"] = $circle["updated_at"];
            $response["circle"]["code"] = $circle["code"];
            $response["circle"]["creator"] = $circle["creator"];
            
echo json_encode($response);
    
} }
else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters is missing!";
    echo json_encode($response);
}
 ?>