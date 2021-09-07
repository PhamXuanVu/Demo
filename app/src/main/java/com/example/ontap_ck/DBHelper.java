package com.example.ontap_ck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

    public class DBHelper extends SQLiteOpenHelper {
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE Student("+"id integer primary key,"
                    +"name text,"
                    + "age)");
        }
        public DBHelper(Context context){
            super(context,"DB",null,1);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Student");
            onCreate(db);
        }

        public int insert(Student student){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            //Neu form add có id
//            contentValues.put("id",student.getId());
            contentValues.put("name",student.getName());
            contentValues.put("age",student.getAge());

            int result = (int) db.  insert("Student", null,contentValues);
            db.close();
            return result;
        }

        public ArrayList<Student> getAll(){
            ArrayList<Student> students = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Student",null);
            if (cursor != null)
                cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                students.add(new Student(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            return students;
        }

//        public ArrayList<Student> getId(String id){
//            ArrayList<Student> students = new ArrayList<>();
//            SQLiteDatabase db = this.getWritableDatabase();
//            Cursor cursor = db.rawQuery("select * from Student where id="+id,null);
//            if (cursor != null)
//                cursor.moveToFirst();
//            Student student = new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2));
//            cursor.moveToNext();
//            students.add(student);
//            cursor.close();
//            db.close();
//            return students;
//        }

//        public void update(String id , String name){
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("name",name);
//            db.update("Student",contentValues,"id =?", new String[]{String.valueOf(id)});
//            db.close();
//
//        }
//
//        public void delete(String id){
//            SQLiteDatabase db = this.getWritableDatabase();
//            db.delete("Student","id =?", new String[]{String.valueOf(id)});
//            db.close();
//        }
    }

