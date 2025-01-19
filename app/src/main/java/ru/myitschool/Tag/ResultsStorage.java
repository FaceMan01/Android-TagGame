package ru.myitschool.Tag;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ResultsStorage {

    private static final String PREFS_NAME = "game_results_prefs";
    private static final String RESULTS_KEY = "results";

    private SharedPreferences sharedPreferences;

    public ResultsStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveResult(GameResult result) {
        List<GameResult> results = getAllResults();
        results.add(0, result);  // Добавляем результат в начало списка (новые выше)

        JSONArray jsonArray = new JSONArray();
        for (GameResult r : results) {
            jsonArray.put(r.toJson());
        }

        sharedPreferences.edit().putString(RESULTS_KEY, jsonArray.toString()).apply();
    }

    public List<GameResult> getAllResults() {
        String jsonString = sharedPreferences.getString(RESULTS_KEY, null);
        List<GameResult> results = new ArrayList<>();
        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    results.add(GameResult.fromJson(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
