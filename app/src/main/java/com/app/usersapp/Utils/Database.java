package com.app.usersapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.usersapp.Models.UserObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class Database {

    private static Db database;

    public Database(Context context) {
        database = new Db(context);
    }

    public List<UserObject> getAllUsers() {
        List<UserObject> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        String columns[] = {database.users_id, database.users_first_name, database.users_last_name, database.users_phone, database.users_email};
        Cursor cursor = sqLiteDatabase.query(database.user_table, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            UserObject user = new UserObject();
            user.setUserId(cursor.getInt(0));
            user.setFirstName(cursor.getString(1));
            user.setLastName(cursor.getString(2));
            user.setPhoneNumber(cursor.getString(3));
            user.setEmailAddress(cursor.getString(4));
            list.add(user);
        }
        return list;
    }

    public UserObject getUserById(int id) {
        UserObject user = new UserObject();
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        String columns[] = {database.users_id, database.users_first_name, database.users_last_name, database.users_phone, database.users_email};
        Cursor cursor = sqLiteDatabase.query(database.user_table, columns, database.users_id + " = " + id, null, null, null, null);
        while (cursor.moveToNext()) {
            user.setUserId(cursor.getInt(0));
            user.setFirstName(cursor.getString(1));
            user.setLastName(cursor.getString(2));
            user.setPhoneNumber(cursor.getString(3));
            user.setEmailAddress(cursor.getString(4));
        }
        return user;
    }

    public long addUser(int userId,
                         String first_name,
                         String last_name,
                         String phone,
                         String email) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(database.users_id, userId);
        contentValues.put(database.users_first_name, first_name);
        contentValues.put(database.users_last_name, last_name);
        contentValues.put(database.users_phone, phone);
        contentValues.put(database.users_email, email);
        long i = sqLiteDatabase.insert(database.user_table, null, contentValues);
        return i;
    }

    public int updateUser(int userId,
                          String first_name,
                          String last_name,
                          String phone,
                          String email){
        SQLiteDatabase sqlitedatabase=database.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put(database.users_first_name, first_name);
        contentvalues.put(database.users_last_name, last_name);
        contentvalues.put(database.users_phone, phone);
        contentvalues.put(database.users_email, email);
        String[] whereArgs={String.valueOf(userId)};
        int i = sqlitedatabase.update(database.user_table,contentvalues,database.users_id+" = ?",whereArgs);
        return i;
    }

    public int deleteUser(int id){
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        String[] whereArgs = {String.valueOf(id)};
        int i = sqLiteDatabase.delete(database.user_table,database.users_id+" = ?",whereArgs);
        return i;
    }

    private static class Db extends SQLiteOpenHelper {
        private Context context;

        //Database
        private static final String database_name = "users_db";
        private static final int database_version = 2;

        //Tables
        private static final String user_table = "users";

        private static final String users_id = "id";
        private static final String users_first_name = "first_name";
        private static final String users_last_name = "last_name";
        private static final String users_phone = "phone";
        private static final String users_email = "email";

        private static final String CREATE_USERS_TABLE = "CREATE TABLE " + user_table + "(" + users_id +
                " INTEGER PRIMARY KEY, " + users_first_name + " VARCHAR(50), " +
                users_last_name + " VARCHAR(50), " + users_phone + " VARCHAR(50), "+users_email+" VARCHAR(50)" + ");";
        //Drop Users table
        private static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS " + user_table;

        //Constructor
        public Db(@Nullable Context context) {
            super(context, database_name, null, database_version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_USERS_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_USERS_TABLE);
                onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
