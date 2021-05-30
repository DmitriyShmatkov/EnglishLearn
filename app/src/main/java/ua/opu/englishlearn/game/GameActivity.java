package ua.opu.englishlearn.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import ua.opu.englishlearn.R;
import ua.opu.englishlearn.game.fragments.QuestionFragment;
import ua.opu.englishlearn.room.dataclasses.QuestionWithOptionsAndAnswer;
import ua.opu.englishlearn.room.entities.Question;
import ua.opu.englishlearn.room.entities.Word;
import ua.opu.englishlearn.room.repository.EnglishLearnRepository;

public class GameActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ViewPager2 viewPager;
    EnglishLearnRepository repository;
    private int questionsNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Toolbar toolbar = findViewById(R.id.gameActivityToolbar);
        setSupportActionBar(toolbar);
        actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setTitle(R.string.game_activity_title);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        repository = EnglishLearnRepository.getInstance(this);

        viewPager = findViewById(R.id.gameActivityViewPager);
//        viewPager.setUserInputEnabled(false);
        GameViewPagerAdapter viewPagerAdapter = new GameViewPagerAdapter(this);
        questionsNumber = 5;
        List<QuestionFragment> questionFragments = new ArrayList<>();
        List<Word> addedWords = repository.getAddedWords();
        List<Word> allWords = repository.getAllWords();

        Random random = new Random();
        List<Word> correctAnswers = new ArrayList<>();
        List<List<Word>> optionsList = new ArrayList<>();
        while (correctAnswers.size() < questionsNumber) {
            // randomize unique added word for question
            Word correctAnswer;
            do {
                correctAnswer = addedWords.get(random.nextInt(addedWords.size()));
            } while (correctAnswers.contains(correctAnswer));
            correctAnswers.add(correctAnswer);

            // randomize options for question
            List<Word> options = new ArrayList<>();
            while (options.size() < 3) {
                Word option;
                do {
                    option = allWords.get(random.nextInt(allWords.size()));
                } while (options.contains(option) || option.equals(correctAnswer));
                options.add(option);
            }
            optionsList.add(options);

            // create question fragment
            QuestionFragment questionFragment = new QuestionFragment(
                    correctAnswers.size() - 1,
                    viewPager,
                    correctAnswer,
                    options
            );
            // add to list
            questionFragments.add(questionFragment);
        }

        viewPagerAdapter.addAllFragments(questionFragments);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    class GameViewPagerAdapter extends FragmentStateAdapter {

        private List<Fragment> viewPagerFragments;

        public GameViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            viewPagerFragments = new ArrayList<>();
        }

        public void addFragment(Fragment fragment) {
            viewPagerFragments.add(fragment);
        }

        public void addAllFragments(List<? extends Fragment> fragments) {
            viewPagerFragments.addAll(fragments);
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
    }
}