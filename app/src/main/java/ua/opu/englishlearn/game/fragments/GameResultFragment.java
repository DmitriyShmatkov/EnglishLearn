package ua.opu.englishlearn.game.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ua.opu.englishlearn.R;
import ua.opu.englishlearn.room.dataclasses.FullGame;
import ua.opu.englishlearn.room.dataclasses.FullQuestion;

public class GameResultFragment extends Fragment {

    private FullGame fullGame;
    private List<FullQuestion> fullQuestions;

    private RecyclerView recyclerView;

    public GameResultFragment(FullGame fullGame) {
        this.fullGame = fullGame;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullQuestions = fullGame.getQuestions();

        int questionsNumber = fullGame.getGame().getQuestionsNumber();
        int correctNumber = fullGame.getGame().getCorrectAnswers();
        double pointsRelation = (double) correctNumber / questionsNumber;

        TextView points = view.findViewById(R.id.game_result_points);
        points.setText(String.format(getText(R.string.game_result_points_text).toString(),
                correctNumber, questionsNumber));
        int colorId = R.color.backgroundTextColor;
        if (pointsRelation >= 0 && pointsRelation < 0.4) {
            colorId = R.color.bad;
        } else if (pointsRelation >= 0.4 && pointsRelation < 0.75) {
            colorId = R.color.medium;
        } else if (pointsRelation >= 0.75 && pointsRelation <= 1) {
            colorId = R.color.good;
        } else {
            Toast.makeText(requireContext(), "Points out of bounds", Toast.LENGTH_LONG).show();
        }
        points.setTextColor(ContextCompat.getColor(requireContext(), colorId));

        view.findViewById(R.id.game_result_menu_button).setOnClickListener(v -> requireActivity().onBackPressed());

        List<FullQuestion> mistakes = new ArrayList<>();
        List<Integer> mistakesNumbers = new ArrayList<>();
        for (int i = 0; i < fullQuestions.size(); i++) {
            FullQuestion fullQuestion = fullQuestions.get(i);
            if (!fullQuestion.getUserAnswer().equals(fullQuestion.getCorrectAnswer())) {
                mistakes.add(fullQuestion);
                mistakesNumbers.add(i + 1);
            }
        }
        GameMistakesAdapter adapter = new GameMistakesAdapter(mistakes, mistakesNumbers);

        recyclerView = view.findViewById(R.id.game_result_mistakes_list);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2,
                GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public class GameMistakesAdapter extends RecyclerView.Adapter<GameMistakesAdapter.MistakeCardViewHolder> {

        private List<FullQuestion> fullQuestions;
        private List<Integer> questionNumbers;

        public GameMistakesAdapter(List<FullQuestion> fullQuestions, List<Integer> questionNumbers) {
            this.fullQuestions = fullQuestions;
            this.questionNumbers = questionNumbers;
        }

        @NonNull
        @Override
        public MistakeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MistakeCardViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.game_result_mistake_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MistakeCardViewHolder holder, int position) {
            FullQuestion fullQuestion = fullQuestions.get(position);

            holder.header.setText(String.format(
                    getText(R.string.game_result_mistake_header).toString(),
                    questionNumbers.get(position)));
            holder.correct.setText(fullQuestion.getCorrectAnswer().getEnglishTranslation());
            holder.incorrect.setText(fullQuestion.getUserAnswer().getEnglishTranslation());
        }

        @Override
        public int getItemCount() {
            return fullQuestions.size();
        }


        class MistakeCardViewHolder extends RecyclerView.ViewHolder {

            private TextView header;
            private TextView correct;
            private TextView incorrect;

            public MistakeCardViewHolder(@NonNull View itemView) {
                super(itemView);

                header = itemView.findViewById(R.id.game_mistake_list_item_header);
                correct = itemView.findViewById(R.id.game_mistake_list_item_correct);
                incorrect = itemView.findViewById(R.id.game_mistake_list_item_incorrect);
            }
        }
    }
}