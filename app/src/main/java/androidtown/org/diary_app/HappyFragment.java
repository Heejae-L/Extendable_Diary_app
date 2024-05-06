package androidtown.org.diary_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class HappyFragment extends Fragment {

    private EditText diaryEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragment의 레이아웃을 설정합니다.
        View view = inflater.inflate(R.layout.happnies_bank_diary, container, false);

        // EditText를 초기화합니다.
        diaryEditText = view.findViewById(R.id.happnies_bank_diary_content);

        return view;
    }
}