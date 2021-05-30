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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ua.opu.englishlearn.R;
import ua.opu.englishlearn.main.MainActivity;
import ua.opu.englishlearn.room.entities.Word;
import ua.opu.englishlearn.room.repository.EnglishLearnRepository;

public class VocabularyFragment extends Fragment {

    private int position;
    private MainActivity.MainViewPagerAdapter viewPagerAdapter;
    private List<Word> words = new ArrayList<>();
    private EnglishLearnRepository repository;

    public VocabularyFragment() {
    }

    public VocabularyFragment(int position, MainActivity.MainViewPagerAdapter viewPagerAdapter) {
        this.position = position;
        this.viewPagerAdapter = viewPagerAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vocabulary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*words.add(new Word("nfsdjkf", "fdsaf"));
        words.add(new Word("nfsdjkf", "fdsaf"));
        words.add(new Word("nfsdjkf", "fdsaf"));
        words.add(new Word("nfsdjkf", "fdsaf"));
        words.add(new Word("nfsdjkf", "fdsaf"));
        words.add(new Word("nfsdjkf", "fdsaf"));
        words.add(new Word("nfsdjkf", "fdsaf"));*/

        AppCompatActivity activity = Objects.requireNonNull((AppCompatActivity) getActivity());
        ActionBar actionBar = Objects.requireNonNull(activity.getSupportActionBar());


        view.findViewById(R.id.wordAddButton).setOnClickListener(v -> {
            actionBar.setTitle(R.string.word_add_fragment_title);
            Toolbar toolbar = activity.findViewById(R.id.mainActivityToolbar);

            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
            toolbar.setNavigationOnClickListener(v1 -> {
                actionBar.setTitle(R.string.vocabulary_fragment_title);
                toolbar.setNavigationIcon(null);
                viewPagerAdapter.replace(position, this);
            });
            viewPagerAdapter.replace(position, new WordAddFragment());
        });

        RecyclerView recyclerView = view.findViewById(R.id.vocabularyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        repository = EnglishLearnRepository.getInstance(requireContext());

        /*List<Word> insertList = Arrays.asList(
                new Word("apple", "яблоко", false),
                new Word("orange", "апельсин", false),
                new Word("banana", "банан", false),
                new Word("pineapple", "ананас", false),
                new Word("tomato", "помидор", false),
                new Word("table", "стол", false),
                new Word("sugar", "сахар", false),
                new Word("chair", "стул", false),
                new Word("pan", "сковорода", false),
                new Word("sausage", "сосиска", false),
                new Word("milk", "молоко", false),
                new Word("teeth", "зубы", false),
                new Word("mouth", "рот", false),
                new Word("mouse", "мышь", false),
                new Word("moist", "влажный", false),
                new Word("gate", "ворота", false),
                new Word("bed", "кровать", false),
                new Word("sofa", "диван", false),
                new Word("pencil", "карандаш", false),
                new Word("square", "квадрат", false)
        );
        repository.insertAllWords(insertList);*/

        words = repository.getAddedWords();
        recyclerView.setAdapter(new VocabularyListAdapter(words));


        EditText searchEditText = view.findViewById(R.id.vocabularySearchEditText);
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
                VocabularyListAdapter searchResultAdapter = new VocabularyListAdapter(searchResult);
                recyclerView.setAdapter(searchResultAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    class VocabularyListAdapter extends RecyclerView.Adapter<VocabularyListAdapter.VocabularyWordViewHolder> {

        private final List<Word> words;

        public VocabularyListAdapter(List<Word> words) {
            this.words = words;
        }

        @NonNull
        @Override
        public VocabularyWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VocabularyWordViewHolder(getLayoutInflater().
                    inflate(R.layout.vocabulary_word_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VocabularyListAdapter.VocabularyWordViewHolder holder, int position) {
            Word word = words.get(position);
            holder.englishTranslation.setText(word.getEnglishTranslation());
            holder.russianTranslation.setText(word.getRussianTranslation());
            holder.deleteButton.setOnClickListener(v -> {
                word.setAdded(false);
                repository.updateWord(word);
                words.remove(word);
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return words.size();
        }

        class VocabularyWordViewHolder extends RecyclerView.ViewHolder {

            private TextView englishTranslation;
            private TextView russianTranslation;
            private ImageButton deleteButton;

            public VocabularyWordViewHolder(@NonNull View itemView) {
                super(itemView);

                englishTranslation = itemView.findViewById(R.id.questionWord);
                russianTranslation = itemView.findViewById(R.id.wordRuTranslation);
                deleteButton = itemView.findViewById(R.id.wordDeleteButton);
            }
        }
    }
}