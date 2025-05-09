// MainActivity.java
package com.example.painter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DrawingView drawingView;
    private ImageButton currPaint;
    private Button brushBtn, eraseBtn, newBtn;
    private SeekBar brushSize;

    private float previous_progess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawing_view);

        // 색상 선택 버튼 레이아웃 가져오기
        LinearLayout paintLayout = findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        // 브러시 크기 조절 시크바 설정
        brushSize = findViewById(R.id.brush_size_bar);
        brushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float brushWidth = (float) progress;
                drawingView.setBrushSize(brushWidth);
                previous_progess = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 사용하지 않음
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 사용하지 않음
            }
        });

        // 브러시 버튼 설정
        brushBtn = findViewById(R.id.brush_btn);
        brushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.setErase(false);
                drawingView.setBrushSize(previous_progess);
            }
        });

        // 지우개 버튼 설정
        eraseBtn = findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.setErase(true);
                drawingView.setBrushSize(100);

            }
        });

        // 새 그림 버튼 설정
        newBtn = findViewById(R.id.new_btn);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.startNew();
            }
        });
    }

    // 색상 선택 메소드
    public void paintClicked(View view) {
        // 사용자가 이미 선택된 색상 버튼을 클릭한 경우
        if (view != currPaint) {
            // 새 색상 버튼 가져오기
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();

            // 새 색상 설정
            drawingView.setColor(color);
            drawingView.setErase(false);

            // UI 업데이트
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint = imgView;
        }
    }
}