package ua.opu.englishlearn.game.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import ua.opu.englishlearn.R;
import ua.opu.englishlearn.game.GameActivity;
import ua.opu.englishlearn.room.dataclasses.FullGame;
import ua.opu.englishlearn.room.dataclasses.FullQuestion;
import ua.opu.englishlearn.room.entities.Word;

public class QuestionFragment extends Fragment {

    private int index;
    private ViewPager2 viewPager;
    private FullGame fullGame;
    ActionBar actionBar;

    public QuestionFragment() {
    }

    public QuestionFragment(int index, ViewPager2 viewPager, FullGame fullGame) {
        this.index = index;
        this.viewPager = viewPager;
        this.fullGame = fullGame;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatActivity activity = Objects.requireNonNull((AppCompatActivity) getActivity());
        actionBar = Objects.requireNonNull(activity.getSupportActionBar());

        int questionsNumber = fullGame.getGame().getQuestionsNumber();
        TextView title = view.findViewById(R.id.questionFragmentTitle);
        title.setText(String.format(getText(R.string.question_fragment_title_text).toString(), index + 1, questionsNumber));

        Word correctAnswer = fullGame.getQuestions().get(index).getCorrectAnswer();
        List<Word> options = fullGame.getQuestions().get(index).getOptions();

        TextView questionWord = view.findViewById(R.id.questionWord);
        questionWord.setText(correctAnswer.getRussianTranslation());

        List<Button> optionButtons = Arrays.asList(
            view.findViewById(R.id.option1),
            view.findViewById(R.id.option2),
            view.findViewById(R.id.option3),
            view.findViewById(R.id.option4)
        );
        options.add(correctAnswer);
        Collections.shuffle(options);
        for (int i = 0; i < options.size(); i++) {
            Word word = options.get(i);
            optionButtons.get(i).setText(word.getEnglishTranslation());
            optionButtons.get(i).setOnClickListener(v -> next(word));
        }
    }

    private void next(Word userAnswer) {
        FullQuestion fullQuestion = fullGame.getQuestions().get(index);
        fullQuestion.setUserAnswer(userAnswer);
        if (userAnswer.equals(fullQuestion.getCorrectAnswer())) {
            fullGame.getGame().setCorrectAnswers(fullGame.getGame().getCorrectAnswers() + 1);
        }
        if (index == fullGame.getGame().getQuestionsNumber() - 1) {
            GameActivity.GameViewPagerAdapter adapter = (GameActivity.GameViewPagerAdapter) viewPager.getAdapter();
            GameResultFragment fragment = new GameResultFragment(fullGame);
            adapter.addFragment(fragment);
            adapter.notifyDataSetChanged();
            actionBar.setTitle(R.string.game_result_fragment_title);
        }
        viewPager.setCurrentItem(index + 1);
    }
}