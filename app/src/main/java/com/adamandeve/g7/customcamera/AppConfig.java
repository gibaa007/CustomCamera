//  Created by Gibin  on 14/09/2015.
//  Copyright (c) 2015 NewageSmb. All rights reserved.
package com.adamandeve.g7.customcamera;


public class AppConfig {

    public static final String INTENTDATA = "_data";
    public static final String BASE_URL = "http://g7.com/";
    public static final String BASE_URL_CLIENT = BASE_URL + "client/";
    public static final String BASE_URL_COMMON = BASE_URL + "common/";
    public static final String APP_URL = "";
    public static final String PRIVACY_URL = BASE_URL_COMMON + "privacy_policy";
    public static final String TERMS_URL = BASE_URL_COMMON + "terms_of_use";
    public static final String FAQ_URL = "";


    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    // LIVE URLS
    //public static final String APP_URL = "";
    //PERMISSION CONSTANTS
    public final static int MY_PERMISSIONS_REQUEST_CAMERA = 80;
    public final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 81;
    public final static int MY_PERMISSIONS_REQUEST_MIC = 82;
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    public static final int REFRESH_ACTIVITY = 0x4;
    //GCM CONSTANTS
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    //Splash
    public final static int SPLASH_TIME_OUT = 5000;
    //INTENT CONSTANTS
    public final static String WHICH_URL = "which_url";
    //Call
    public static final String CALLACCEPTED = "_call_acepted";
    public static final String CALLREJECTED = "_call_rejected";
    public static final String CALLMISSED = "_call_missed";

    public static String DEVICE_ID = "device_id";
    public static String LOGGED_VIA = "logged_via";


    //SHARED PREFERENCE
    public static String SHARED_VALUE = "name";
    // USER CREDENTIALS
    //API CONSTANTS
    public static int GET = 0;
    public static int POST_WITH_DATA = 1;//Files
    public static int POST_WITH_JSON = 2;//JSON

    public static String TEMP_PROFILE_PHOTO_FILE_NAME = "temp_profile_photo.jpg";
    //USER
    public static String USER_ID = "member_id";
    public static String USER_NAME = "user_name";
    public static String USER_ACCESS_TOKEN = "user_access_token";
    public static String USER_REFRESH_TOKEN = "user_refresh_token";
    public static String USER_EMAIL = "user_email";
    public static String USER_PASSWORD = "password";
    public static String USER_FULL_NAME = "user_full_name";
    public static String USER_PROFILE_PIC = "user_profile_pic";
    public static String USER_COVER_PIC = "user_Cover";
    public static String USER_TYPE = "user_type";
    public static String USER_LAT = "lat";
    public static String USER_LNG = "lng";
    public static String USER_COUNTRY = "country_name";
    public static String USER_COUNTRY_CODE = "country_code";
    public static String ALLOWED_TO_PURCHASE = "allowed_to_purchase";


    //Webservice status
    public static int FAIL = 0;
    public static int SUCCESS = 1;
    public static int FAILURE_INTERNET = 2;
    public static int SESSION_EXPIRED = 3;
    public static int FAILURE_OTHER = 4;
    //WEB SERVICE METHOD CONSTANTS
    public static int LOGIN = 100;
    public static int REGISTER = 101;

    //IMAGE CHANGE
    public static int NO_CHANGE = 0;
    public static int DEFAULT_PIC_ADDED = 1;
    public static int NEW_PIC_ADDED = 2;

}
