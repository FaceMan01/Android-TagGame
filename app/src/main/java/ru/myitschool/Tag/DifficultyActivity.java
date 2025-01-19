package ru.myitschool.Tag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DifficultyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        findViewById(R.id.resultsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifficultyActivity.this, ResultsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button3x3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(3);
            }
        });

        findViewById(R.id.button4x4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(4);
            }
        });

        findViewById(R.id.button5x5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(5);
            }
        });
    }

    private void startGame(int size) {
        Intent intent = new Intent(DifficultyActivity.this, MainActivity.class);
        intent.putExtra("size", size);
        startActivity(intent);
        finish();
    }
}
