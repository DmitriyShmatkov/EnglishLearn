package ua.opu.englishlearn.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ua.opu.englishlearn.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setTitle(R.string.vocabulary_fragment_title);

        List<Button> bottomNavButtons = new ArrayList<>();
        bottomNavButtons.add(findViewById(R.id.vocabularyButton));
        bottomNavButtons.add(findViewById(R.id.statsButton));


    }
}