package ua.opu.englishlearn.main.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ua.opu.englishlearn.R;
import ua.opu.englishlearn.room.entities.Word;
import ua.opu.englishlearn.room.repository.EnglishLearnRepository;

public class WordAddFragment extends Fragment {

    private List<Word> words = new ArrayList<>();
    private EnglishLearnRepository repository;

    public WordAddFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_word_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = EnglishLearnRepository.getInstance(requireContext());
        RecyclerView recyclerView = view.findViewById(R.id.wordAddRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        words = repository.getAllWords();
        recyclerView.setAdapter(new WordAddListAdapter(words));

        EditText searchEditText = view.findViewById(R.id.wordAddSearchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchInput = s.toString();
                List<Word> searchResult = new ArrayList<>();
                for (Word word : words) {
                    String regex = searchInput + ".*";
                    if (word.getEnglishTranslation().matches(regex)) {
                        searchResult.add(word);
                    }
                }
                WordAddListAdapter searchResultAdapter = new WordAddListAdapter(searchResult);
                recyclerView.setAdapter(searchResultAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    class WordAddListAdapter extends RecyclerView.Adapter<WordAddListAdapter.WordAddWordViewHolder> {

        private List<Word> words;

        public WordAddListAdapter(List<Word> words) {
            this.words = words;
        }

        @NonNull
        @Override
        public WordAddWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new WordAddWordViewHolder(getLayoutInflater().
                    inflate(R.layout.vocabulary_word_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull WordAddFragment.WordAddListAdapter.WordAddWordViewHolder holder, int position) {
            Word word = words.get(position);

            holder.englishTranslation.setText(word.getEnglishTranslation());
            holder.russianTranslation.setText(word.getRussianTranslation());
            ImageButton addRemoveButton = holder.addRemoveButton;
            if (word.isAdded()) {
                addRemoveButton.setImageResource(R.drawable.ic_check_circle_outline_black_24dp);
                holder.addRemoveButton.setOnClickListener(v -> {
                    word.setAdded(false);
                    repository.updateWord(word);
                    addRemoveButton.setImageResource(R.drawable.ic_outline_add_circle_outline_24);
                });
            } else {
                addRemoveButton.setImageResource(R.drawable.ic_outline_add_circle_outline_24);
                holder.addRemoveButton.setOnClickListener(v -> {
                    word.setAdded(true);
                    repository.updateWord(word);
                    addRemoveButton.setImageResource(R.drawable.ic_check_circle_outline_black_24dp);
                });
            }

        }

        @Override
        public int getItemCount() {
            return words.size();
        }

        class WordAddWordViewHolder extends RecyclerView.ViewHolder {

            private TextView englishTranslation;
            private TextView russianTranslation;
            private ImageButton addRemoveButton;


            public WordAddWordViewHolder(@NonNull View itemView) {
                super(itemView);

                englishTranslation = itemView.findViewById(R.id.wordEnTranslation);
                russianTranslation = itemView.findViewById(R.id.wordRuTranslation);
                addRemoveButton = itemView.findViewById(R.id.wordDeleteButton);
            }
        }
    }
}