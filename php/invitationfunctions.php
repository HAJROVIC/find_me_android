<?php 
class InvitationFunctions 
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
 

    public function addMember($circle_code, $user_id) {


        
 
        $stmt = $this->conn->prepare("INSERT INTO invitations(circle_code, user_id, created_at, updated_at) VALUES(?, ?, NOW(), NOW())");
        $stmt->bind_param("si",$circle_code, $user_id);
                   
        $result = $stmt->execute();
        $stmt->close();

  
    }
  public function getCirclesByUser($iduser){

       $response["circles"]= array();
        $sql = "SELECT c.* FROM circles c , invitations i WHERE  c.code= i.circle_code AND i.user_id=$iduser
        UNION 
        SELECT * FROM `circles` WHERE `creator`=$iduser";
        
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