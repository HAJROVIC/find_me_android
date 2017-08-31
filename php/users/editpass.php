<?php 
	
	//importing dbDetails file 
	require_once 'dbDetails.php';
	header("Access-Control-Allow-Origin: *"); 

 
	$response = array(); 

			
		//checking the required parameters from the request 
		if(isset($_POST['id']) and isset($_POST['password']))
		{
			
			//connecting to the database 
			$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
			
			//getting name from the request 
			$id = $_POST['id'];

			$password = $_POST['password'];

			 $hash = hashSSHA($password);
       		 $encrypted_password = $hash["encrypted"]; // encrypted password
       		 $salt = $hash["salt"]; // salt

			try{

				$sql = "UPDATE `admin_findme`.`users` set encrypted_password='$encrypted_password', salt='$salt' where id='$id'";
				$news = mysqli_query($con,$sql);
				
				if($news){
					
					//filling response array with values 
					$response['error'] = false; 
					echo json_encode($response);
				}
			//if some error occurred 
			}catch(Exception $e){
				$response['error']=true;
				$response['message']=$e->getMessage();
				echo json_encode($response);
			}		
			//displaying the response 
			
			
			//closing the connection 
			mysqli_close($con);
		}

		else{
			$response['error']=true;
			$response['message']='Empty fields';
		}


	  function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
	
?>