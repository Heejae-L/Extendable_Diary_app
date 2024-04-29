package androidtown.org.diary_app;

import android.graphics.Bitmap;

// Memo 클래스
public class Memo {
    private int memoId;
    private String feeling;
    private String date;
    private String bodyText;
    private Bitmap image;
    private int authorId;

    public Memo(int memoId, String feeling, String date, String bodyText, Bitmap image, int authorId) {
        this.memoId = memoId;
        this.feeling = feeling;
        this.date = date;
        this.bodyText = bodyText;
        this.image = image;
        this.authorId = authorId;
    }

    // Getter 메소드
    public int getMemoId() {
        return memoId;
    }

    public String getFeeling() {
        return feeling;
    }

    public String getDate() {
        return date;
    }

    public String getBodyText() {
        return bodyText;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getAuthorId() {
        return authorId;
    }

    // Setter 메소드
    public void setMemoId(int memoId) {
        this.memoId = memoId;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
