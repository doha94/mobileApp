package com.example.survey;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private HashMap<Integer, String> answers;

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button nextButton;
    private Button prevButton;
    private EditText inputAnswer;
    private LinearLayout radioLayout;
    private LinearLayout inputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기화
        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        inputAnswer = findViewById(R.id.inputAnswer);
        radioLayout = findViewById(R.id.radioLayout);
        inputLayout = findViewById(R.id.inputLayout);

        answers = new HashMap<>();
        setupQuestions();
        displayQuestion(currentQuestionIndex);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAnswer();

                if (currentQuestionIndex < questions.size() - 1) {
                    currentQuestionIndex++;
                    displayQuestion(currentQuestionIndex);
                } else {
                    // 설문 완료
                    showResults();
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAnswer();

                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    displayQuestion(currentQuestionIndex);
                }
            }
        });
    }

    private void setupQuestions() {
        questions = new ArrayList<>();

        // 객관식 질문 추가
        ArrayList<String> options1 = new ArrayList<>();
        options1.add("10대");
        options1.add("20대");
        options1.add("30대");
        options1.add("40대 이상");
        questions.add(new Question("1. 당신의 연령대는 어떻게 되나요?", options1, Question.TYPE_CHOICE));

        // 객관식 질문 추가
        ArrayList<String> options2 = new ArrayList<>();
        options2.add("매우 만족");
        options2.add("만족");
        options2.add("보통");
        options2.add("불만족");
        options2.add("매우 불만족");
        questions.add(new Question("2. 이 앱의 디자인에 얼마나 만족하시나요?", options2, Question.TYPE_CHOICE));

        // 주관식 질문 추가
        questions.add(new Question("3. 이 앱에서 개선되었으면 하는 점은 무엇인가요?", null, Question.TYPE_INPUT));

        // 객관식 질문 추가
        ArrayList<String> options3 = new ArrayList<>();
        options3.add("매일");
        options3.add("주 3-4회");
        options3.add("주 1-2회");
        options3.add("월 1-2회");
        options3.add("거의 사용하지 않음");
        questions.add(new Question("4. 얼마나 자주 앱을 사용하시나요?", options3, Question.TYPE_CHOICE));

        // 주관식 질문 추가
        questions.add(new Question("5. 추가적인 피드백이 있으시면 적어주세요.", null, Question.TYPE_INPUT));
    }

    private void displayQuestion(int index) {
        Question currentQuestion = questions.get(index);
        questionTextView.setText(currentQuestion.getQuestionText());

        // 이전 답변 불러오기
        String previousAnswer = answers.get(index);

        // 질문 유형에 따라 UI 조정
        if (currentQuestion.getType() == Question.TYPE_CHOICE) {
            radioLayout.setVisibility(View.VISIBLE);
            inputLayout.setVisibility(View.GONE);

            optionsRadioGroup.removeAllViews();
            ArrayList<String> options = currentQuestion.getOptions();

            for (int i = 0; i < options.size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(options.get(i));
                radioButton.setId(i);
                optionsRadioGroup.addView(radioButton);

                // 이전 답변 선택
                if (previousAnswer != null && previousAnswer.equals(options.get(i))) {
                    radioButton.setChecked(true);
                }
            }
        } else {
            radioLayout.setVisibility(View.GONE);
            inputLayout.setVisibility(View.VISIBLE);

            // 이전 답변 표시
            if (previousAnswer != null) {
                inputAnswer.setText(previousAnswer);
            } else {
                inputAnswer.setText("");
            }
        }

        // 이전/다음 버튼 상태 조정
        prevButton.setEnabled(index > 0);

        if (index == questions.size() - 1) {
            nextButton.setText("완료");
        } else {
            nextButton.setText("다음");
        }
    }

    private void saveAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);

        if (currentQuestion.getType() == Question.TYPE_CHOICE) {
            int selectedId = optionsRadioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton radioButton = findViewById(selectedId);
                answers.put(currentQuestionIndex, radioButton.getText().toString());
            }
        } else {
            String textAnswer = inputAnswer.getText().toString().trim();
            if (!textAnswer.isEmpty()) {
                answers.put(currentQuestionIndex, textAnswer);
            }
        }
    }

    private void showResults() {
        // 결과 보기 화면으로 이동
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);

        // 질문과 답변 전달
        ArrayList<String> questionsList = new ArrayList<>();
        ArrayList<String> answersList = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            questionsList.add(questions.get(i).getQuestionText());
            String answer = answers.get(i);
            answersList.add(answer != null ? answer : "응답 없음");
        }

        intent.putStringArrayListExtra("questions", questionsList);
        intent.putStringArrayListExtra("answers", answersList);
        startActivity(intent);
    }
}

// 질문 클래스
class Question {
    public static final int TYPE_CHOICE = 1;
    public static final int TYPE_INPUT = 2;

    private String questionText;
    private ArrayList<String> options;
    private int type;

    public Question(String questionText, ArrayList<String> options, int type) {
        this.questionText = questionText;
        this.options = options;
        this.type = type;
    }

    public String getQuestionText() {
        return questionText;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public int getType() {
        return type;
    }
}