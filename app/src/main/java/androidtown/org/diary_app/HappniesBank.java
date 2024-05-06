package androidtown.org.diary_app;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HappniesBank extends AppCompatActivity {

    private CalendarView calendarView;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.happnies_bank);

        // CalendarView 및 Fragment Container 초기화
        calendarView = findViewById(R.id.calendar_view);
        fragmentContainer = findViewById(R.id.fragment_container);

        // CalendarView의 날짜 선택 리스너 설정
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 선택한 날짜에 따라 HappyFragment를 로드하여 표시합니다.
                loadHappyFragment(year, month, dayOfMonth);
            }
        });
    }

    private void loadHappyFragment(int year, int month, int dayOfMonth) {
        // HappyFragment를 동적으로 생성하여 사용자가 선택한 날짜에 대한 정보를 표시합니다.
        HappyFragment happyFragment = new HappyFragment();
        // 선택된 날짜 정보를 Bundle에 추가하여 Fragment로 전달합니다.
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("dayOfMonth", dayOfMonth);
        happyFragment.setArguments(bundle);

        // Fragment를 Fragment Container에 표시합니다.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, happyFragment)
                .commit();
    }
}