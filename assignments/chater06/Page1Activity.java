// Page1Activity.java
package com.example.navigationchallenge;

import android.os.Bundle;
import android.view.KeyEvent;
import androidx.appcompat.app.AppCompatActivity;

public class Page1Activity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
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
