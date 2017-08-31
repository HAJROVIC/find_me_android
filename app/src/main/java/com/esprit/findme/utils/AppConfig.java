package com.esprit.findme.utils;

/**
 * Created by TIBH on 07/11/2016.
 */

public class AppConfig {

    public static   String IP ="192.168.1.2" ;


    // user
    public static String URL_LOGIN = "http://"+IP+"/findme/login.php";
    public static String URL_REGISTER = "http://"+IP+"/findme/register.php";
    public static String URL_LOAD_PHOTO = "http://"+IP+"/findme/updateUserImage.php";
    public static String URL_GET_USERS = "http://"+IP+"/findme/getUserByCircle.php";
    public static String URL_UPDATE_POSITION = "http://"+IP+"/findme/updateUserPosition.php";
    public static String URL_EDIT_USER = "http://"+IP+"/findme/users/editprofile.php";
    public static String URL_EDIT_PWD="http://"+IP+"/findme/users/editpass.php";




    // circles
    public static String URL_AddCircle = "http://"+IP+"/findme/circle.php";
    public static String URL_JOIN_CIRCLE = "http://"+IP+"/findme/inviter.php";

    //NEWS


    public static String URL_ADD_NEWS = "http://"+IP+"/findme/news/addNews.php";

    public static String URL_GET_NEWS = "http://"+IP+"/findme/news/getAllCircleNews";

    public static String URL_GET_CIRCLES = "http://"+IP+"/findme/news/testCircles.php";




}
