package com.example.survey;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        LinearLayout resultsContainer = findViewById(R.id.resultsContainer);
        Button finishButton = findViewById(R.id.finishButton);

        // Intent에서 질문과 답변 가져오기
        ArrayList<String> questions = getIntent().getStringArrayListExtra("questions");
        ArrayList<String> answers = getIntent().getStringArrayListExtra("answers");

        if (questions != null && answers != null) {
            for (int i = 0; i < questions.size(); i++) {
                // 질문과 답변 표시
                TextView questionView = new TextView(this);
                questionView.setText(questions.get(i));
                questionView.setTextSize(16);
                questionView.setPadding(0, 16, 0, 4);

                TextView answerView = new TextView(this);
                answerView.setText("답변: " + answers.get(i));
                answerView.setTextSize(14);
                answerView.setPadding(20, 0, 0, 16);

                // 구분선 추가
                View divider = new View(this);
                divider.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(0, 8, 0, 8);
                divider.setLayoutParams(params);

                resultsContainer.addView(questionView);
                resultsContainer.addView(answerView);
                if (i < questions.size() - 1) {
                    resultsContainer.addView(divider);
                }
            }
        }

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 설문 완료 후 앱 종료
                finish();
            }
        });
    }
}