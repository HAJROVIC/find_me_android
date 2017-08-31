<?php 
require_once 'dbDetails.php';
header("Access-Control-Allow-Origin: *"); 



	$abspath=$_SERVER['DOCUMENT_ROOT'];

	$dir = $abspath.'/findme/users/images/';
		
	$upload_path = 'images/';
	
	$server_ip = gethostbyname(gethostname());
	
	$upload_url = 'http://'.$server_ip.'/findme/users/'.$upload_path; 

	$response = array(); 

if(isset($_POST['id']) and isset($_POST['name']) and isset($_POST['email'])and isset($_POST['phone']) )
{
			
			$id = $_POST['id'];
			$name = $_POST['name'];
			$email = $_POST['email'];
			$phone = $_POST['phone'];

			$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
				
				



			try{
				//saving the file 
				$req = "UPDATE `admin_findme`.`users` set name='$name' ,email='$email' ,phone='$phone'  where id='$id'";
				
				//adding the path and name to database 
				if(mysqli_query($con,$req)){
					
					//filling response array with values 
					$response['error'] = false; 
					$response['name'] = $name;
					$response['email'] = $email;
					$response['phone'] = $phone;
					
				}
			//if some error occurred 
			}catch(Exception $e){
				$response['error']=true;
				$response['message']=$e->getMessage();
			}		
			//displaying the response 


		


			echo json_encode($response);
			
			//closing the connection 
			mysqli_close($con);
}
	else{
			$response['error']=true;
			$response['message']='Nothing has changed ';
		}
		


 ?>