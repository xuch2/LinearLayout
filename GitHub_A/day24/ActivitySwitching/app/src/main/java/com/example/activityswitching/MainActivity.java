package com.example.activityswitching;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button menuBtn = (Button)findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Intent 는 Activity 의 Context 를 저장한다.
                   저번 시간에 학습한 Context Switching 개념에 입각하여
                   getApplicationContext() 를 통해서
                   현재 Context 를 백업하고
                   MenuActivity 로 제어권을 넘기도록 Intent 를 만든다.
                 */
                Intent intent = new Intent(
                        getApplicationContext(),
                        MenuActivity.class
                );
                /* 만든 Intent 를 통해 MenuActivity 를 호출한다.
                   Request Code 는 REQUEST_CODE_MENU 가 된다. */
                startActivityForResult(
                        intent,
                        REQUEST_CODE_MENU
                );
            }
        });
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_MENU) {
            Toast.makeText(
                getApplicationContext(),
                "onActivityResult Called: " + requestCode,
                Toast.LENGTH_SHORT
            ).show();

            if(resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                Toast.makeText(
                    getApplicationContext(),
                    "Response name: " + name,
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}

/* 문제 22. 아래와 같은 버튼들을 만든다.
   강화, 승급, 아이템, 랭킹
   버튼을 누르면 각각의 전용 Activity 로 이동을 하도록 한다.
   각각의 전용 Activity 에는 나가기 버튼이 있다.
   나가기 버튼을 누르면 원본 화면으로 복귀를 하도록 만들어보자!
   Request Code 와 Result Code 를
   Toast 방식이 아닌 TextView 로 보여주도록 하자! */