package com.example.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class insertandelete extends AppCompatActivity {
    EditText et1;
    EditText et2;
    ListView ls1;
    ListView ls2;
    Button bt1;
    Button bt2;
    ImageButton bt3;
    DBHelper dbHelper;
    SQLiteDatabase db = null;
    Cursor cursor;
    ArrayAdapter adapter;
    ArrayAdapter adapter2;

    void list(){
        cursor = db.rawQuery("SELECT * FROM tableName", null);
        startManagingCursor(cursor);

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);

        while (cursor.moveToNext()) {
            adapter.add(cursor.getString(0));
            adapter2.add(cursor.getString(1));
        }

        ls1.setAdapter(adapter);
        ls2.setAdapter(adapter2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertandelete);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        ls1 = findViewById(R.id.ls1);
        ls2 = findViewById(R.id.ls2);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3=findViewById(R.id.bt3);

        dbHelper = new DBHelper(this, 4);
        db = dbHelper.getWritableDatabase();
        list();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String english = et1.getText().toString();
                String korean = et2.getText().toString();
                if(et1.getText().toString().equals("") || et2.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"단어와 뜻을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    db.execSQL("INSERT INTO tableName VALUES('" + english + "', '" + korean + "');");
                    Toast.makeText(getApplicationContext(), "단어 추가", Toast.LENGTH_SHORT).show();
                    et1.setText(null);
                    et2.setText(null);

                }
                list();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String english = et1.getText().toString();
                db.execSQL("DELETE FROM tableName WHERE english = '"+english+"';");
                Toast.makeText(getApplicationContext(), "단어 삭제", Toast.LENGTH_SHORT).show();
                et1.setText(null);
                et2.setText(null);
                list();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
