package dev.azsoft.wifiattendance.global;


public class Const {
    public static final String API_KEY = "AIzaSyDU0QBIoZtE-J97foUfhIa5n8Myg3dKu3I";
    public static final String FIRST_LAUNCH = "first-launch";
    public static final double DEFAULT_DIALOG_WIDTH = 0.9;
    public static final String CURRENT_USER = "current-user";

    // Firestore collections
    public static final String ADMINS = "admins";
    public static final String SOME_THING_WENT_WRONG = "Something went wrong";
    public static final String TRY_CONTACT_ADMIN = "Try to contact admin";
    public static String EMPLOYEES = "employees";
    public static String EMPLOYEE = "employee";
    public static String PRIVATE_NETWORKS = "privateNetworks";


    // database
    public static final String DATABASE_NAME = "wifi_attendance1.db";
    public static final String NETWORKS_TABLE_NAME = "private_networks";
    public static final int DATABASE_VERSION = 1;

    public final static String ID = "id";
    public final static String SSID = "ssid";
    public final static String BSSID = "bssid";
    public final static String NETWORK_ID = "networkId";
    public final static String IP_ADDRESS = "ipAddress";
    public final static String MAC_ADDRESS = "macAddress";
    public final static String TIMESTAMP = "timestamp";
    public final static String ADDED_BY_UID = "addedByUID";
    public static String ATTENDANCES = "attendances";
    public static String ATTENDANCE = "attendance";
}
