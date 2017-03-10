package com.vijayyogapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vijayyogapp.models.BoothDetailModel;
import com.vijayyogapp.models.VoterDetailTable;
import com.vijayyogapp.models.VoterSurveyDetailModel;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vijay_yog.db";
    public static final String VOTER_DETAIL_TABLE_NAME = "voter_detail";
    public static final String BOOTH_DETAIL_TABLE_NAME = "booth_detail";
    public static final String VOTER_SURVEY_DETAILS_TABLE_NAME = "voter_survey_detail";

    //VOTER_DETAIL_TABLE_NAME
    public static final String VOTER_COLUMN_ID = "Id";
    public static final String VOTER_COLUMN_UNIQUE_KEY = "Uniquekey";
    public static final String VOTER_COLUMN_AC_NUM = "AcNo";
    public static final String VOTER_COLUMN_LISTNO = "ListNo";
    public static final String VOTER_COLUMN_SRNO = "SrNo";
    public static final String VOTER_COLUMN_FNAME = "fName";
    public static final String VOTER_COLUMN_MNAME = "mName";
    public static final String VOTER_COLUMN_LNAME = "lName";
    public static final String VOTER_COLUMN_EFNAME = "efName";
    public static final String VOTER_COLUMN_MFNAME = "mfName";
    public static final String VOTER_COLUMN_FATHERNAME = "fatherName";
    public static final String VOTER_COLUMN_VOTER_ID = "VoterID";
    public static final String VOTER_COLUMN_SEX = "Sex";
    public static final String VOTER_COLUMN_AGE = "Age";
    public static final String VOTER_COLUMN_HNO = "HallNo";
    public static final String VOTER_COLUMN_ADDRESS = "Address";
    public static final String VOTER_COLUMN_BOOTHID = "BoothId";
    public static final String VOTER_COLUMN_ACTIVE = "Active";

    //BOOTH_DETAIL_TABLE_NAME
    public static final String BOOTH_COLUMN_ID = "Id";
    public static final String BOOTH_ID = "Booth_Id";
    public static final String BOOTH_COLUMN_ADDRESS = "Booth_Address";


    //SURVEY_DETAIL_TABLE_NAME
    public static final String VOTER_SURVEY_DETAIL_COLUMN_ID = "Id";
    public static final String VOTER_SURVEY_COLUMN_UNIQUE_KEY = "Uniquekey";
    public static final String VOTER_SURVEY_COLUMN_AADHAR_NO = "AadharNo";
    public static final String VOTER_SURVEY_COLUMN_MOBILE_NO = "MobileNo";
    public static final String VOTER_SURVEY_COLUMN_VOTER_STATUS = "Status";

    private static DBHelper mInstance;


    public DBHelper(Context context) {
        //TODO UPADTE db VERSION IN NEXT RELEASE
        super(context, DATABASE_NAME, null, 1);
    }

    public static DBHelper getInstance(Context context) {
        if (mInstance == null)
            mInstance = new DBHelper(context);

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String VOTER_DETAIL_TABLE = "CREATE TABLE " + VOTER_DETAIL_TABLE_NAME + "("
                + VOTER_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + VOTER_COLUMN_UNIQUE_KEY + " TEXT,"
                + VOTER_COLUMN_AC_NUM + " TEXT,"
                + VOTER_COLUMN_LISTNO + " TEXT,"
                + VOTER_COLUMN_SRNO + " TEXT,"
                + VOTER_COLUMN_FNAME + " TEXT,"
                + VOTER_COLUMN_MNAME + " TEXT,"
                + VOTER_COLUMN_LNAME + " TEXT,"
                + VOTER_COLUMN_EFNAME + " TEXT,"
                + VOTER_COLUMN_MFNAME + " TEXT,"
                + VOTER_COLUMN_FATHERNAME + " TEXT,"
                + VOTER_COLUMN_VOTER_ID + " TEXT,"
                + VOTER_COLUMN_SEX + " TEXT,"
                + VOTER_COLUMN_AGE + " TEXT,"
                + VOTER_COLUMN_ADDRESS + " TEXT,"
                + VOTER_COLUMN_HNO + " TEXT,"
                + VOTER_COLUMN_BOOTHID + " TEXT,"
                + VOTER_COLUMN_ACTIVE + " Boolean" + ")";

        String BOOTH_DETAIL_TABLE = "CREATE TABLE " + BOOTH_DETAIL_TABLE_NAME + "("
                + BOOTH_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + BOOTH_ID + " INTEGER,"
                + BOOTH_COLUMN_ADDRESS + " TEXT" + ")";

        String SURVEY_DETAIL_TABLE = "CREATE TABLE " + VOTER_SURVEY_DETAILS_TABLE_NAME + "("
                + VOTER_SURVEY_DETAIL_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + VOTER_SURVEY_COLUMN_UNIQUE_KEY + " TEXT,"
                + VOTER_SURVEY_COLUMN_AADHAR_NO + " TEXT,"
                + VOTER_SURVEY_COLUMN_MOBILE_NO + " TEXT,"
                + VOTER_SURVEY_COLUMN_VOTER_STATUS + " TEXT" + ")";

        db.execSQL(VOTER_DETAIL_TABLE);
        db.execSQL(BOOTH_DETAIL_TABLE);
        db.execSQL(SURVEY_DETAIL_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public void initDB() {
        this.getReadableDatabase();
        this.getWritableDatabase();
    }

    //TODO ===========================================   Insert data   ===========================================

    public boolean insertVoter(VoterDetailTable detailModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(VOTER_COLUMN_CHECKER_REMARK, detailModel.getCheckerRemark());

        db.insert(VOTER_DETAIL_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertBoothDetails(BoothDetailModel boothDetailModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BOOTH_ID, boothDetailModel.getBoothId());
        contentValues.put(BOOTH_COLUMN_ADDRESS, boothDetailModel.getBoothAddress());
        db.insert(BOOTH_DETAIL_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertSurveyDetails(VoterSurveyDetailModel voterSurveyDetailModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(VOTER_SURVEY_COLUMN_UNIQUE_KEY, voterSurveyDetailModel.getUniquekey());
        contentValues.put(VOTER_SURVEY_COLUMN_AADHAR_NO, voterSurveyDetailModel.getAadharNo());
        contentValues.put(VOTER_SURVEY_COLUMN_MOBILE_NO, voterSurveyDetailModel.getMobileNo());
        contentValues.put(VOTER_SURVEY_COLUMN_VOTER_STATUS, voterSurveyDetailModel.getStatus());

        db.insert(BOOTH_DETAIL_TABLE_NAME, null, contentValues);
        return true;
    }


//TODO ===========================================   Retrieve data   ===========================================

}
