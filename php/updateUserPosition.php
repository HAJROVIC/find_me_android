<?php

require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['id']) && isset($_POST['position']) ) {
 //getting name from the request 
            $id = $_POST['id'];
            $position= $_POST['position'];
            
         
                $db->updateUserPosition($id,$position);
             
        }

?>