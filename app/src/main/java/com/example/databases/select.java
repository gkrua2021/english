package com.example.databases;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Locale;
import static android.speech.tts.TextToSpeech.ERROR;
import android.widget.Toast;


public class select extends AppCompatActivity {
    private TextToSpeech tts;
    ImageButton bt1, bt3, bt2;
    EditText et1;
    TextView tv1;
    TextView tv2;
    DBHelper dbHelper;
    SQLiteDatabase db = null;
    Cursor cursor;

    void tts(){
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    void listi(){
        if (cursor.moveToNext()) {
            String word = cursor.getString(0);
            String kora = cursor.getString(1);
            tv1.setText("" +word);
            tv2.setText("" +kora);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        et1=findViewById(R.id.et1);
        bt1=findViewById(R.id.bt1);
        bt3=findViewById(R.id.bt3);
        bt2=findViewById(R.id.bt2);

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);

        dbHelper = new DBHelper(this, 4);
        db = dbHelper.getReadableDatabase();
        tts();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"영어 단어를 입력해주세요",Toast.LENGTH_SHORT).show();
                }

                else {
                    String english = et1.getText().toString();
                    cursor = db.rawQuery("SELECT * FROM tableName WHERE english = ?", new String[]{english});
                    startManagingCursor(cursor);
                    et1.setText(null);
                    listi();
                }
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv1.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"단어 검색후 사용해 주세요",Toast.LENGTH_SHORT).show();
                }
                else{
                    tts.speak(tv1.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }

}

