package com.example.adminapplication.SQLdatabase;

public class SQLUtility {


    public static String createFavoriteTable(String tableName){
        return   "CREATE TABLE "+tableName +"("+MySQLHelper.NAME +" TEXT , "+
                MySQLHelper.EMAIL+" TEXT,"+MySQLHelper.PASSWORD+" TEXT,"+
                MySQLHelper.IMAGE+" TEXT,"+MySQLHelper.PHONE+" TEXT,"+
                MySQLHelper.BALANCE+" TEXT); ";

    }

}
