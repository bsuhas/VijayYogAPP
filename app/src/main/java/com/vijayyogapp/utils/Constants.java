package com.vijayyogapp.utils;

/**
 * Created by harshalranpise on 19/10/15.
 */
public class Constants {

    /**
     * URL's
     */
//    public static final String BASE_URL = "http://onenethost-001-site1.1tempurl.com/api/";
//    public static final String URL_AUTHENTICATE = BASE_URL + "Account/Authenticate";
//    public static final String URL_ADD_CONTAINER = BASE_URL + "Container/add";
//    public static final String URL_ADD_IMAGE = BASE_URL + "Container/saveimage";
//    public static final String URL_GET_CUSTOMER_CODE = BASE_URL + "Customer/getCodes";


    public static final String BASE_URL = "http://one-pro.in/CPA/webservices/";
    public static final String URL_AUTHENTICATE = BASE_URL + "login.php";
    public static final String URL_ADD_CONTAINER = BASE_URL + "add_container_data.php";
    public static final String URL_ADD_IMAGE = BASE_URL + "image_upload.php";
    public static final String URL_GET_CUSTOMER_CODE = BASE_URL + "get_customer_codes.php";

    public static final int GALLERY_INTENT_REQUEST_CODE = 0x000005;

    /**
     * URL Request id's
     */
    public static final int REQUEST_ID_AUTHENTICATE = 100;
    public static final int REQUEST_ID_ADD_CONTAINER = 101;
    public static final int REQUEST_ID_ADD_IMAGE = 102;
    public static final int REQUEST_ID_GET_CUSTOMER_CODE = 103;

    /**
     * Error Id's
     */
    public static final int ERROR_ID_NO_NETWORK = 200;
    public static final int ERROR_ID_NO_SERVER_RESPONSE = 201;
    public static final String OPEN_CONTAINERS_FILENAME = "OpenContainer";
    public static final String REFRESH_CONTAINER_LIST = "refresh_container_list";
    public static final String SEND_NEW_IMAGE_REQUEST = "send_new_image_request";
    public static final String SEND_CONTAINER_DETAILS_REQUEST = "send_Container_Details";
    public static final String SHOW_SCANNER_SCREEN = "show_Scanner_Screen";

    public static final String INTENT_PASS_REQ_IMG_MODEL = "req_img_model";
    public static final String INTENT_PASS_CONTAINER_ID = "req_pass_container_num";
    public static final String SUCCESS = "000";
    public static final String FAIL = "001";


    public static String ASYNC_PROGRESS_MESSAGE = "Processing....";


    public static final int REQUEST_CAMERA = 10;
    public static final int SELECT_FILE = 11;

    public static final String EXTRA_SCANNER_RESULT = "ScannerResult";
    public static final String EXTRA_CAMERA_RESULT = "CameraResult";
    public static final String EXTRA_IMAGE_BYTES = "ImageBytes";

    public static String EXTRA_CONTAINER_NO = "ContainerNumber";
    public static String EXTRA_MAX_PERM_WEIGHT = "MaxPermissibleWeight";
    public static String EXTRA_CARGO_TYPE = "CargoType";

    public static final String EXTRA_IS_BARCODE_FLOW = "isBarcodeFlow";

    //Shared preference
    public static final String USERDATA = "username";
    public static final String AUTHkEY = "authkey";
    public static final String KEEP_ME_LOGIN = "keepmelogin";
    public static final String CUSTOMER_CODE_API_CALLED = "customer_code_api_called";

    public static final String CONTAINER_NUMBER = "ContainerNumber";
    public static final String EDIT_CONTAINER = "edit_container";
    public static final String SHIPMENT_MODEL = "shipment_model";
    public static final String DASHBOARD_VIEW = "dashboard_view";

    public static final String SEARCH_FOR_TEXT = "SearchForText";

}
