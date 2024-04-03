package androidtown.org.diary_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    ListView diaryListView;
    String[] diaryEntries = {"Entry 1", "Entry 2", "Entry 3"}; // 예시 다이어리 항목
    ImageButton new_diary_button;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_main_screen);

        // Top App Bar 설정
        Toolbar toolbar = findViewById(R.id.top_app_bar);
        setSupportActionBar(toolbar);

        diaryListView = findViewById(R.id.diary_list);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diaryEntries);
        diaryListView.setAdapter(adapter);

        new_diary_button = (android.widget.ImageButton)findViewById(R.id.new_diary_button);
        new_diary_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaryWriter();
            }
        });



        diaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEntry = diaryEntries[position]; // 선택된 다이어리 항목
                openDiary(selectedEntry); // 다이어리 화면으로 이동하는 메소드 호출
            }
        });


        // Bottom NavigationView에서 항목을 클릭했을 때 동작할 리스너 설정
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_profile) {
                    // Home 화면으로 이동하는 코드
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_shared) {
                    // Dashboard 화면으로 이동하는 코드
                    startActivity(new Intent(MainActivity.this, SharedHomeActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_calendar) {
                    // Notifications 화면으로 이동하는 코드
                    startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_home) {
                    // Notifications 화면으로 이동하는 코드
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
    private void openDiary(String selectedEntry) {
        Intent intent = new Intent(this, MyDiaryActivity.class); // MyDiaryActivity로 이동하는 Intent 생성
        intent.putExtra("entry_text", selectedEntry); // 선택된 다이어리 항목을 인텐트에 추가
        startActivity(intent); // 새로운 액티비티 시작
    }
    private void openDiaryWriter() {
        Intent intent = new Intent(this, WriteDiaryActivity.class); // MyDiaryActivity로 이동하는 Intent 생성
        startActivity(intent); // 새로운 액티비티 시작
    }
}
