package ru.myitschool.Tag;

import org.json.JSONException;
import org.json.JSONObject;

public class GameResult {
    private long time;
    private int steps;
    private String difficulty;
    private String date;

    public GameResult(long time, int steps, String difficulty, String date) {
        this.time = time;
        this.steps = steps;
        this.difficulty = difficulty;
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public int getSteps() {
        return steps;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getDate() {
        return date;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("time", time);
            jsonObject.put("steps", steps);
            jsonObject.put("difficulty", difficulty);
            jsonObject.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static GameResult fromJson(JSONObject jsonObject) {
        try {
            long time = jsonObject.getLong("time");
            int steps = jsonObject.getInt("steps");
            String difficulty = jsonObject.getString("difficulty");
            String date = jsonObject.getString("date");
            return new GameResult(time, steps, difficulty, date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}