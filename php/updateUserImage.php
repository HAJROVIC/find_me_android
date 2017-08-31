<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
//this is our upload folder 
    $upload_path = 'users/images/';
    
    //Getting the server ip 
    $server_ip = gethostbyname(gethostname());
    
    //creating the upload url 
    $upload_url = 'http://'.$server_ip.'/findme/'.$upload_path; 
 
// json response array
$response = array("error" => FALSE);
if (isset($_POST['email']) && isset($_POST['email']) ) {
 //getting name from the request 
            $email = $_POST['email'];
            $name = $_POST['name'];
            
            //getting file info from the request 
            $fileinfo = pathinfo($_FILES['image']['name']);
            
            //getting the file extension 
            $extension = $fileinfo['extension'];
            
            //file url to store in the database 
            $file_url = $upload_url . $name. '.' . $extension;
            
            //file path to upload in the server 
            $file_path = $upload_path . $name . '.'. $extension; 
            
            //trying to save the file in the directory 
            try{
                //saving the file 
                move_uploaded_file($_FILES['image']['tmp_name'],$file_path);
                $db->updateUserImage($email,$file_url);
                
             
                    
                    //filling response array with values 
                    $response['error'] = false; 
                    $response['url'] = $file_url; 
                    $response['name'] = $name;
                
            //if some error occurred 
            }catch(Exception $e){
                $response['error']=true;
                $response['message']=$e->getMessage();
            }       
            //displaying the response 
            echo json_encode($response);
        }else{
            $response['error']=true;
            $response['message']='Please choose a file';
            echo json_encode($response);
        }

?>