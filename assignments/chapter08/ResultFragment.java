package com.example.quizapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {
    
    private TextView scoreText;
    private TextView messageText;
    private Button restartButton;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        
        initViews(view);
        displayResult();
        
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다시 시작 - StartFragment로 이동
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.loadFragment(new StartFragment());
                }
            }
        });
        
        return view;
    }
    
    private void initViews(View view) {
        scoreText = view.findViewById(R.id.tv_score);
        messageText = view.findViewById(R.id.tv_message);
        restartButton = view.findViewById(R.id.btn_restart);
    }
    
    private void displayResult() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int score = bundle.getInt("score", 0);
            int total = bundle.getInt("total", 0);
            
            scoreText.setText(score + " / " + total);
            
            String message;
            if (score == total) {
                message = "완벽합니다! 🎉";
            } else if (score >= total * 0.7) {
                message = "잘했습니다! 👏";
            } else if (score >= total * 0.5) {
                message = "괜찮네요! 👍";
            } else {
                message = "더 공부하세요! 📚";
            }
            
            messageText.setText(message);
        }
    }
}
