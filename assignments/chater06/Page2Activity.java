// Page2Activity.java
package com.example.navigationchallenge;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Page2Activity extends AppCompatActivity {
    
    private SeekBar seekBar1, seekBar2;
    private TextView value1, value2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        
        // SeekBar 초기화
        seekBar1 = findViewById(R.id.seekbar1);
        seekBar2 = findViewById(R.id.seekbar2);
        
        // 값 표시 TextView 초기화
        value1 = findViewById(R.id.text_value1);
        value2 = findViewById(R.id.text_value2);
        
        // 첫 번째 SeekBar 리스너
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value1.setText("값 1: " + progress);
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // 두 번째 SeekBar 리스너
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value2.setText("값 2: " + progress);
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // 초기값 설정
        seekBar1.setProgress(50);
        seekBar2.setProgress(50);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BACK 키를 눌렀을 때 메인 페이지로 돌아가기
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
                volumeValue.setText(progress + "%");
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // 밝기 SeekBar 리스너
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightnessValue.setText(progress + "%");
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // 난이도 SeekBar 리스너
        difficultySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String[] difficulties = {"쉬움", "보통", "어려움", "매우 어려움", "극한"};
                int index = Math.min(progress / 20, difficulties.length - 1);
                difficultyValue.setText(difficulties[index]);
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // 초기값 설정
        volumeSeekBar.setProgress(50);
        brightnessSeekBar.setProgress(75);
        difficultySeekBar.setProgress(40);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // BACK 키를 눌렀을 때 메인 페이지로 돌아가기
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish(); // 현재 액티비티를 종료하여 메인 페이지로 돌아감
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
