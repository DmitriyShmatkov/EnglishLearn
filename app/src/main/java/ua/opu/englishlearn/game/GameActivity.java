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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import ua.opu.englishlearn.R;
import ua.opu.englishlearn.game.fragments.GameResultFragment;
import ua.opu.englishlearn.game.fragments.QuestionFragment;
import ua.opu.englishlearn.main.MainActivity;
import ua.opu.englishlearn.room.dataclasses.FullGame;
import ua.opu.englishlearn.room.dataclasses.FullQuestion;
import ua.opu.englishlearn.room.entities.Game;
import ua.opu.englishlearn.room.entities.Word;
import ua.opu.englishlearn.room.repository.EnglishLearnRepository;

public class GameActivity extends AppCompatActivity {

    public static int questionsNumber = 4;

    private ActionBar actionBar;
    private ViewPager2 viewPager;
    EnglishLearnRepository repository;

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
        viewPager.setUserInputEnabled(false);
        GameViewPagerAdapter viewPagerAdapter = new GameViewPagerAdapter(this);

        FullGame fullGame = new FullGame();
        fullGame.setGame(new Game(questionsNumber, 0));
        List<FullQuestion> fullQuestions = new ArrayList<>();
        fullGame.setQuestions(fullQuestions);

        List<QuestionFragment> questionFragments = new ArrayList<>();
        List<Word> addedWords = repository.getAddedWords();

        Random random = new Random();

        List<Integer> correctAnswersIdx = new ArrayList<>();
        List<Word> correctAnswers = new ArrayList<>();
        Set<String> partsOfSpeech = new HashSet<>();
        while (correctAnswersIdx.size() < questionsNumber) {
            FullQuestion fullQuestion = new FullQuestion();

            // randomize unique added word for question
            int i;
            do {
                i = random.nextInt(addedWords.size());
            } while (correctAnswersIdx.contains(i));
            correctAnswersIdx.add(i);

            Word word = addedWords.get(i);
            correctAnswers.add(word);
            partsOfSpeech.add(word.getPartOfSpeech());

            fullQuestion.setCorrectAnswer(word);
            fullQuestion.setOptions(new ArrayList<>());
            fullGame.getQuestions().add(fullQuestion);
        }

        Map<String, List<Word>> wordsByPartsOfSpeech = new HashMap<>();
        for (String partOfSpeech : partsOfSpeech) {
            wordsByPartsOfSpeech.put(partOfSpeech, repository.getWordsByPartOfSpeech(partOfSpeech));
        }

        int i = 0;
        for (FullQuestion fullQuestion : fullGame.getQuestions()) {
            Word correctAnswer = fullQuestion.getCorrectAnswer();
            List<Word> allOptions = wordsByPartsOfSpeech.get(correctAnswer.getPartOfSpeech());
            List<Integer> optionsIdx = new ArrayList<>();

            while (fullQuestion.getOptions().size() < questionsNumber - 1) {
                int optionIdx;
                Word option;
                do {
                    optionIdx = random.nextInt(allOptions.size());
                    option = allOptions.get(optionIdx);
                } while (optionsIdx.contains(optionIdx) || option.equals(correctAnswer));
                optionsIdx.add(optionIdx);
                fullQuestion.getOptions().add(option);
            }

            QuestionFragment questionFragment = new QuestionFragment(i, viewPager, fullGame);

            // add to list
            questionFragments.add(questionFragment);
            i++;
        }

        viewPagerAdapter.addAllFragments(questionFragments);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class GameViewPagerAdapter extends FragmentStateAdapter {

        public List<Fragment> viewPagerFragments;

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