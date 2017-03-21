package com.vijayyogapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vijayyogapp.models.BoothDetailModel;
import com.vijayyogapp.models.SurnameCountModel;
import com.vijayyogapp.models.VoterDetailModel;
import com.vijayyogapp.models.VoterSurveyDetailModel;
import com.vijayyogapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vijay_yog.db";
    public static final String VOTER_DETAIL_TABLE_NAME = "voter_detail";
    public static final String BOOTH_DETAIL_TABLE_NAME = "booth_detail";
    public static final String VOTER_SURVEY_DETAILS_TABLE_NAME = "voter_survey_detail";

    //VOTER_DETAIL_TABLE_NAME
    public static final String VOTER_COLUMN_ID = "Id";
    public static final String VOTER_COLUMN_RECORD_ID = "RecordId";
    public static final String VOTER_COLUMN_UNIQUE_KEY = "Uniquekey";
    public static final String VOTER_COLUMN_LOKSABHA_ID = "LoksabhaId";
    public static final String VOTER_COLUMN_VIDHANSABHA_ID = "VidhansabhaId";
    public static final String VOTER_COLUMN_WARD_NUMBER = "Wardnumber";
    public static final String VOTER_COLUMN_LISTNO = "ListNo";
    public static final String VOTER_COLUMN_PSRNO = "PSRNO";
    public static final String VOTER_COLUMN_SRNO = "SrNo";
    public static final String VOTER_COLUMN_FNAME = "fName";
    public static final String VOTER_COLUMN_MNAME = "mName";
    public static final String VOTER_COLUMN_LNAME = "lName";
    public static final String VOTER_COLUMN_EFNAME = "efullName";

    public static final String VOTER_COLUMN_MFNAME = "mfName";
    public static final String VOTER_COLUMN_MMNAME = "mmName";
    public static final String VOTER_COLUMN_MLNAME = "mlName";
    public static final String VOTER_COLUMN_MFULLNAME = "mfullfName";

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
                + VOTER_COLUMN_RECORD_ID + " INTEGER,"
                + VOTER_COLUMN_UNIQUE_KEY + " TEXT,"
                + VOTER_COLUMN_LOKSABHA_ID + " TEXT,"
                + VOTER_COLUMN_VIDHANSABHA_ID + " TEXT,"
                + VOTER_COLUMN_WARD_NUMBER + " TEXT,"
                + VOTER_COLUMN_LISTNO + " TEXT,"
                + VOTER_COLUMN_PSRNO + " TEXT,"
                + VOTER_COLUMN_SRNO + " TEXT,"
                + VOTER_COLUMN_FNAME + " TEXT,"
                + VOTER_COLUMN_MNAME + " TEXT,"
                + VOTER_COLUMN_LNAME + " TEXT,"
                + VOTER_COLUMN_EFNAME + " TEXT,"
                + VOTER_COLUMN_MFNAME + " TEXT,"
                + VOTER_COLUMN_MMNAME + " TEXT,"
                + VOTER_COLUMN_MLNAME + " TEXT,"
                + VOTER_COLUMN_MFULLNAME + " TEXT,"
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

    public boolean insertVoter(VoterDetailModel detailModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(VOTER_COLUMN_RECORD_ID, detailModel.getRecordID());
        contentValues.put(VOTER_COLUMN_UNIQUE_KEY, detailModel.getPrimaryKey());
        contentValues.put(VOTER_COLUMN_VIDHANSABHA_ID, detailModel.getVidhansabhaId());
        contentValues.put(VOTER_COLUMN_LOKSABHA_ID, detailModel.getLoksabhaId());
        contentValues.put(VOTER_COLUMN_WARD_NUMBER, detailModel.getWardnumber());

        contentValues.put(VOTER_COLUMN_LISTNO, detailModel.getListNo());
        contentValues.put(VOTER_COLUMN_PSRNO, detailModel.getPSRNO());
        contentValues.put(VOTER_COLUMN_SRNO, detailModel.getSRNO());

        contentValues.put(VOTER_COLUMN_FNAME, detailModel.getFNAME());
        contentValues.put(VOTER_COLUMN_MNAME, detailModel.getMNAME());
        contentValues.put(VOTER_COLUMN_LNAME, detailModel.getLNAME());
        contentValues.put(VOTER_COLUMN_EFNAME, detailModel.getEFNAME());

        contentValues.put(VOTER_COLUMN_MFNAME, detailModel.getMFNAME());
        contentValues.put(VOTER_COLUMN_MMNAME, detailModel.getMMNAME());
        contentValues.put(VOTER_COLUMN_MLNAME, detailModel.getMLNAME());
        contentValues.put(VOTER_COLUMN_MFULLNAME, detailModel.getMFULLNAME());

        contentValues.put(VOTER_COLUMN_FATHERNAME, detailModel.getFATHERNAME());
        contentValues.put(VOTER_COLUMN_SEX, detailModel.getSEX());
        contentValues.put(VOTER_COLUMN_AGE, detailModel.getAge());
        contentValues.put(VOTER_COLUMN_HNO, detailModel.getHNO());

        contentValues.put(VOTER_COLUMN_ADDRESS, detailModel.getAddress());
        contentValues.put(VOTER_COLUMN_VOTER_ID, detailModel.getVoterID());
        contentValues.put(VOTER_COLUMN_BOOTHID, detailModel.getBID());
        contentValues.put(VOTER_COLUMN_ACTIVE, detailModel.getActive());

        db.insert(VOTER_DETAIL_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertBoothDetails(BoothDetailModel boothDetailModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BOOTH_ID, boothDetailModel.getBID());
        contentValues.put(BOOTH_COLUMN_ADDRESS, boothDetailModel.getBoothAddress());

        db.insert(BOOTH_DETAIL_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertBoothData(List<BoothDetailModel> list) {
        boolean result = false;
        for (int i = 0; i < list.size(); i++) {
            result = insertBoothDetails(list.get(i));
        }
        return result;
    }

    public boolean insertVoterData(List<VoterDetailModel> list) {
        boolean result = false;
        for (int i = 0; i < list.size(); i++) {
            result = insertVoter(list.get(i));
        }
        return result;
    }

    public boolean insertOrUpdateSurveyDetails(VoterSurveyDetailModel voterSurveyDetailModel, boolean isUdpdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VOTER_SURVEY_COLUMN_UNIQUE_KEY, voterSurveyDetailModel.getUniquekey());
        contentValues.put(VOTER_SURVEY_COLUMN_AADHAR_NO, voterSurveyDetailModel.getAadharNo());
        contentValues.put(VOTER_SURVEY_COLUMN_MOBILE_NO, voterSurveyDetailModel.getMobileNo());
        contentValues.put(VOTER_SURVEY_COLUMN_VOTER_STATUS, voterSurveyDetailModel.getStatus());
        if (isUdpdate) {
            db.insert(VOTER_SURVEY_DETAILS_TABLE_NAME, null, contentValues);
        } else {
            db.update(VOTER_SURVEY_DETAILS_TABLE_NAME, contentValues, VOTER_SURVEY_COLUMN_UNIQUE_KEY + " = ? ", new String[]{voterSurveyDetailModel.getUniquekey()});
        }
        return true;
    }


//TODO ===========================================   Retrieve data   ===========================================

    public ArrayList<VoterDetailModel> getAllVoters() {
        ArrayList<VoterDetailModel> containerDetailModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + VOTER_DETAIL_TABLE_NAME + " LIMIT 20", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            VoterDetailModel model = getVoterDetailModel(cursor);

            containerDetailModels.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return containerDetailModels;
    }

    public ArrayList<VoterDetailModel> getSearchVotersByName(String searchText, int SearchType) {
        ArrayList<VoterDetailModel> containerDetailModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = null;
        if (SearchType == Constants.BY_FNAME) {
            if (Constants.KEYBOARD_TYPE == 1) //Marathi
                sql = "select * from " + VOTER_DETAIL_TABLE_NAME + " where " + VOTER_COLUMN_MFNAME + " like '%" + searchText + "%'";
            else
                sql = "select * from " + VOTER_DETAIL_TABLE_NAME + " where " + VOTER_COLUMN_FNAME + " like '%" + searchText + "%'";
        } else if (SearchType == Constants.BY_LNAME) {
            if (Constants.KEYBOARD_TYPE == 1) //Marathi
                sql = "select * from " + VOTER_DETAIL_TABLE_NAME + " where " + VOTER_COLUMN_MLNAME + " like '%" + searchText + "%'";
            else
                sql = "select * from " + VOTER_DETAIL_TABLE_NAME + " where " + VOTER_COLUMN_LNAME + " like '%" + searchText + "%'";
        } else if (SearchType == Constants.BY_VOTERID) {
            sql = "select * from " + VOTER_DETAIL_TABLE_NAME + " where " + VOTER_COLUMN_VOTER_ID + " like '%" + searchText + "%'";
        } else if (SearchType == Constants.BY_ADDRESS) {
            sql = "select * from " + VOTER_DETAIL_TABLE_NAME + " where " + VOTER_COLUMN_ADDRESS + " like '%" + searchText + "%'";
        } else if (SearchType == Constants.BY_BOOTH) {
            sql = "select * from " + VOTER_DETAIL_TABLE_NAME + " where " + VOTER_COLUMN_BOOTHID + " like '%" + searchText + "%'";
        }
        Log.e("SQL", "getSearchVotersByName:" + sql);
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            VoterDetailModel model = getVoterDetailModel(cursor);

            containerDetailModels.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return containerDetailModels;
    }

    public ArrayList<VoterDetailModel> getSearchVotersByAge(String minAge, String maxAge, int SearchType) {
        ArrayList<VoterDetailModel> containerDetailModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = null;
        if (SearchType == Constants.BY_AGE) {
            sql = "select * from " + VOTER_DETAIL_TABLE_NAME + " where " + VOTER_COLUMN_AGE + " BETWEEN '" + minAge + "' AND '" + maxAge + "' ORDER BY " + VOTER_COLUMN_AGE + " ASC";
        }
        Log.e("SQL", "getSearchVotersByName:" + sql);
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            VoterDetailModel model = getVoterDetailModel(cursor);

            containerDetailModels.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return containerDetailModels;
    }

    @NonNull
    private VoterDetailModel getVoterDetailModel(Cursor cursor) {
        VoterDetailModel model = new VoterDetailModel();

        model.setRecordID(cursor.getInt(cursor.getColumnIndex(VOTER_COLUMN_RECORD_ID)));
        model.setPrimaryKey(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_UNIQUE_KEY)));
        model.setLoksabhaId(cursor.getInt(cursor.getColumnIndex(VOTER_COLUMN_LOKSABHA_ID)));
        model.setVidhansabhaId(cursor.getInt(cursor.getColumnIndex(VOTER_COLUMN_VIDHANSABHA_ID)));
        model.setWardnumber(cursor.getInt(cursor.getColumnIndex(VOTER_COLUMN_WARD_NUMBER)));

        model.setListNo(cursor.getInt(cursor.getColumnIndex(VOTER_COLUMN_LISTNO)));
        model.setPSRNO(cursor.getInt(cursor.getColumnIndex(VOTER_COLUMN_PSRNO)));
        model.setSRNO(cursor.getInt(cursor.getColumnIndex(VOTER_COLUMN_SRNO)));

        model.setFNAME(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_FNAME)));
        model.setMNAME(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_MNAME)));
        model.setLNAME(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_LNAME)));
        model.setEFNAME(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_EFNAME)));

        model.setMFNAME(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_MFNAME)));
        model.setMMNAME(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_MMNAME)));
        model.setMLNAME(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_MLNAME)));
        model.setMFULLNAME(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_MFULLNAME)));

        model.setFATHERNAME(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_FATHERNAME)));
        model.setSEX(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_SEX)));
        model.setAge(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_AGE)));
        model.setHNO(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_HNO)));

        model.setAddress(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_ADDRESS)));
        model.setVoterID(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_VOTER_ID)));
        model.setBID(cursor.getInt(cursor.getColumnIndex(VOTER_COLUMN_BOOTHID)));
        model.setActive(cursor.getInt(cursor.getColumnIndex(VOTER_COLUMN_ACTIVE)));
        return model;
    }

    public String getBoothAddress(String boothId) {
        String boothAddress = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] params = new String[]{boothId};
        Cursor cursor = db.rawQuery("select * from " + BOOTH_DETAIL_TABLE_NAME + " where " + BOOTH_ID + " = ?", params);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            boothAddress = cursor.getString(cursor.getColumnIndex(BOOTH_COLUMN_ADDRESS));
            cursor.moveToNext();
        }
        cursor.close();
        return boothAddress;
    }

    public VoterSurveyDetailModel getSurveyData(String uniqueKey) {
        VoterSurveyDetailModel model = new VoterSurveyDetailModel();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] params = new String[]{uniqueKey};
        Cursor cursor = db.rawQuery("select * from " + VOTER_SURVEY_DETAILS_TABLE_NAME + " where " + VOTER_SURVEY_COLUMN_UNIQUE_KEY + " = ?", params);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                model.setUniquekey(cursor.getString(cursor.getColumnIndex(VOTER_SURVEY_COLUMN_UNIQUE_KEY)));
                model.setAadharNo(cursor.getString(cursor.getColumnIndex(VOTER_SURVEY_COLUMN_AADHAR_NO)));
                model.setMobileNo(cursor.getString(cursor.getColumnIndex(VOTER_SURVEY_COLUMN_MOBILE_NO)));
                model.setStatus(cursor.getString(cursor.getColumnIndex(VOTER_SURVEY_COLUMN_VOTER_STATUS)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return model;
    }

    public long getStatusTypeCount(String statusType) {
        int count;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] params = new String[]{statusType};
        Cursor cursor = db.rawQuery("select * from " + VOTER_SURVEY_DETAILS_TABLE_NAME + " where " + VOTER_SURVEY_COLUMN_VOTER_STATUS + " = ?", params);
        count = cursor.getCount();
        cursor.close();
        return count;
    }
    public int getLastInsertedVoterID() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select "+ VOTER_COLUMN_RECORD_ID +" from " + VOTER_DETAIL_TABLE_NAME  + " ORDER BY "+ VOTER_COLUMN_RECORD_ID + " DESC LIMIT 1";
        Log.e("sql",sql);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            count = cursor.getInt(cursor.getColumnIndex(VOTER_COLUMN_RECORD_ID));
        }
