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

    /* SQLite DB 를 사용하기 위한 변수를 만든다.
       이 변수는 SQLite 에 직접 접근할 수 있게 만들어준다.
       또한 SQLite 는 Local(로컬)에서 사용하는 DB 에 해당한다.
       그러므로 Remote(원격)을 같이 생각하고 있다면
       소켓 프로그래밍을 통해 json 등을 활용하는 것이 좋다. */
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

        /* 만약 DB name 이 이미 존재한다면
           db 에 존재하는 정보 객체를
           생성하는 것이 아닌 기존 정보를 연결해준다(open)

           만약 기존 정보가 없다면 새로 만든다.
           외부에서 함부로 접근하지 못하게 만든다. */
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

        /* 실제 DB 에서도 SQL Query 문들이 있다.
           이 Query 문을 작성하여 SQL 을 제어한다고 보면 된다.
           우선 create table if not exists 'name' 은
           해당 이름의 table 이 있는지 검사하고
           없으면 table 을 새로 만드는 작업을 담당한다.

           name 이후에 오는 것은
           테이블 이름의 구조가 어떻게 만들어지는지를 결정한다.
           구조는 id 값(정수형), 이름(문자), 나이(정수형),
           폰 번호(문자)로 구성된다고 보면 된다.
           여기서 주의할 것은 이 작업은
           테이블 구조만을 만들었다는 것이다. */
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

        /* 마찬가지로 SQL Query 문에 해당한다.
           insert into 는 특정한 테이블에 값을 넣을 때 사용
           뒤에 오는 tableName 에 값을 넣는 작업이 됩니다.
           그 뒤에는 어떤 데이터들이 어디에 배치될지를 결정
           (name - 'Chris', age - 27, mobile - 010-7737-2743)

           tableName 뒤에 설정하고자 하는 table 구조를
           () 에 넣어서 배치하고
           집어넣고자 하는 값은 values 뒤쪽에 쓴다. */
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
