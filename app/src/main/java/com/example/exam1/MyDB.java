package com.example.exam1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {
    public static final String TableTen = "Contact_191203366";
    public static final String Id = "Id";

    public static final String Ten = "Ten";
    public static final String SDT = "SDT";

    public MyDB(Context context, String Ten, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Ten, factory, version);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase sqLiteDatabase) {
        // Tạo câu sql để tạo bảng TableContact
        String sqlCreate = "Create table if not exists "
                + TableTen + "("
                + Id + " Integer Primary key autoincrement, "
                + Ten + " Text,"
                + SDT + " Text" + ")";
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("Drop table if exists " + TableTen);
        onCreate(sqLiteDatabase);
    }

    // Hàm thêm một contact
    public void addContact(Contact_191203366 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Ten, contact.getTen());
        value.put(SDT, contact.getSDT());
        db.insert(TableTen, null, value);
        db.close();

    }

    //Cap nhat contact
    public void updateContact(int id, Contact_191203366 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id, contact.getId());
        value.put(Ten, contact.getTen());
        value.put(SDT, contact.getSDT());
        // Tham số cuối là mảng tương ứng với ?
        db.update(TableTen, value, Id + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    //Xoa
    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete from " + TableTen + " Where " + Id + "=" + id;
        db.execSQL(sql);
        db.close();
    }

    // Lấy tất cả các dòng của bảng
    public ArrayList<Contact_191203366> getAllcontacts() {
        ArrayList<Contact_191203366> list = new ArrayList<>();
        // câu truy vấn
        String sql = "Select * from " + TableTen+" ORDER BY "+Ten;
        // Lấy đối tuojng csdl
        SQLiteDatabase db = this.getReadableDatabase();
        //Chay câu truy vấn trả vể Cursor
        Cursor cursor = db.rawQuery(sql, null);
        //Tạo kết quả trả về
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Contact_191203366 contact = new Contact_191203366(
                        cursor.getString(1),
                        cursor.getString(2)
                );
                contact.setId(cursor.getInt(0));
                list.add(contact);
            }
        }
        return list;
    }
}

