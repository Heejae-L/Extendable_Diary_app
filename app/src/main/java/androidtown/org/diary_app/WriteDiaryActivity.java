package androidtown.org.diary_app;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;


public class WriteDiaryActivity extends AppCompatActivity {

    private static final int YOUR_REQUEST_CODE = 123;
    //Floating menu
    private FloatingActionButton fab_main;
    private FloatingActionButton fab_weather;
    private FloatingActionButton fab_mood;
    private FloatingActionButton fab_location;
    private FloatingActionButton fab_image;
    private FloatingActionButton fab_voice;
    private Animation fab_open, fab_close;
    private boolean fabMain_status = false;

    DatePicker diary_date;
    EditText diary_content;
    Button save_button;


    private ImageView selectedWeatherImageView;
    private ImageView selectedMoodImageView;
    private int[] weatherImageIds = {R.drawable.weather1, R.drawable.weather2, R.drawable.weather3, R.drawable.weather4};
    private int[] moodImageIds = {R.drawable.icon_draw, R.drawable.mood2, R.drawable.mood3, R.drawable.mood4};


    private static final int PICK_IMAGES_REQUEST = 1;

    private ImageView selected_img1, selected_img2, selected_img3;

    private VoiceHandler voiceHandler;
    private ImageView recorded_voice;

    //private LocationHandler locationHandler;

