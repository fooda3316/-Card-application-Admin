package com.example.adminapplication.SQLdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.adminapplication.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.adminapplication.SQLdatabase.MySQLHelper.USERS;


public class SQLEditor {

    static MySQLHelper helper;
    private static Context context;
    private static SQLEditor sqlEditor;
    private SQLEditor(Context context){
        this.context = context;
        helper=new MySQLHelper(context);


    }
    public static SQLEditor newInstance(Context context){
        if (sqlEditor==null) {
            sqlEditor=new SQLEditor(context);
        }
        return sqlEditor;
    }


    public static void addToUsers(User user)  {
        if (!isLessonFavorite(user.getEmail())) {
            Log.e("new user","user is: "+user.getName());
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MySQLHelper.NAME, user.getName());
            values.put(MySQLHelper.EMAIL, user.getEmail());
            values.put(MySQLHelper.PASSWORD, user.getPassword());
            values.put(MySQLHelper.IMAGE, user.getImage());
            values.put(MySQLHelper.PHONE, user.getPhone());
            values.put(MySQLHelper.BALANCE, user.getBalance());

            db.insert(USERS, null, values) ;
            db.close();}

    }


    public static List<User> getAllUsers(){
        List<User> list = new ArrayList<>();

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor=null;
            cursor= db.query(USERS,null,null,null,null,null,1+" ASC");
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            String email = cursor.getString(1);
            String pssword = cursor.getString(2);
            String image = cursor.getString(3);
            String phone = cursor.getString(4);
            int balance = cursor.getInt(5);
            Log.e("database balance",balance+" sd");
            list.add(new User(name,email, pssword,image,phone,balance));
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * used to look for particular item
     * @param email
     */
    public static Boolean isLessonFavorite(@NonNull String email) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS +" WHERE "+MySQLHelper.NAME +"=?", new String[]{email});
        Boolean isItemFound = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isItemFound;
    }

}


