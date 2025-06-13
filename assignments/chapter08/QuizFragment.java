package com.example.quizapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class QuizFragment extends Fragment {
    
    private TextView questionText;
    private RadioGroup answerGroup;
    private RadioButton option1, option2, option3, option4;
    private Button nextButton;
    
    private int currentQuestionIndex = 0;
    private int score = 0;
    
    // 퀴즈 문제 데이터
    private String[] questions = {
        "안드로이드 프로그래밍에 가장 많이 사용되는 언어는?",
        "안드로이드의 4가지 컴포넌트가 아닌것은??",
        "안드로이드 앱의 설정 파일은 무엇인가?"
    };
    
    private String[][] options = {
        {"C언어", "python", "C++언어", "자바언어"},
        {"서비스", "현대차 자동차", "액티비티", "프로세스"},
        {"AndroidManifest.xml", "build.gradle", "strings.xml", "styles.xml"}
    };
    
    private int[] correctAnswers = {3, 2, 0}; // 정답 인덱스 (0부터 시작)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        
        initViews(view);
        loadQuestion();
        
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
        
        return view;
    }
    
    private void initViews(View view) {
        questionText = view.findViewById(R.id.tv_question);
        answerGroup = view.findViewById(R.id.rg_answers);
        option1 = view.findViewById(R.id.rb_option1);
        option2 = view.findViewById(R.id.rb_option2);
        option3 = view.findViewById(R.id.rb_option3);
        option4 = view.findViewById(R.id.rb_option4);
        nextButton = view.findViewById(R.id.btn_next);
    }
    
    private void loadQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionText.setText(questions[currentQuestionIndex]);
            option1.setText(options[currentQuestionIndex][0]);
            option2.setText(options[currentQuestionIndex][1]);
            option3.setText(options[currentQuestionIndex][2]);
            option4.setText(options[currentQuestionIndex][3]);
            
            // 라디오 버튼 선택 초기화
            answerGroup.clearCheck();
            
            // 마지막 문제일 때 버튼 텍스트 변경
            if (currentQuestionIndex == questions.length - 1) {
                nextButton.setText("결과 보기");
            }
        }
    }
    
    private void checkAnswer() {
        int selectedId = answerGroup.getCheckedRadioButtonId();
        
        if (selectedId == -1) {
            Toast.makeText(getContext(), "답을 선택해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // 선택된 답 확인
        int selectedAnswer = -1;
        if (selectedId == R.id.rb_option1) selectedAnswer = 0;
        else if (selectedId == R.id.rb_option2) selectedAnswer = 1;
        else if (selectedId == R.id.rb_option3) selectedAnswer = 2;
        else if (selectedId == R.id.rb_option4) selectedAnswer = 3;
        
        // 정답 확인
        if (selectedAnswer == correctAnswers[currentQuestionIndex]) {
            score++;
        }
        
        currentQuestionIndex++;
        
        if (currentQuestionIndex < questions.length) {
            loadQuestion();
        } else {
            // 퀴즈 완료 - 결과 화면으로 이동
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("score", score);
                bundle.putInt("total", questions.length);
                
                ResultFragment resultFragment = new ResultFragment();
                resultFragment.setArguments(bundle);
                
                mainActivity.loadFragment(resultFragment);
            }
        }
    }
}
