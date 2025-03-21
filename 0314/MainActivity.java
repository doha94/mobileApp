package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textViewRandomNumber; // 난수 표시할 텍스트뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML에서 정의한 텍스트뷰를 연결
        textViewRandomNumber = findViewById(R.id.textViewRandomNumber);
    }

    // 버튼 클릭 시 실행되는 메서드
    public void generateRandomNumber(View view) {
        Random random = new Random();
        int randomNumber = random.nextInt(100); // 0~99 사이의 난수 생성
        textViewRandomNumber.setText("난수: " + randomNumber);
    }
}
