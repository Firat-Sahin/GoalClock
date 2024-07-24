package com.firatsahin.goalclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity2 extends AppCompatActivity {

    private EditText addictionEditText;
    private Button addButton;
    private RecyclerView recyclerView;
    private AddictionAdapter adapter;
    private List<String> addictionList;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "GoalClockPrefs";
    private static final String ADDICTION_KEY = "AddictionList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        addictionEditText = findViewById(R.id.addictionEditText);
        addButton = findViewById(R.id.addButton);
        recyclerView = findViewById(R.id.recyclerView);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        addictionList = new ArrayList<>(loadAddictions());

        adapter = new AddictionAdapter(addictionList, this::openMainActivity3);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> addAddiction());
    }

    private void addAddiction() {
        String addiction = addictionEditText.getText().toString().trim();
        if (!addiction.isEmpty()) {
            addictionList.add(addiction);
            adapter.notifyItemInserted(addictionList.size() - 1);
            addictionEditText.setText("");
            saveAddictions();
        }
    }

    private void openMainActivity3(String addiction) {
        Intent intent = new Intent(this, MainActivity3.class);
        intent.putExtra("addiction", addiction);
        startActivity(intent);
    }

    private void saveAddictions() {
        Set<String> set = new HashSet<>(addictionList);
        sharedPreferences.edit().putStringSet(ADDICTION_KEY, set).apply();
    }

    private Set<String> loadAddictions() {
        return sharedPreferences.getStringSet(ADDICTION_KEY, new HashSet<>());
    }
}
