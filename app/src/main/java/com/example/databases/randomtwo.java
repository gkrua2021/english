package com.example.databases;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class randomtwo extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db = null;
    Cursor cursor;
    TextView tv1;
    EditText et1;
    ImageButton bt1;
    ImageButton bt2;
    int in;
    String[] a = new String [in];

    void randomf(){
        cursor = db.rawQuery("SELECT * FROM tableName ORDER BY random()", null);
        startManagingCursor(cursor);
    }

    void listi(){
        if (cursor.moveToNext()) {
            String word = cursor.getString(0);
            String kora = cursor.getString(1);
            a = word.split(" " + word);
            tv1.setText(" " + kora);
            in = 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomtwo);

        dbHelper = new DBHelper(this, 4);
        db = dbHelper.getReadableDatabase();
        tv1=findViewById(R.id.tv1);
        et1=findViewById(R.id.et1);
        bt1=findViewById(R.id.bt1);
        bt2=findViewById(R.id.bt2);
        randomf();
        listi();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().equals(a[in])){
                    Toast.makeText(getApplicationContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                    et1.setText(null);
                    randomf();
                    listi();
                }

                else if(et1.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "정답을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "오답입니다...", Toast.LENGTH_SHORT).show();
                    et1.setText(null);
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
}