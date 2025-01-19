package ru.myitschool.Tag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.util.DisplayMetrics;

public class MainActivity extends AppCompatActivity {

    private GameBoard gameBoard;
    private Button[][] buttons;
    private int size;
    private int steps = 0;
    private long startTime = 0;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;
    private TextView stepsTextView, timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepsTextView = findViewById(R.id.stepsTextView);
        timeTextView = findViewById(R.id.timeTextView);

        size = getIntent().getIntExtra("size", 4); // Получаем размер из выбора сложности
        gameBoard = new GameBoard(size);
        buttons = new Button[size][size];

        initializeUI();
        startGame();
    }

    private void initializeUI() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setRowCount(size);
        gridLayout.setColumnCount(size);

// Получаем ширину экрана
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

// Рассчитываем размер одной плитки с минимальными отступами
        int tileSize = screenWidth / size - 8; // 8dp отступа на плитки

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setOnClickListener(tileClickListener);

// Устанавливаем размер каждой кнопки
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = tileSize;
                params.height = tileSize;
                params.setMargins(2, 2, 2, 2); // Минимальные отступы между плитками

                buttons[i][j].setLayoutParams(params);
                gridLayout.addView(buttons[i][j]);
            }
        }

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Переход к экрану выбора сложности
                Intent intent = new Intent(MainActivity.this, DifficultyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void startGame() {
        steps = 0;
        stepsTextView.setText("Шаги: " + steps);

// Инициализируем таймер
        startTime = System.currentTimeMillis();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                timeTextView.setText("Время: " + elapsedTime + " сек");
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.postDelayed(timerRunnable, 1000);

        updateUI();
    }

    private void updateUI() {
        int[][] board = gameBoard.getBoard();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    buttons[i][j].setText("");
                } else {
                    buttons[i][j].setText(String.valueOf(board[i][j]));
                }
            }
        }

        if (gameBoard.isSolved()) {
            timerHandler.removeCallbacks(timerRunnable); // Останавливаем таймер
            saveResult(); // Сохранение результата
            Toast.makeText(this, "Поздравляем! Вы выиграли!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveResult() {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        String difficulty = size + "x" + size;
        String date = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault()).format(new Date());

        GameResult result = new GameResult(elapsedTime, steps, difficulty, date);
        ResultsStorage resultsStorage = new ResultsStorage(this);
        resultsStorage.saveResult(result);
    }

    private View.OnClickListener tileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if
                    (view == buttons[i][j]) {
                        if (gameBoard.moveTile(i, j)) {
                            steps++;
                            stepsTextView.setText("Шаги: " + steps);
                            updateUI();
                        }
                        return;
                    }
                }
            }
        }
    };
}