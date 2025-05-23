package com.example.calendar2;



import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText titleInput, descriptionInput;
    private Button dateButton, addScheduleButton;
    private LinearLayout scheduleList;
    private Date selectedDate;
    private List<Schedule> schedules;
    private SimpleDateFormat dateFormat;
    private int scheduleIdCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 권한 요청
        requestNotificationPermission();

        // 초기화
        initViews();
        schedules = new ArrayList<>();
        dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN);
        selectedDate = new Date();

        setupClickListeners();
        updateDateButton();
    }

    private void initViews() {
        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        dateButton = findViewById(R.id.dateButton);
        addScheduleButton = findViewById(R.id.addScheduleButton);
        scheduleList = findViewById(R.id.scheduleList);
    }

    private void setupClickListeners() {
        dateButton.setOnClickListener(v -> showDatePicker());
        addScheduleButton.setOnClickListener(v -> addSchedule());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, month, dayOfMonth, 9, 0, 0); // 오전 9시로 설정
                    selectedDate = selectedCalendar.getTime();
                    updateDateButton();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void updateDateButton() {
        dateButton.setText("날짜: " + dateFormat.format(selectedDate));
    }

    private void addSchedule() {
        String title = titleInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        // 일정 생성
        Schedule schedule = new Schedule(scheduleIdCounter++, title, description, selectedDate);
        schedules.add(schedule);

        // 알람 설정
        setAlarm(schedule);

        // 화면 업데이트
        updateScheduleList();
        clearInputs();

        Toast.makeText(this, "일정이 추가되었습니다", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm(Schedule schedule) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("title", schedule.getTitle());
        intent.putExtra("description", schedule.getDescription());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                schedule.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // 정확한 시간에 알람 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    schedule.getDate().getTime(),
                    pendingIntent
            );
        } else {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    schedule.getDate().getTime(),
                    pendingIntent
            );
        }

    }

    private void updateScheduleList() {
        scheduleList.removeAllViews();

        for (Schedule schedule : schedules) {
            TextView scheduleView = new TextView(this);
            String scheduleText = String.format(
                    "📅 %s\n제목: %s\n내용: %s\n날짜: %s\n",
                    schedule.getTitle(),
                    schedule.getTitle(),
                    schedule.getDescription().isEmpty() ? "내용 없음" : schedule.getDescription(),
                    dateFormat.format(schedule.getDate())
            );

            scheduleView.setText(scheduleText);
            scheduleView.setPadding(16, 16, 16, 16);
            scheduleView.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
            scheduleView.setTextSize(14);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16);
            scheduleView.setLayoutParams(params);

            scheduleList.addView(scheduleView);
        }
    }

    private void clearInputs() {
        titleInput.setText("");
        descriptionInput.setText("");
        selectedDate = new Date();
        updateDateButton();
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }
}