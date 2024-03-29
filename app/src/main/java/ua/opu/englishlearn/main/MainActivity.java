package ua.opu.englishlearn.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ua.opu.englishlearn.R;
import ua.opu.englishlearn.game.GameActivity;
import ua.opu.englishlearn.main.fragments.StatisticsFragment;
import ua.opu.englishlearn.main.fragments.VocabularyFragment;
import ua.opu.englishlearn.room.database.EnglishLearnDatabase;
import ua.opu.englishlearn.room.dataclasses.FullGame;
import ua.opu.englishlearn.room.dataclasses.FullQuestion;
import ua.opu.englishlearn.room.entities.Word;
import ua.opu.englishlearn.room.repository.EnglishLearnRepository;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;
    List<ImageButton> bottomNavButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EnglishLearnRepository repository = EnglishLearnRepository.getInstance(this);
        List<FullQuestion> fullQuestions = repository.getAllQuestions();
        for (FullQuestion fullQuestion : fullQuestions) {
            System.out.println(fullQuestion.toString());
        }
        System.out.println("--------------------------------------------------");
        for (FullGame fullGame : repository.getAllGames()) {
            System.out.println(fullGame);
        }

        /*Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> EnglishLearnDatabase.getInstance(this).clearAllTables());*/

//        populateDatabase();

//        deleteDatabase("english_learn_db.db");

        Toolbar toolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);
        actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setTitle(R.string.vocabulary_fragment_title);

        ViewPager2 viewPager = findViewById(R.id.mainViewPager);
        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(this);
        viewPagerAdapter.addFragment(new VocabularyFragment(0, viewPagerAdapter));
        viewPagerAdapter.addFragment(new StatisticsFragment());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setUserInputEnabled(false);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for (ImageButton bottomButton : bottomNavButtons) {
                    if (bottomNavButtons.indexOf(bottomButton) == position) {
                        bottomButton.setColorFilter(Color.WHITE);
                    } else {
                        bottomButton.setColorFilter(Color.BLACK);
                    }
                }
            }
        });

        ImageButton vocabularyBottomButton = findViewById(R.id.vocabularyButton);
        ImageButton statisticsBottomButton = findViewById(R.id.statsButton);
        bottomNavButtons = new ArrayList<>();
        bottomNavButtons.add(vocabularyBottomButton);
        bottomNavButtons.add(statisticsBottomButton);

        vocabularyBottomButton.setOnClickListener(v -> {
            actionBar.setTitle(R.string.vocabulary_fragment_title);
            viewPager.setCurrentItem(0, false);
        });
        statisticsBottomButton.setOnClickListener(v -> {
            actionBar.setTitle(R.string.statistics_fragment_title);
            viewPager.setCurrentItem(1, false);
        });

        findViewById(R.id.playButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });

    }

    public class MainViewPagerAdapter extends FragmentStateAdapter {

        private List<Fragment> viewPagerFragments;

        public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            viewPagerFragments = new ArrayList<>();
        }

        public void addFragment(Fragment fragment) {
            viewPagerFragments.add(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return viewPagerFragments.get(position);
        }

        @Override
        public int getItemCount() {
            return viewPagerFragments.size();
        }

        @Override
        public long getItemId(int position) {
            return viewPagerFragments.get(position).hashCode();
        }

        @Override
        public boolean containsItem(long itemId) {
            for (Fragment fragment : viewPagerFragments) {
                if (fragment.hashCode() == itemId) {
                    return true;
                }
            }
            return false;
        }

        public void replace(int position, Fragment fragment) {
            viewPagerFragments.set(position, fragment);
            notifyItemChanged(position);
        }
    }

    private void populateDatabase() {
        EnglishLearnRepository repository = EnglishLearnRepository.getInstance(this);

        String allWordsString = "";
        try {
            InputStream inputStream = getAssets().open("dictionary.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            allWordsString = new String(buffer);
            Toast.makeText(this, "File read", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found!", Toast.LENGTH_SHORT).show();
        }

        String[] allWordsArray = allWordsString.split("\n");
        List<Word> words = new ArrayList<>();
        String trueString = allWordsArray[0].split(";")[3];
        for (String wordString : allWordsArray) {
            String[] parts = wordString.split(";");
            Word word = new Word(parts[0], parts[1], parts[2], false);
            if (parts[3].equals(trueString)) {
                word.setAdded(true);
                System.out.println(word);
            }
            words.add(word);
        }
        words.remove(0);

        repository.insertAllWords(words);
    }
}