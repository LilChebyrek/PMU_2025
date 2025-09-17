package com.example.jukes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

class PlayerInfo {
    public String fio;
    public boolean sex;
    public String course;
    public int difficulty;
    public String birthday;
    public ImageView zodiac;

    public PlayerInfo(String _fio, boolean _sex, String _course, int _difficulty, String _birthday, ImageView _zodiac) {
        this.fio = _fio;
        this.sex = _sex;
        this.course = _course;
        this.difficulty = _difficulty;
        this.birthday = _birthday;
        this.zodiac = _zodiac;
    }
}


public class MainActivity extends AppCompatActivity {
    String[] courses = {"1 курс", "2 курс", "3 курс", "4 курс"};
    PlayerInfo playerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                long millis = new GregorianCalendar(year, month, dayOfMonth).getTimeInMillis();
                calendarView.setDate(millis, true, true);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void savePlayerInfo(View view) {
        EditText editText = findViewById(R.id.editTextText);
        RadioButton radioButtonM = findViewById(R.id.radioButtonM);
        RadioButton radioButtonF = findViewById(R.id.radioButtonF);
        Spinner spinner = findViewById(R.id.spinner);
        SeekBar seekBar = findViewById(R.id.seekBar);
        CalendarView calendarView = findViewById(R.id.calendarView);
        ImageView imageView = findViewById(R.id.imageView);

        if (editText.toString() == "" || !(radioButtonM.isChecked() || radioButtonF.isChecked())) {
            Toast toast = Toast.makeText(this, "Заполните все пункты!", Toast.LENGTH_LONG);
            toast.show();

        }
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calendarView.getDate());

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            long a = calendarView.getDate();
            int month = calendar.get(Calendar.MONTH);
            //ImageView z = new ImageView(this);
            if (day > 10 && day < 25) {
                imageView.setImageResource(R.drawable.kozerog);


            }
            else {
                imageView.setImageResource(R.drawable.hzkakoyznak);
            }

            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dateString = date.format(new Date(calendarView.getDate()));

            playerInfo = new PlayerInfo(editText.toString(), !radioButtonM.isChecked(), spinner.getSelectedItem().toString(), seekBar.getProgress(), dateString, imageView);

            TextView textView = findViewById(R.id.playerInfo);
            textView.setText("Добавлен новый игрок " + editText.getText().toString() + " " +  (radioButtonM.isChecked() ? "мужского пола, " : "женского пола, ") + spinner.getSelectedItem().toString() + "а, " + dateString + " даты рождения");
        }

    }
}