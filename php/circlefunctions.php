<?php 
header("Access-Control-Allow-Origin: *"); 

class CircleFunctions 
{
     private $conn;

    function __construct()
    {
         require_once 'include/DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
     // destructor
    function __destruct() {
         
    }
 public function CodeExist($code) {
        $stmt = $this->conn->prepare("SELECT code from circles WHERE code = ?");
 
        $stmt->bind_param("s", $code);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }

    public function storeCircle($title, $description, $code ,$creator) {

       $stmt = $this->conn->prepare("INSERT INTO circles(title, description, created_at, updated_at,code, creator) VALUES(?, ?, NOW(), NOW(), ?, ?)");
        $stmt->bind_param("ssss",$title, $description, $code ,$creator);
        $result = $stmt->execute();
        $stmt->close();
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM circles WHERE code= ?");
            $stmt->bind_param("s", $code);
            $stmt->execute();
            $circle = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $circle;
        } else {
            return false;
        }

  
    }
    public function getAllCircles(){

       $response["circles"]= array();
        $sql = "SELECT * FROM `circles`";
        $result = $this->conn->query($sql);

    if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc())
    {
       $sujet = array();
        $sujet["id"]  = $row["id"];
        $sujet["title"]  = $row["title"];
        $sujet["description"]=  $row["description"];
        $sujet["created_at"]=  $row["created_at"];
        $sujet["updated_at"]=  $row["updated_at"];
        $sujet["code"]=  $row["code"];
        $sujet["creator"]=  $row["creator"];
        array_push($response["circles"], $sujet);
    }
    echo json_encode($response);
    } else 
        {
         echo "0 results";
        }
    $result->close(); 
    }  
       public function getCirclesCreatedByUser($user_id){

       $response["circles"]= array();
        $sql = "SELECT * FROM `circles` where creator = '$user_id'";
        $result = $this->conn->query($sql);

    if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc())
    {
       $sujet = array();
        $sujet["id"]  = $row["id"];
        $sujet["title"]  = $row["title"];
        $sujet["description"]=  $row["description"];
        $sujet["created_at"]=  $row["created_at"];
        $sujet["updated_at"]=  $row["updated_at"];
        $sujet["code"]=  $row["code"];
        $sujet["creator"]=  $row["creator"];
        array_push($response["circles"], $sujet);
    }
    echo json_encode($response);
    } else 
        {
         echo "0 results";
        }
    $result->close(); 
    }   
}

 ?>