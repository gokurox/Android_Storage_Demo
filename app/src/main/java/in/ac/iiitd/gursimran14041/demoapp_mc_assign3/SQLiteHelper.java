package in.ac.iiitd.gursimran14041.demoapp_mc_assign3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gursimran Singh on 29-09-2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static SQLiteHelper INSTANCE = null;

    public static final String DATABASE_NAME = "demoAppDB.db";
    static class StudentTable {
        static String TABLE_NAME = "student_data";
        static String COL_1 = "_id";
        static String COL_2 = "roll_no";
        static String COL_3 = "name";
        static String COL_4 = "semester";
    }

    private SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static SQLiteHelper getInstance (Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SQLiteHelper (context, DATABASE_NAME, null, 1);
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + StudentTable.TABLE_NAME + " (" +
                        StudentTable.COL_1 + " INTEGER PRIMARY KEY," +
                        StudentTable.COL_2 + " TEXT UNIQUE," +
                        StudentTable.COL_3 + " TEXT," +
                        StudentTable.COL_4 + " INTEGER"
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(
                "DROP TABLE IF EXISTS " + StudentTable.TABLE_NAME
        );
        onCreate(sqLiteDatabase);
    }

    public boolean insertRecord (String roll_no, String name, int semester) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (StudentTable.COL_2, roll_no);
        cv.put (StudentTable.COL_3, name);
        cv.put (StudentTable.COL_4, semester);

        return (db.insert(StudentTable.TABLE_NAME, null, cv) >= 0);
    }

    public Cursor getData (String roll_no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery (
                "SELECT * FROM " + StudentTable.TABLE_NAME + " WHERE " + StudentTable.COL_2 +
                        " = " + roll_no,
                null
        );
        return res;
    }

    public Cursor getData () {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery (
                "SELECT * FROM " + StudentTable.TABLE_NAME,
                null
        );
        return res;
    }

    public boolean updateRecord (String roll_no, String name, int semester) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (StudentTable.COL_2, roll_no);
        cv.put (StudentTable.COL_3, name);
        cv.put (StudentTable.COL_4, semester);

        return (db.update(StudentTable.TABLE_NAME,
                cv,
                StudentTable.COL_2 + " = ?",
                new String[] {roll_no}) >= 0);
    }

    public boolean deleteRecord (String roll_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        return (db.delete(
                StudentTable.TABLE_NAME,
                StudentTable.COL_2 + " = ?",
                new String[] {roll_no}) > 0);
    }
}
