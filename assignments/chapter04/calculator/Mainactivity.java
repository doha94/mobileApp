package com.example.calculator1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button plus,minus,multiply,divide;
    private EditText number1,number2;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        multiply = (Button) findViewById(R.id.multiply);
        divide = (Button) findViewById(R.id.divide);
        number1 = (EditText) findViewById(R.id.number1);
        number2 = (EditText) findViewById(R.id.number2);
        result = (TextView) findViewById(R.id.result);

    }
    public void onClickButton(View view){

        if (view.equals(plus)){
            result.setText(String.valueOf(Integer.parseInt(number1.getText().toString()) + Integer.parseInt(number2.getText().toString())));
        }else if (view.equals(minus)){
            result.setText(String.valueOf(Integer.parseInt(number1.getText().toString()) - Integer.parseInt(number2.getText().toString())));
        } else if (view.equals(multiply)) {
            result.setText(String.valueOf(Integer.parseInt(number1.getText().toString()) * Integer.parseInt(number2.getText().toString())));
        }
        else if (view.equals(divide)){
            if (Integer.parseInt(number2.getText().toString()) == 0){
                result.setText("0으로 나눌 수 없습니다.");
            }
            else {
                result.setText(String.valueOf(Integer.parseInt(number1.getText().toString()) / Integer.parseInt(number2.getText().toString())));
            }
        }
    }

}
