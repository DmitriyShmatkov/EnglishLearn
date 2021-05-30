package ua.opu.englishlearn.game.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import ua.opu.englishlearn.room.dataclasses.QuestionWithOptionsAndAnswer;
import ua.opu.englishlearn.room.entities.Question;
import ua.opu.englishlearn.room.entities.Word;

public class QuestionFragment extends Fragment {

    private int index;
    private ViewPager2 viewPager;

    private Word correctAnswer;
    private List<Word> options;

    public QuestionFragment() {
    }

    public QuestionFragment(int index, ViewPager2 viewPager, Word correctAnswer, List<Word> options) {
        this.index = index;
        this.viewPager = viewPager;
        this.correctAnswer = correctAnswer;
        this.options = options;
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
        ActionBar actionBar = Objects.requireNonNull(activity.getSupportActionBar());

        int questionsNumber = 5;
        TextView title = view.findViewById(R.id.questionFragmentTitle);
        title.setText(String.format(getText(R.string.question_fragment_title_text).toString(), (index + 1), questionsNumber));

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
            optionButtons.get(i).setText(options.get(i).getEnglishTranslation());
            optionButtons.get(i).setOnClickListener(v -> nextQuestion());
        }
    }

    private void nextQuestion() {
        viewPager.setCurrentItem(index + 1);
    }
}