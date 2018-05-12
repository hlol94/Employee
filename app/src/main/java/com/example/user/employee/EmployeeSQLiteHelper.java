package com.example.user.employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by user on 29/04/2018.
 */

public class EmployeeSQLiteHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "employeedb";
    static final int DB_VERSION = 1;


    public EmployeeSQLiteHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE EMP_TB (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NAME TEXT, GENDER TEXT, COUNTRY TEXT, DOB TEXT, SALARY FLOAT, IMG BLOB) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addEmployee (Employee e){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("NAME",e.getEmpName());
        values.put("GENDER",e.getEmpGender());
        values.put("COUNTRY",e.getEmpCountry());
        values.put("SALARY",e.getEmpSalary());
        values.put("DOB",e.getDOB());
        values.put("IMG",e.getEmpImg());

        db.insert("EMP_TB",null,values);

    }

    public void updateEmployee (Employee e){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("NAME",e.getEmpName());
        values.put("GENDER",e.getEmpGender());
        values.put("COUNTRY",e.getEmpCountry());
        values.put("SALARY",e.getEmpSalary());
        values.put("DOB",e.getDOB());
       // values.put("IMG",e.getEmpImg());

        db.update("EMP_TB",values,"ID=?", new String[]{String.valueOf(e.getEmpID())});

    }

    public void deleteEmployee (int id){

        SQLiteDatabase db = getWritableDatabase();


        db.delete("EMP_TB","ID=?", new String[]{String.valueOf(id)});

        db.close();

    }

    public ArrayList<Employee> getAllEmployees(){

        ArrayList<Employee> empList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM EMP_TB";

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.moveToFirst()){

            do {
                Employee e = new Employee();
                //0 = id , name = 1

                e.setEmpID(cursor.getInt(0));
                e.setEmpName(cursor.getString(1));
                e.setEmpGender(cursor.getString(2));
                e.setEmpCountry(cursor.getString(3));
                e.setDOB(cursor.getString(4));
                e.setEmpSalary(cursor.getFloat(5));
                e.setEmpImg(cursor.getBlob(6));

                empList.add(e);

            }while (cursor.moveToNext());

        }

        return empList;
    }
}
