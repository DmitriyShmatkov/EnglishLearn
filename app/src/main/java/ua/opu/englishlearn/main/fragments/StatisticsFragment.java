package ua.opu.englishlearn.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.opu.englishlearn.R;
import ua.opu.englishlearn.room.dataclasses.FullGame;
import ua.opu.englishlearn.room.entities.Game;
import ua.opu.englishlearn.room.repository.EnglishLearnRepository;

public class StatisticsFragment extends Fragment {

    private RecyclerView recyclerView;

    public StatisticsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EnglishLearnRepository repository = EnglishLearnRepository.getInstance(requireContext());

        // sorted by date descending list of all games
        List<FullGame> fullGames = repository.getAllGames();

        // set of games dates
        Set<Date> gamesDates = new LinkedHashSet<>();
        for (FullGame fullGame : fullGames) {
            gamesDates.add(fullGame.getGame().getDate());
        }
        System.out.println(gamesDates);

        // games sub lists by each date
        List<List<FullGame>> fullGamesListByDate = new ArrayList<>();
        int i = 0;
        for (Date date : gamesDates) {
            List<FullGame> fullGamesAtDate = new ArrayList<>();
            // since we have sorted data, we know where to stop searching on current iteration
            while (i < fullGames.size() && fullGames.get(i).getGame().getDate().equals(date)) {
                fullGamesAtDate.add(fullGames.get(i));
                i++;
            }
            fullGamesListByDate.add(fullGamesAtDate);
        }
        System.out.println(fullGamesListByDate);


        recyclerView = view.findViewById(R.id.statistics_fragment_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2,
                GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new StatisticsAdapter(fullGamesListByDate));
    }

    public class StatisticsAdapter extends
            RecyclerView.Adapter<StatisticsAdapter.StatisticsDayCardViewHolder> {

        private List<List<FullGame>> fullGamesListByDate;
        private final Map<Integer, String> monthsMap = new HashMap<>();

        public StatisticsAdapter(List<List<FullGame>> fullGamesListByDate) {
            this.fullGamesListByDate = fullGamesListByDate;
            monthsMap.put(0, "January");
            monthsMap.put(1, "February");
            monthsMap.put(2, "March");
            monthsMap.put(3, "April");
            monthsMap.put(4, "May");
            monthsMap.put(5, "June");
            monthsMap.put(6, "July");
            monthsMap.put(7, "August");
            monthsMap.put(8, "September");
            monthsMap.put(9, "October");
            monthsMap.put(10, "November");
            monthsMap.put(11, "December");
        }

        @NonNull
        @Override
        public StatisticsDayCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new StatisticsDayCardViewHolder(
                    LayoutInflater.from(requireContext())
                            .inflate(R.layout.statistics_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull StatisticsDayCardViewHolder holder, int position) {

            List<FullGame> fullGames = fullGamesListByDate.get(position);

            Date date = fullGames.get(0).getGame().getDate();
            holder.dateTextView.setText(
                    String.format(getText(R.string.statistics_list_item_date_text).toString(),
                            monthsMap.get(date.getMonth()), date.getDate()));
            holder.gamesNumberTextView.setText(
                    String.format(getText(R.string.statistics_list_item_games_number_text).toString(),
                            fullGames.size()));

            double correctAnswersRatio = 0.0;
            for (FullGame fullGame : fullGames) {
                Game game = fullGame.getGame();
                correctAnswersRatio += (double) game.getCorrectAnswers() / game.getQuestionsNumber();
            }
            correctAnswersRatio /= fullGames.size();

            int colorId = R.color.black;
            if (correctAnswersRatio >= 0 && correctAnswersRatio < 0.4) {
                colorId = R.color.bad;
            } else if (correctAnswersRatio >= 0.4 && correctAnswersRatio < 0.75) {
                colorId = R.color.medium;
            } else if (correctAnswersRatio >= 0.75 && correctAnswersRatio <= 1) {
                colorId = R.color.good;
            } else {
                Toast.makeText(requireContext(), "Points out of bounds", Toast.LENGTH_LONG).show();
            }

            holder.correctAnswersTextView.setText(
                    String.format(getText(R.string.statistics_list_item_correct_answers_text).toString(),
                            Math.round(correctAnswersRatio * 100)));

            holder.card.setStrokeColor(ContextCompat.getColor(requireContext(), colorId));
        }

        @Override
        public int getItemCount() {
            return fullGamesListByDate.size();
        }


        class StatisticsDayCardViewHolder extends RecyclerView.ViewHolder {

            private MaterialCardView card;
            private TextView dateTextView;
            private TextView gamesNumberTextView;
            private TextView correctAnswersTextView;

            public StatisticsDayCardViewHolder(@NonNull View itemView) {
                super(itemView);

                card = itemView.findViewById(R.id.statistics_list_item_card);
                dateTextView = itemView.findViewById(R.id.statistics_list_item_date);
                gamesNumberTextView = itemView.findViewById(R.id.statistics_list_item_games_number);
                correctAnswersTextView = itemView.findViewById(R.id.statistics_list_item_correct_answers);
            }
        }

    }
}