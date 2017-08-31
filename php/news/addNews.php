<?php 
	header('Access-Control-Allow-Origin: *');
	//importing dbDetails file 
	require_once 'dbDetails.php';
	
	//this is our upload folder 
	$upload_path = 'uploads/';
	
	//Getting the server ip 
	$server_ip = gethostbyname(gethostname());
	
	//creating the upload url 
	$upload_url = 'http://'.$server_ip.'/findme/news/'.$upload_path; 
	
	//response array 
	$response = &array();
		
		//checking the required parameters from the request 
		if(isset($_POST['circle_id']) and isset($_POST['id_user']) and isset($_POST['content']) and isset($_FILES['image']['name']))
		{
			
			//connecting to the database 
			$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
			
			//getting name from the request 
			$circle_id = $_POST['circle_id'];

			$id_user = $_POST['id_user'];

			$content=$_POST['content'];

			//getting file info from the request 
			$fileinfo = pathinfo($_FILES['image']['name']);
			
			//getting the file extension 
			$extension = $fileinfo['extension'];
			
			//file url to store in the database 
			$file_url = $upload_url . getFileName() . '.' . $extension;
			
			//file path to upload in the server 
			$file_path = $upload_path . getFileName() . '.'. $extension; 

			try{

				
				$sql = "INSERT INTO `admin_findme`.`news` ( `circle_id`, `user_id`,`created_at`,`updated_at`,`url`,`content`) VALUES ('$circle_id', '$id_user',NOW(),NOW(),'$file_url','$content')";
				$news = mysqli_query($con,$sql);
				
				if($news){
					move_uploaded_file($_FILES['image']['tmp_name'],$file_path);
					//filling response array with values 
					$response['error'] = false; 
					$response["news"]["id"] = mysqli_insert_id($con) ;
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
		}else{
			$response['error']=true;
			$response['message']='Please choose a file';	
		}
			function getFileName(){
		$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
		$sql = "SELECT max(id) as id FROM news";
		$result = mysqli_fetch_array(mysqli_query($con,$sql));
		
		mysqli_close($con);
		if($result['id']==null)
			return 1; 
		else 
			return ++$result['id']; 
	}

	
?>