    private Context mContext;
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_writer);
        mContext = getApplicationContext();

        diary_date = findViewById(R.id.diary_Date);
        diary_content = findViewById(R.id.diary_content);
        save_button = findViewById(R.id.save_button);
        //save_button.setClickable(false);

        // ImageView 초기화
        selected_img1 = findViewById(R.id.selected_img1);
        selected_img2 = findViewById(R.id.selected_img2);
        selected_img3 = findViewById(R.id.selected_img3);

        //Voice 초기화
        recorded_voice = findViewById(R.id.recorded_voice);
        voiceHandler = new VoiceHandler(this);

        sqLiteHelper = new SQLiteHelper(this);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        diary_date.init(year, month, dayOfMonth, null);

        selectedWeatherImageView = findViewById(R.id.selected_weather);
        selectedMoodImageView = findViewById(R.id.selected_mood);

        fab_main = findViewById(R.id.fab_main);
        fab_weather = findViewById(R.id.fab_weather);
        fab_mood = findViewById(R.id.fab_mood);
        fab_location = findViewById(R.id.fab_location);
        fab_image = findViewById(R.id.fab_image);
        fab_voice = findViewById(R.id.fab_voice);

        diary_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length() >3){
                    save_button.setEnabled(true);
                }
                else{
                    save_button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //초기 상태에서 editText에 값이 있을 경우
        if (diary_content.getText().toString().trim().length() > 0) {
            save_button.setEnabled(true);
        } else {
            save_button.setEnabled(false);
        }

        //floating menu option
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });

        fab_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageBottomSheet(weatherImageIds, selectedWeatherImageView);
                Toast.makeText(WriteDiaryActivity.this, "weather 버튼 클릭", Toast.LENGTH_SHORT).show();

            }
        });

        fab_mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageBottomSheet(moodImageIds, selectedMoodImageView);
                Toast.makeText(WriteDiaryActivity.this, "mood 버튼 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        // locationHandler = new LocationHandler(this);

        fab_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WriteDiaryActivity.this, "location 버튼 클릭", Toast.LENGTH_SHORT).show();
                //locationHandler.searchLocation();
                //구글 지도를 사용해서
            }
        });

        fab_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WriteDiaryActivity.this, "image 버튼 클릭", Toast.LENGTH_SHORT).show();
                pickImages();
            }
        });


        //녹음된 이미지 버튼 누르면 재생
        recorded_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceHandler.playAudio();
            }
        });

        //녹음 하려고 누르는 버튼
        fab_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WriteDiaryActivity.this, "voice 버튼 클릭", Toast.LENGTH_SHORT).show();
                voiceHandler.startRecording();
            }
        });


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WriteDiaryActivity.this, "saved", Toast.LENGTH_SHORT).show();
                //저장 데이터 넘기기
            }
        });
    }

    //여기서부터 onCreate 밖
    //floating button 에니메이션
    public void toggleFab() {
        if (fabMain_status) {
            // 플로팅 액션 버튼 닫기
            ObjectAnimator[] animations = {
                    ObjectAnimator.ofFloat(fab_weather, "translationY", 0f),
                    ObjectAnimator.ofFloat(fab_mood, "translationY", 0f),
                    ObjectAnimator.ofFloat(fab_location, "translationY", 0f),
                    ObjectAnimator.ofFloat(fab_image, "translationY", 0f),
                    ObjectAnimator.ofFloat(fab_voice, "translationY", 0f)
            };
            for (ObjectAnimator animation : animations) {
                animation.start();
            }
            fab_main.setImageResource(R.drawable.icon_menu);
        } else {
            // 플로팅 액션 버튼 열기
            ObjectAnimator[] animations = {
                    ObjectAnimator.ofFloat(fab_weather, "translationY", -200f),
                    ObjectAnimator.ofFloat(fab_mood, "translationY", -400f),
                    ObjectAnimator.ofFloat(fab_location, "translationY", -600f),
                    ObjectAnimator.ofFloat(fab_image, "translationY", -800f),
                    ObjectAnimator.ofFloat(fab_voice, "translationY", -1000f)
            };
            for (ObjectAnimator animation : animations) {
                animation.start();
            }
            fab_main.setImageResource(R.drawable.icon_add_menu);
        }
        fabMain_status = !fabMain_status;
    }

    //weather버튼, mood버튼 누르면 이미지 세팅하고, 선택한 이미지 보여주기
    private void showImageBottomSheet(int[] imageIds, final ImageView selectedImageView) {
        ImageBottomSheetFragment bottomSheetFragment = new ImageBottomSheetFragment();
        bottomSheetFragment.setImageIds(imageIds); // 이미지 정보 전달
        bottomSheetFragment.setOnImageSelectedListener(new ImageBottomSheetFragment.OnImageSelectedListener() {
            @Override
            public void onImageSelected(int selectedImageId) {

                // 선택된 이미지가 moodImageIds 배열 중 첫 번째 이미지인 경우 MyPaintBoard 액티비티 실행
                if (selectedImageId == moodImageIds[0]) {
                    Intent intent = new Intent(WriteDiaryActivity.this, MyPaintBoard.class);
                    startActivity(intent);
                } else {
                    // 그 외의 경우 선택된 이미지를 selectedImageView에 표시
                    selectedImageView.setImageResource(selectedImageId);
                }

            }
        });
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    //image 버튼 누르면 photo picker 사용해서 이미지 선택하기
    private void pickImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK && data != null) {
            // 이미지 선택 결과 처리 코드
            ArrayList<Uri> selectedImages = new ArrayList<>();
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(imageUri);
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                selectedImages.add(imageUri);
            }

            // 선택한 이미지를 ImageView에 설정
            for (int i = 0; i < selectedImages.size(); i++) {
                Uri imageUri = selectedImages.get(i);
                ImageView imageView = getSelectedImageView(i);
                imageView.setImageURI(imageUri);
            }
        } else if (requestCode == YOUR_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // MyPaintBoard 액티비티의 결과 처리 코드
            Bitmap drawnBitmap = data.getParcelableExtra("drawn_image");
            selectedMoodImageView.setImageBitmap(drawnBitmap);
        }
    }


    //선택한 이미지 imageview에 넣기
    private ImageView getSelectedImageView(int index) {
        if (index == 0) {
            return selected_img1;
        } else if (index == 1) {
            return selected_img2;
        } else if (index == 2) {
            return selected_img3;
        }  else {
            return null;
        }
    }
    // 여기까지 이미지 버튼


    //voice 버튼 누르면 녹음시작, 녹음이 되면 이미지화해서 저장, 저장된 이미지를 누르면 녹음된 음성 재생
    //voice 녹음
    public void stopRecording(View view) {
        voiceHandler.stopRecording();
        String audioFilePath = voiceHandler.getAudioFilePath();
        if (audioFilePath != null) {
            Toast.makeText(this, "Recording saved: " + audioFilePath, Toast.LENGTH_SHORT).show();
            recorded_voice.setImageURI(Uri.parse(audioFilePath));
        }
    }

    //voice 녹음 멈추기
    @Override
    protected void onStop() {
        super.onStop();
        voiceHandler.releaseMedia();
    }




}


