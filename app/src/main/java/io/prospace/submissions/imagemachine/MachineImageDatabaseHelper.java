package io.prospace.submissions.imagemachine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class MachineImageDatabaseHelper extends SQLiteOpenHelper {

    public MachineImageDatabaseHelper(@Nullable Context context, @Nullable String name,
                                      @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
    }

    public void insertMachineImages(String name, byte[] image) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sqLiteInsertQuery = "INSERT INTO IMAGES VALUES (NULL, ?, ?)";

        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sqLiteInsertQuery);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, name);
        sqLiteStatement.bindBlob(2, image);

        sqLiteStatement.executeInsert();
    }

//    public void deleteMachineImages(int id) {
//        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        String sqLiteDeleteQuery = "DELETE FROM IMAGES WHERE id=?";
//
//        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sqLiteDeleteQuery);
//        sqLiteStatement.clearBindings();
//        sqLiteStatement.bindDouble(1, id);
//
//        sqLiteStatement.execute();
//        sqLiteStatement.close();
//    }

//    public Cursor getQuery(String sql) {
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        return sqLiteDatabase.rawQuery(sql, null);
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
