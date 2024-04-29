package androidtown.org.diary_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DiaryDbHelper extends SQLiteOpenHelper {
    // 데이터베이스 이름과 버전 설정
    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 1;

    // 생성자
    public DiaryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 데이터베이스 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_DIARY_TABLE = "CREATE TABLE " +
                DiaryContract.DiaryEntry.TABLE_NAME + " (" +
                DiaryContract.DiaryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DiaryContract.DiaryEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                DiaryContract.DiaryEntry.COLUMN_CONTENT + " TEXT NOT NULL," +
                DiaryContract.DiaryEntry.COLUMN_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_DIARY_TABLE);
    }

    // 데이터베이스 업그레이드 로직 (여기서는 테이블을 삭제하고 다시 생성)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DiaryContract.DiaryEntry.TABLE_NAME);
        onCreate(db);
    }
}
