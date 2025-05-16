package com.example.todolist;




public class MainActivity extends AppCompatActivity {

    EditText inputTask;
    Button addButton;
    TextView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTask = findViewById(R.id.inputTask);
        addButton = findViewById(R.id.addButton);
        taskList = findViewById(R.id.taskList);

        addButton.setOnClickListener(v -> {
            String title = inputTask.getText().toString();
            new Thread(() -> {
                TaskDAO.insertTask(title);
                List<String> tasks = TaskDAO.getTasks();
                runOnUiThread(() -> taskList.setText(TextUtils.join("\n", tasks)));
            }).start();
        });
    }
}
