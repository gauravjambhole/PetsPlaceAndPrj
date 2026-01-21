package com.taksh.petspalace10.Common;

public class Urls {
    // Base URL to make it easier to manage
    public static final String BaseURL = "http://192.168.1.10:80/petpalaceapi/";

    // Point to the Registration script
    public static final String RegisterUserWebService = BaseURL + "registerUser.php";

    // Point to the Login script (FIXED THIS LINE)
    public static final String LoginUserWebService = BaseURL + "loginUser.php";
}