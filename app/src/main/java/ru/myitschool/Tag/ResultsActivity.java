package ru.myitschool.Tag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {

    private ResultsStorage resultsStorage;
    private ListView resultsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultsStorage = new ResultsStorage(this);
        resultsListView = findViewById(R.id.resultsListView);

// Обработчик для кнопки "Назад"
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Возвращаемся на экран выбора сложности
                Intent intent = new Intent(ResultsActivity.this, DifficultyActivity.class);
                startActivity(intent);
                finish();
            }
        });

        displayResults();
    }

    private void displayResults() {
        List<GameResult> results = resultsStorage.getAllResults();
        List<Map<String, String>> data = new ArrayList<>();

        for (GameResult result : results) {
            Map<String, String> item = new HashMap<>();
            item.put("time", "Время: " + result.getTime() + " сек");
            item.put("steps", "Шаги: " + result.getSteps());
            item.put("difficulty", "Сложность: " + result.getDifficulty());
            item.put("date", "Дата: " + result.getDate());
            data.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.result_item,
                new String[]{"time", "steps", "difficulty", "date"},
                new int[]{R.id.timeTextView, R.id.stepsTextView, R.id.difficultyTextView, R.id.dateTextView});

        resultsListView.setAdapter(adapter);
    }
}