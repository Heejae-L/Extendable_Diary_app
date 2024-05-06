package androidtown.org.diary_app;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidtown.org.diary_app.MyPaintView;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MyPaintBoard extends AppCompatActivity {

    MyPaintView view;
    int tColor,n=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint_board);
        view = new MyPaintView(this);

        LinearLayout container = findViewById(R.id.container);
        Resources res = getResources();


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        container.addView(view, params);

        RadioButton rBtn1 = findViewById(R.id.radioButton);
        RadioButton rBtn2 = findViewById(R.id.radioButton2);
        RadioButton rBtn3 = findViewById(R.id.radioButton3);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton) {
                    view.setCap(0);
                } else if (checkedId == R.id.radioButton2) {
                    view.setCap(1);
                } else if (checkedId == R.id.radioButton3) {
                    view.setCap(2);
                }
            }
        });

        Button btn=findViewById(R.id.colorPickerButton);
        Button btn2=findViewById(R.id.thickPickerButton);
        Button saveBtn = findViewById(R.id.paint_save);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void show() {
        final EditText editText=new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog Title");
        builder.setMessage("굵기 입력");
        builder.setView(editText);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        view.setStrokeWidth(Integer.parseInt(editText.getText().toString()));

                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();

    }

    private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, tColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Toast.makeText(getApplicationContext(),""+tColor,Toast.LENGTH_LONG).show();
                view.setColor(color);
            }
        });
        colorPicker.show();
    }

}
