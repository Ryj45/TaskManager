package c346.rp.edu.sg.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "task.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_REMIND_TIME = "remind_time";

    public DBHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSQL = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + COLUMN_NAME + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_REMIND_TIME + " INTEGER )";
        db.execSQL(createSQL);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public long insert(String name, String description, int remindTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_REMIND_TIME, remindTime);
        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        if(result == -1){
            Log.d("DBHelper", "Insert failed");
        }
        return result;
    }

    public ArrayList<Task> getTask(){
        ArrayList<Task> tasks = new ArrayList<Task>();

        String selectSQL = "SELECT " + COLUMN_ID +", " + COLUMN_NAME + "," +
                COLUMN_DESCRIPTION + ", " + COLUMN_REMIND_TIME + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectSQL, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int remindTime = cursor.getInt(3);

                Task task = new Task(id, name, description, remindTime);
                tasks.add(task);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }
}
