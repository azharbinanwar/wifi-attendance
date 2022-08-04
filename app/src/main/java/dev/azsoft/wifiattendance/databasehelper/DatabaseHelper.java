package dev.azsoft.wifiattendance.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import dev.azsoft.wifiattendance.global.Const;
import dev.azsoft.wifiattendance.models.PrivateNetwork;

public class DatabaseHelper extends SQLiteOpenHelper {

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public DatabaseHelper(Context context) {
        super(context, Const.DATABASE_NAME, null, Const.DATABASE_VERSION);
    }

    public void deleteAllRecords(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Const.NETWORKS_TABLE_NAME, null, null);
    }

    public long updateAllRecords(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(Const.NETWORKS_TABLE_NAME, null, contentValues);
    }

    public long insertRecord(ContentValues contentValues) {
        long index = -1;
        String id = contentValues.get(Const.ID).toString();
        PrivateNetwork privateNetwork = getARecord(id);
        if (privateNetwork == null) {
            SQLiteDatabase db = this.getWritableDatabase();
            index = db.insert(Const.NETWORKS_TABLE_NAME, null, contentValues);
        }
        System.out.println("DatabaseHelper.insertRecord: " + index);
        return index;
    }

    public PrivateNetwork getARecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Const.NETWORKS_TABLE_NAME + " where " + Const.ID + "='" + id + "' limit 1", null);
        if (cursor.moveToFirst()) {
            PrivateNetwork privateNetwork = new PrivateNetwork(
                    cursor.getString(cursor.getColumnIndexOrThrow(Const.ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Const.SSID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Const.BSSID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Const.NETWORK_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Const.IP_ADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Const.MAC_ADDRESS)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(Const.TIMESTAMP)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Const.ADDED_BY_UID))
            );
            cursor.close();
            return privateNetwork;
        }

        return null;
    }


    public boolean updateRecord(Integer id, String name, String phone, String email, String street, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update(Const.NETWORKS_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteRecord(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Const.NETWORKS_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DatabaseHelper.onCreate");
        String query =
                "CREATE TABLE " +
                        Const.NETWORKS_TABLE_NAME + "(" +
                        Const.ID + " TEXT PRIMARY KEY, " +
                        Const.SSID + " TEXT, " +
                        Const.BSSID + " TEXT, " +
                        Const.NETWORK_ID + " INTEGER ," +
                        Const.MAC_ADDRESS + " TEXT ," +
                        Const.TIMESTAMP + " INTEGER ," +
                        Const.IP_ADDRESS + " INTEGER ," +
                        Const.ADDED_BY_UID + " TEXT" +
                        ");";

        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("DatabaseHelper.onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + Const.NETWORKS_TABLE_NAME);
        onCreate(db);
    }

    public static String toValidId(String id) {
        return id.replaceAll(":", "_");
    }

}