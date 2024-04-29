package androidtown.org.diary_app;

import android.provider.BaseColumns;

public final class DiaryContract {
    // 이 클래스의 인스턴스화를 방지합니다.
    private DiaryContract() {}

    // "diary" 테이블을 위한 내부 클래스를 정의합니다.
    public static final class DiaryEntry implements BaseColumns {
        public static final String TABLE_NAME = "diary";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_DATE = "date";
    }
}