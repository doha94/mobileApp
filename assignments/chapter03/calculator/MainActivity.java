package com.example.assignment_first;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button plus_button, minus_button, multiply_button, divide_button;
    private EditText first_number, second_number;

    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        plus_button = (Button) findViewById(R.id.plus_button);
        minus_button = (Button) findViewById(R.id.minus_button);
        multiply_button = (Button) findViewById(R.id.multiply_button);
        divide_button = (Button) findViewById(R.id.divide_button);

        first_number = (EditText) findViewById(R.id.first_number);
        second_number = (EditText) findViewById(R.id.second_number);

        text = (TextView) findViewById(R.id.text);

        plus_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int result = Integer.parseInt(first_number.getText().toString()) +Integer.parseInt(second_number.getText().toString());

                text.setText(String.valueOf(result));
            }
        });

        minus_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int result = Integer.parseInt(first_number.getText().toString()) - Integer.parseInt(second_number.getText().toString());
                text.setText(String.valueOf(result));
            }
        });
        multiply_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int result = Integer.parseInt(first_number.getText().toString()) * Integer.parseInt(second_number.getText().toString());
                text.setText(String.valueOf(result));
            }
        });
        divide_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                int result = Integer.parseInt(first_number.getText().toString()) / Integer.parseInt(second_number.getText().toString());
                text.setText(String.valueOf(result));
            }
        });
    }
}
