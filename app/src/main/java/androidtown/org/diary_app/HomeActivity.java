package androidtown.org.diary_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity { // AppCompatActivity를 상속받아야 합니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home); // 홈 화면의 레이아웃을 설정합니다.
    }
}
