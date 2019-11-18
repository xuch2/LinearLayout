package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText et, et2;
    TextView tv;

    SQLiteDatabase db;

    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText)findViewById(R.id.et);
        et2 = (EditText)findViewById(R.id.et2);
        tv = (TextView) findViewById(R.id.tv);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbName = et.getText().toString();
                createDB(dbName);
            }
        });

        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableName = et2.getText().toString();
                createTable(tableName);

                insertRecord();
            }
        });
    }

    private void createDB(String name) {
        println("createDB Called");

        db = openOrCreateDatabase(
                name, MODE_PRIVATE, null
        );

        println("DB Created");
    }

    private void createTable(String name) {
        println("createTable Called");

        if(db == null) {
            println("First, Do createDB()");
            return;
        }

        db.execSQL("create table if not exists " + name + "("
                + "_id integer PRIMARY KEY autoincrement, "
                + "name text, "
                + "age integer, "
                + "mobile text)"
        );

        println("Table Created: " + name);
    }

    private void insertRecord() {
        println("insertRecord() Called");

        if(db == null) {
            println("First, Do createDB()");
            return;
        }

        if(tableName == null) {
            println("First, Do createTable()");
            return;
        }

        db.execSQL("insert into " + tableName
                + "(name, age, mobile) "
                + "values "
                + "('Chris', 27, 010-7737-2743)"
        );

        println("Record Added");
    }

    public void println(String data) {
        tv.append(data + "\n");
    }
}
