<?php 
 
 //Importing dbdetails file 
 require_once 'dbDetails.php';
header("Access-Control-Allow-Origin: *"); 

$circle_id=$_GET['circle_id'];
 //connection to database 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
 

 $sql = "SELECT n.id id , DATE_FORMAT(n.created_at,'%a %h:%i %p %d %b %Y') 'when',u.name user_name,u.photo photo,n.url url,n.content content 
FROM news n
inner JOIN
users u 
ON n.user_id = u.id 
WHERE n.circle_id = '$circle_id'
ORDER by 'when' asc";
 
 //getting news
 $result = mysqli_query($con,$sql);
 
 //response array 
 $response = array(); 
 $response['error'] = false; 
 $response['news'] = array(); 
 
 //traversing through all the rows 
 while($row = mysqli_fetch_array($result)){
 $temp = array(); 
 $temp['id']=$row['id'];
  $temp['when']=$row['when'];
 $temp['user_name']=$row['user_name'];
 $temp['photo']=$row['photo'];
 $temp['url']=$row['url'];
 $temp['content']=$row['content'];
 array_push($response['news'],$temp);
 }
 //displaying the response 
 echo json_encode($response);
 

