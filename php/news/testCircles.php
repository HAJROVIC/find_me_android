<?php 
 
 //Importing dbdetails file 
 require_once 'dbDetails.php';
header("Access-Control-Allow-Origin: *"); 

$user_id=$_GET['user_id'];
 //connection to database 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
 

 $sql = "SELECT * from circles where creator='$user_id' 
 		UNION 
 		SELECT c.* from circles c 
 		inner join invitations i 
 		on c.code = i.circle_code 
 		WHERE i.user_id= '$user_id'";
 
 //getting news
 $result = mysqli_query($con,$sql);
 
 //response array 
 $response = array(); 
 $response['error'] = false; 
 $response['circles'] = array(); 
 
 //traversing through all the rows 
 while($row = mysqli_fetch_array($result)){
 $temp = array(); 
 $temp['id']=$row['id'];
 $temp['title']=$row['title'];
 $temp['code']=$row['code'];
 array_push($response['circles'],$temp);
 }
 //displaying the response 
 echo json_encode($response);
 

