<?php 
require_once 'dbDetails.php';


	$abspath=$_SERVER['DOCUMENT_ROOT'];

	$dir = $abspath.'/findme/users/images/';
		
	$upload_path = 'images/';
	
	$server_ip = gethostbyname(gethostname());
	
	$upload_url = 'http://'.$server_ip.'/findme/users/'.$upload_path; 

	$response = array(); 

if(isset($_POST['id']) and isset($_POST['name']) and isset($_POST['email'])and isset($_POST['phone']) and isset($_FILES['image']['name']))
{
			
			$id = $_POST['id'];
			$name = $_POST['name'];
			$email = $_POST['email'];
			$phone = $_POST['phone'];

			$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
			try{
				
				$sql = "select *from `users` where id = '$id'";
				
				//adding the path and name to database 
				if(mysqli_query($con,$sql)){
					$result = mysqli_fetch_array(mysqli_query($con,$sql));
					$url=$result['photo'];
					$fileName = substr(strrchr($url,'/'), 1);
					$path=$dir.$fileName;
					$newpath = str_replace('/', '\\', $path);
					unlink($newpath);





			//getting file info from the request 
			$fileinfo = pathinfo($_FILES['image']['name']);
			
			//getting the file extension 
			$extension = $fileinfo['extension'];
			
			//file url to store in the database 
			$file_url = $upload_url . $id . '.' . $extension;
			
			//file path to upload in the server 
			$file_path = $upload_path . $id . '.'. $extension; 
			
			//trying to save the file in the directory 
			try{
				//saving the file 
				move_uploaded_file($_FILES['image']['tmp_name'],$file_path);
				$req = "UPDATE `users` set name='$name' ,email='$email' ,phone='$phone' ,photo='$file_url' where id='$id'";
				
				//adding the path and name to database 
				if(mysqli_query($con,$req)){
					
					//filling response array with values 
					$response['error'] = false; 
					$response['name'] = $name;
					$response['email'] = $email;
					$response['phone'] = $phone;
					$response['photo'] = $file_url; 
					
				}
			//if some error occurred 
			}catch(Exception $e){
				$response['error']=true;
				$response['message']=$e->getMessage();
			}		
			//displaying the response 


		}



			//if some error occurred 
			}catch(Exception $e){
				$response['error']=true;
				$response['message']=$e->getMessage();
			}
			echo json_encode($response);
			
			//closing the connection 
			mysqli_close($con);
}
	else{
			$response['error']=true;
			$response['message']='Nothing has changed ';
		}
		


 ?>