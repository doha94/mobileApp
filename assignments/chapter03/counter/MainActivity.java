package com.example.counter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button increase_button, decrease_button;
    private TextView counter_value;
    private int counter = 0; // 카운터 초기값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        counter_value = findViewById(R.id.counter_value);
        increase_button = findViewById(R.id.increase);
        decrease_button = findViewById(R.id.decrease);

        // 증가 버튼 이벤트 리스너
        increase_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                counter_value.setText(String.valueOf(counter));
            }
        });

        // 감소 버튼 이벤트 리스너
        decrease_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                counter_value.setText(String.valueOf(counter));
            }
        });
    }
}
