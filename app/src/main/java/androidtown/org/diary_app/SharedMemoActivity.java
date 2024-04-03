package androidtown.org.diary_app;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SharedMemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_memo);

        TextView diaryTextView = findViewById(R.id.shared_memo_entry);
        String entryText = getIntent().getStringExtra("entry_text"); // MainActivity로부터 전달된 다이어리 항목 텍스트 가져오기
        diaryTextView.setText(entryText); // 다이어리 화면에 다이어리 항목 텍스트 설정
    }
}