//        count = cursor.getCount();
        cursor.close();
        return count;
    }

    public long getTotalVoterCount() {
        int count;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + VOTER_DETAIL_TABLE_NAME , null);
        count = cursor.getCount();
        cursor.close();
        return count;
    }
    public ArrayList<SurnameCountModel> getSurnameWiseCount() {
        ArrayList<SurnameCountModel> modelArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select " + VOTER_COLUMN_LNAME + " , COUNT( " + VOTER_COLUMN_LNAME + " ) as count from " + VOTER_DETAIL_TABLE_NAME + " group by " + VOTER_COLUMN_LNAME + " ORDER BY " + 2 + " DESC;" ;
        Log.e("SurnameCountModel:", sql);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SurnameCountModel model = new SurnameCountModel();
                model.setSurname(cursor.getString(cursor.getColumnIndex(VOTER_COLUMN_LNAME)));
                model.setCount(cursor.getInt(cursor.getColumnIndex("count")));
                modelArrayList.add(model);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return modelArrayList;
    }


    //TODO================== Delete===============================================
    public Integer deleteSurevey(String primarykey) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(VOTER_SURVEY_DETAILS_TABLE_NAME, VOTER_SURVEY_COLUMN_UNIQUE_KEY + " = ?", new String[]{primarykey});
    }

    public Integer deleteVoterData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(VOTER_DETAIL_TABLE_NAME, null, null);
    }

    public Integer deleteBoothData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BOOTH_DETAIL_TABLE_NAME, null, null);
    }

}
