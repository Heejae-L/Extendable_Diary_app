package androidtown.org.diary_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 1;


    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // USER 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS USER (" +
                "user_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "number TEXT, " +
                "image BLOB, " +
                "birth DATE, " +
                "age INTEGER, " +
                "sex TEXT, " +
                "password TEXT" +
                ");");

        // MEMO 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS MEMO (" +
                "memo_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "feeling TEXT, " +
                "date DATE, " +
                "body_text TEXT, " +
                "image BLOB, " +
                "author_ID INTEGER, " +
                "FOREIGN KEY (author_ID) REFERENCES USER(user_ID)" +
                ");");

        // CALENDAR 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS CALENDAR (" +
                "calendar_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "private INTEGER, " +
                "memo TEXT, " +
                "start_date DATE, " +
                "end_date DATE, " +
                "title TEXT, " +
                "user_ID INTEGER, " +
                "FOREIGN KEY (user_ID) REFERENCES USER(user_ID)" +
                ");");

        // GROUP 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS GROUPS (" +
                "group_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "description TEXT, " +
                "title TEXT, " +
                "date DATE" +
                ");");

        // USER와 GROUP 사이의 다대다 관계를 위한 연결 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS USER_GROUP (" +
                "user_ID INTEGER, " +
                "group_ID INTEGER, " +
                "FOREIGN KEY (user_ID) REFERENCES USER(user_ID), " +
                "FOREIGN KEY (group_ID) REFERENCES GROUPS(group_ID), " +
                "PRIMARY KEY (user_ID, group_ID)" +
                ");");

        // GROUP과 MEMO 사이의 일대다 관계를 위한 연결 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS GROUP_MEMO (" +
                "group_ID INTEGER, " +
                "memo_ID INTEGER, " +
                "FOREIGN KEY (group_ID) REFERENCES GROUPㅈ(group_ID), " +
                "FOREIGN KEY (memo_ID) REFERENCES MEMO(memo_ID), " +
                "PRIMARY KEY (group_ID, memo_ID)" +
                ");");

        // GROUP과 CALENDAR 사이의 다대다 관계를 위한 연결 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS share_calendar (" +
                "calendar_ID INTEGER, " +
                "group_ID INTEGER, " +
                "FOREIGN KEY (calendar_ID) REFERENCES CALENDAR(calendar_ID), " +
                "FOREIGN KEY (group_ID) REFERENCES GROUPS(group_ID), " +
                "PRIMARY KEY (calendar_ID, group_ID)" +
                ");");

        // DIARY_ENTRY 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS DIARY_ENTRY (" +
                "author_ID INT, " +
                "entry_date DATE, " +
                "entry_weather VARCHAR(255), " +
                "entry_mood BLOB, " +
                "entry_location TEXT(255), " +
                "entry_image BLOB, " +
                "entry_voice VARCHAR(255), " +
                "entry_content TEXT, " +
                "PRIMARY KEY(author_ID, entry_date), " +
                "FOREIGN KEY (author_ID) REFERENCES USER(user_ID)" +
                ");");

        // HAPPINESS_BOX 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS HAPPINESS_BOX (" +
                "author_ID INT, " +
                "box_date DATE, " +
                "PRIMARY KEY(author_ID, box_date), " +
                "FOREIGN KEY (author_ID, box_date) REFERENCES DIARY_ENTRY(author_ID, entry_date)" +
                ");");

        // NOTIFICATIONS 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS NOTIFICATIONS (" +
                "notification_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "author_ID INT, " +
                "content TEXT, " +
                "vibrate INTEGER, " + // 0: 무음, 1: 진동
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (author_ID) REFERENCES USER(user_ID)" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<String> getMemberNames (){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Name FROM Members",null);
        ArrayList<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        cursor.close();
        return result;
    }
    // 특정 사용자의 다이어리 제목과 날짜와 ID를 조회하는 메소드
    public List<String> getDiaryEntries(int userId) {
        List<String> entries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT entry_date, entry_content, memo_ID FROM DIARY_ENTRY WHERE author_ID = ?",
                new String[] { String.valueOf(userId) });

        int dateIndex = cursor.getColumnIndexOrThrow("entry_date"); // Use getColumnIndexOrThrow for expected columns
        int contentIndex = cursor.getColumnIndex("entry_content"); // Use getColumnIndex if the column might not exist
        int memoID = cursor.getColumnIndex("memo_ID");

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(dateIndex);
                String content = cursor.getString(contentIndex);
                String ID = cursor.getString(memoID);
                entries.add(date + " - " + content + "-" + ID);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return entries;
    }
    // 특정 메모 ID로 메모 데이터 조회
    public Memo getMemoById(int memoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Memo memo = null;

        Cursor cursor = db.rawQuery("SELECT feeling, date, body_text, image, author_ID FROM MEMO WHERE memo_ID = ?",
                new String[] { String.valueOf(memoId) });

        if (cursor.moveToFirst()) {
            String feeling = cursor.getString(cursor.getColumnIndexOrThrow("feeling"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String bodyText = cursor.getString(cursor.getColumnIndexOrThrow("body_text"));
            byte[] imgBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
            Bitmap image = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            int authorId = cursor.getInt(cursor.getColumnIndexOrThrow("author_ID"));

            memo = new Memo(memoId, feeling, date, bodyText, image, authorId);
        }
        cursor.close();
        db.close();

        return memo;
    }

}
