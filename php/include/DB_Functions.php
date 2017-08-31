<?php
 
header("Access-Control-Allow-Origin: *"); 

class DB_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
         
    }
 
    // destructor
    function __destruct() {
         
    }
 
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name, $email, $password,$phone) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
 
        $stmt = $this->conn->prepare("INSERT INTO users(unique_id, name, email, encrypted_password, salt, created_at,phone) VALUES(?, ?, ?, ?, ?, NOW(),?)");
        $stmt->bind_param("ssssss", $uuid, $name, $email, $encrypted_password, $salt, $phone);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        } else {
            return false;
        }
    }
 
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password) {
 
        $stmt = $this->conn->prepare("SELECT * FROM users WHERE email = ?");
 
        $stmt->bind_param("s", $email);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            // verifying user password
            $salt = $user['salt'];
            $encrypted_password = $user['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $stmt = $this->conn->prepare("SELECT email from users WHERE email = ?");
 
        $stmt->bind_param("s", $email);
 
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
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
     public function updateUserImage($email, $image) {
 
        $stmt = $this->conn->prepare("UPDATE `users` SET `photo`=? WHERE `email`= ? ");
 
        $stmt->bind_param("ss",$image , $email);
        $stmt->execute();
        $stmt->close();

 
    }

    public function getUserByCircle($idcircle){
         $sql1 = "SELECT code FROM circles WHERE  id=$idcircle ";
        $code = $this->conn->query($sql1);
        //var_dump($code->fetch_assoc()["code"]);
         $response = array();        
       $response["users"]= array();
        $sql = 'SELECT u.* FROM users u , invitations i WHERE  u.id= i.user_id AND i.circle_code='.$code->fetch_assoc()["code"];
        $result = $this->conn->query($sql);
       


    while($row = mysqli_fetch_array($result))
    {
        $users = array();
        $users["id"]  = $row["id"];
        $users["name"]  = $row["name"];
        $users["photo"]=  $row["photo"];
        $users["position"]=  $row["position"];
       
        array_push($response["users"], $users);
    }
    echo json_encode($response);
    $result->close(); 
    }  
    public function updateUserPosition($user_id, $position) {
 
        $stmt = $this->conn->prepare("UPDATE `users` SET `position`=? WHERE `id`= ? ");
 
        $stmt->bind_param("si",$position , $user_id);
        $stmt->execute();
        $stmt->close();
 
}
}

 
?>