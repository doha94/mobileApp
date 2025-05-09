package com.example.assignment_todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_TODO = "todos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK = "task";
    private static final String COLUMN_COMPLETED = "completed";
    private static final String COLUMN_DATETIME = "datetime";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TASK + " TEXT, "
                + COLUMN_COMPLETED + " INTEGER, "
                + COLUMN_DATETIME + " TEXT"
                + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    // 할 일 추가
    public long insertTodo(TodoItem todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, todo.getTask());
        values.put(COLUMN_COMPLETED, todo.isCompleted() ? 1 : 0);
        values.put(COLUMN_DATETIME, todo.getDateTime()); // 날짜 저장
        long id = db.insert(TABLE_TODO, null, values);
        db.close();
        return id;
    }

    // 할 일 수정
    public int updateTodo(TodoItem todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, todo.getTask());
        values.put(COLUMN_COMPLETED, todo.isCompleted() ? 1 : 0);
        int result = db.update(TABLE_TODO, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(todo.getId())});
        db.close();
        return result;
    }

    // 할 일 삭제
    public void deleteTodo(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // 모든 할 일 가져오기
    public List<TodoItem> getAllTodos() {
        List<TodoItem> todoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TODO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TodoItem todo = new TodoItem();
                todo.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                todo.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_TASK)));
                todo.setCompleted(cursor.getInt(cursor.getColumnIndex(COLUMN_COMPLETED)) == 1);
                todo.setDateTime(cursor.getString(cursor.getColumnIndex(COLUMN_DATETIME))); // 날짜 불러오기
                todoList.add(todo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return todoList;
    }
}
