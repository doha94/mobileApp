package com.example.assignment_todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends BaseAdapter {
    private Context context;
    private List<TodoItem> todoItems;
    private OnDeleteClickListener deleteListener;
    private OnCompleteClickListener completeListener;

    public TodoAdapter(Context context, List<TodoItem> todoItems) {
        this.context = context;
        this.todoItems = todoItems;
    }

    @Override
    public int getCount() {
        return todoItems.size();
    }

    @Override
    public Object getItem(int position) {
        return todoItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
            holder = new ViewHolder();
            holder.taskText = convertView.findViewById(R.id.task_text);
            holder.completeCheckBox = convertView.findViewById(R.id.complete_checkbox);
            holder.deleteButton = convertView.findViewById(R.id.delete_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TodoItem currentItem = todoItems.get(position);

        holder.taskText.setText(currentItem.getTask());

        // 완료 여부에 따라 텍스트 스타일 변경
        if (currentItem.isCompleted()) {
            holder.taskText.setAlpha(0.5f);
        } else {
            holder.taskText.setAlpha(1.0f);
        }

        // 체크박스 상태 설정
        holder.completeCheckBox.setOnCheckedChangeListener(null);
        holder.completeCheckBox.setChecked(currentItem.isCompleted());
        holder.completeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (completeListener != null) {
                    completeListener.onCompleteClick(position, isChecked);
                }
            }
        });

        // 삭제 버튼 클릭 리스너
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener != null) {
                    deleteListener.onDeleteClick(position);
                }
            }
        });

        return convertView;
    }

    // 뷰홀더 클래스
    static class ViewHolder {
        TextView taskText;
        CheckBox completeCheckBox;
        ImageButton deleteButton;
    }

    // 삭제 클릭 인터페이스
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    // 완료 클릭 인터페이스
    public interface OnCompleteClickListener {
        void onCompleteClick(int position, boolean isComplete);
    }

    // 리스너 설정 메소드
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }

    public void setOnCompleteClickListener(OnCompleteClickListener listener) {
        this.completeListener = listener;
    }
}
