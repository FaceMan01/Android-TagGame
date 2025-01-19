package ru.myitschool.Tag;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {
    private int[][] board;
    private int size;
    private int emptyX, emptyY;

    public GameBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        ArrayList<Integer> tiles = new ArrayList<>();

// Создаем решаемую последовательность
        for (int i = 0; i < size * size; i++) {
            tiles.add(i); // Последовательность от 0 до (size * size - 1)
        }

// Перемешиваем плитки четное количество раз
        do {
            Collections.shuffle(tiles);
        } while (!isSolvable(tiles));

// Заполняем игровую доску
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = tiles.get(i * size + j);
                if (board[i][j] == 0) {
                    emptyX = i; // Сохраняем позицию пустой плитки
                    emptyY = j;
                }
            }
        }
    }

    // Проверяем, является ли последовательность решаемой
    private boolean isSolvable(ArrayList<Integer> tiles) {
        int inversions = 0;

// Подсчет инверсий
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(i) != 0 && tiles.get(j) != 0 && tiles.get(i) > tiles.get(j)) {
                    inversions++;
                }
            }
        }

// Если размер поля нечетный
        if (size % 2 != 0) {
            return inversions % 2 == 0; // Решаемо, если количество инверсий четное
        } else {
// Если размер поля четный, учитываем позицию пустой плитки
            int emptyRowFromBottom = size - (tiles.indexOf(0) / size); // Номер строки пустой клетки снизу
            if (emptyRowFromBottom % 2 == 0) {
                return inversions % 2 != 0; // Решаемо, если количество инверсий нечетное
            } else {
                return inversions % 2 == 0; // Решаемо, если количество инверсий четное
            }
        }
    }

    public boolean moveTile(int x, int y) {
        if (Math.abs(emptyX - x) + Math.abs(emptyY - y) == 1) {
            board[emptyX][emptyY] = board[x][y];
            board[x][y] = 0;
            emptyX = x;
            emptyY = y;
            return true;
        }
        return false;
    }

    public boolean isSolved() {
        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    return board[i][j] == 0;
                }
                if (board[i][j] != count) {
                    return false;
                }
                count++;
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }
}