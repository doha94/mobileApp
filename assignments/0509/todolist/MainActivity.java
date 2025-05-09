package com.example.assignment_todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView todoListView;
    private TodoAdapter adapter;
    private List<TodoItem> todoItems;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터베이스 헬퍼 초기화
        dbHelper = new DatabaseHelper(this);

        // 할 일 목록 초기화
        todoItems = new ArrayList<>();

        // 저장된 할 일 목록 불러오기
        todoItems = dbHelper.getAllTodos();

        // 리스트뷰 및 어댑터 설정
        todoListView = findViewById(R.id.todo_list);
        adapter = new TodoAdapter(this, todoItems);
        todoListView.setAdapter(adapter);

        // 할 일 추가 버튼 설정
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTodoDialog();
            }
        });

        // 할 일 삭제 리스너 설정
        adapter.setOnDeleteClickListener(new TodoAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                TodoItem item = todoItems.get(position);
                dbHelper.deleteTodo(item.getId());
                todoItems.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "할 일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 완료 상태 변경 리스너 설정
        adapter.setOnCompleteClickListener(new TodoAdapter.OnCompleteClickListener() {
            @Override
            public void onCompleteClick(int position, boolean isComplete) {
                TodoItem item = todoItems.get(position);
                item.setCompleted(isComplete);
                dbHelper.updateTodo(item);
                adapter.notifyDataSetChanged();
            }
        });
    }

    // 할 일 추가 다이얼로그 표시
    private void showAddTodoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("할 일 추가");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String task = input.getText().toString().trim();
                if (!task.isEmpty()) {
                    // 새 할 일 추가
                    TodoItem newTodo = new TodoItem(task, false);
                    long id = dbHelper.insertTodo(newTodo);
                    newTodo.setId(id);
                    todoItems.add(newTodo);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "할 일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}