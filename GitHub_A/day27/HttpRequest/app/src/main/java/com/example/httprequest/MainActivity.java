package com.example.httprequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText)findViewById(R.id.et);
        tv = (TextView)findViewById(R.id.tv);

        Button btn = (Button)findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* EditText 에 우리가 입력한
                   HTTP 주소를 String 형태로 가져온다. */
                final String uriStr = et.getText().toString();

                /* Thread 또한 프로세스라고 생각하면 되겠다.
                   약간의 차이점이 있다면
                   프로세스는 메모리를 공유한다는 것이다.
                   그렇기 때문에 Critical Section 등의
                   동기화 문제에 고찰이 필요하다. */
                new Thread(new Runnable() {
                    /* Thread 의 run() 이 동작하는 시점이
                       굉장히 어려운 개념 중 하나인데
                       프로세스들이 Context Switching 을 하면서
                       CPU 를 획득하기 위한 경쟁을 한다고 했었다.
                       바로 Thread 의 run() 도 이 경쟁에 함께 참여한다.
                       그러므로 제어권이 넘어갈 때마다 동작하게 되며
                       다른 프로세스 보다 우선순위가 높아
                       보다 자주 실행할 수 있는 기회를 얻게 된다.
                       결론: 반응 속도가 좋다.
                       주의점: 동기화 문제를 제대로 해결하지 못한다면
                       오히려 안좋은 성능 혹은 잘못된 결과를 초래함 */
                    @Override
                    public void run() {
                        request(uriStr);
                    }
                }).start();
                /* start() 라는 것이 실제 run() 을 동작시키게 된다.
                   결국 익명 객체를 만들면서 Thread 가 시작된다. */
            }
        });
    }

    public void request(String uriStr) {
        // StringBuilder 는 말 그대로 문자열을 만드는 녀석이다.
        StringBuilder output = new StringBuilder();

        try {
            // 우리가 입력한 http://www.naver.com 을 URL 타입으로 만듬
            URL url = new URL(uriStr);

            /* 실제 HTTP 프로토콜(80)을 사용해서
               URL 주소와 TCP/IP Session 을 맺는다. */
            HttpURLConnection con =
                    (HttpURLConnection)url.openConnection();

            /*con == null 이란 것은 둘 중 하나
              1. 없는 주소를 입력한 것
              2. 진짜 서버가 죽은 상태
              3. 방화벽에 막힘(IP 제한, 포트 제한) */
            if(con != null) {
                // 일정 시간이 지나면 자동으로 세션 해제
                con.setConnectTimeout(10000);
                /* Web 에서 사용하는 방식이 두 가지 있다.
                   1. GET
                   2. POST */
                con.setRequestMethod("GET");
                /* 입력을 하겠다 */
                con.setDoInput(true);

                /* HTTP Response Code 를 받는다.
                   이 부분은 디버깅 시 매우 유용 */

                int resCode = con.getResponseCode();
                /* BufferedReader 와 InputStreamReader 가 보이는데
                   Stream 이란 것의 정체를 파악하는 것이 무엇보다 중요하다.
                   순서가 반드시 지켜져야하는 모든 것은 Stream 이다.
                   그럼 여기서 왜 Stream 을 사용했냐 ?
                   기본적으로 Network 동작은 Stream 동작이 아니기 때문
                   결국 순차성을 보장하기 위해 사용하는 것이
                   InputStreamReader 이며
                   데이터를 빠르게 처리하기 위한 녀석은
                   BufferedReader 에 해당한다.
                   즉, 빠르면서 순차성을 보장(정확하게)하기 위한 형식임
                   이런 자료구조는 원형 큐(환영 큐) 이다. */
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(con.getInputStream())
                );
                String line = null;

                /* while 루프는 HTML 코드를 뿌려주게 된다. */
                while (true) {
                    line = reader.readLine();
                    if(line == null) {
                        break;
                    }

                    output.append(line + "\n");
                }

                // stream 을 닫는다.
                reader.close();
                // 세션을 해제한다.
                con.disconnect();
            }
        } catch (Exception e) {
            println("Exception Occurance: " + e.toString());
        }
        println("Request Code: " + output.toString());
    }

    public void println(final String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                tv.append(data + "\n");
            }
        });
    }
}
/* 내일 작업 내용
   1. Notification 주석 달기
   2. DB update, delete, query 작성
   3. 2번 주석

   내일 모레
   Animation 기능 추가

   마지막
   1. 서비스 추가(특정 조건 만족시 자동 Notification)
 */
