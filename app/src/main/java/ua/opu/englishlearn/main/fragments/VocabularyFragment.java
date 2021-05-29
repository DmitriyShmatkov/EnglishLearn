package ua.opu.englishlearn.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import ua.opu.englishlearn.R;
import ua.opu.englishlearn.main.MainActivity;

public class VocabularyFragment extends Fragment {

    private int position;
    private MainActivity.MainViewPagerAdapter viewPagerAdapter;

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

        view.findViewById(R.id.wordAddButton).setOnClickListener(v -> {
            AppCompatActivity activity = Objects.requireNonNull((AppCompatActivity) getActivity());
            ActionBar actionBar = Objects.requireNonNull(activity.getSupportActionBar());
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
    }
}