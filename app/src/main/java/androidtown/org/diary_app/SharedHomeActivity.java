package androidtown.org.diary_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SharedHomeActivity extends AppCompatActivity {

    ListView sharedListView;
    String[] diaryEntries = {"Shared 1", "Shared 2", "Shared 3"}; // 예시 공유메모 항목


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_home);
        sharedListView = findViewById(R.id.shared_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diaryEntries);
        sharedListView.setAdapter(adapter);

        sharedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEntry = diaryEntries[position]; // 선택된 공유메모 항목
                openSharedMemo(selectedEntry); // 공유메모 화면으로 이동하는 메소드 호출
            }
        });
    }
    private void openSharedMemo(String selectedEntry) {
        Intent intent = new Intent(this, SharedMemoActivity.class); // MyDiaryActivity로 이동하는 Intent 생성
        intent.putExtra("entry_text", selectedEntry); // 선택된 다이어리 항목을 인텐트에 추가
        startActivity(intent); // 새로운 액티비티 시작
    }
}

