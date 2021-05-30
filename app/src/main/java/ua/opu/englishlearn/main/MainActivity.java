package ua.opu.englishlearn.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import ua.opu.englishlearn.R;
import ua.opu.englishlearn.main.fragments.StatisticsFragment;
import ua.opu.englishlearn.main.fragments.VocabularyFragment;
import ua.opu.englishlearn.main.fragments.WordAddFragment;
import ua.opu.englishlearn.room.database.EnglishLearnDatabase;
import ua.opu.englishlearn.room.entities.Word;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;
    List<ImageButton> bottomNavButtons;

    long[] fdasf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        /*EnglishLearnDatabase database = EnglishLearnDatabase.getInstance(this);
        List<Word> words = Arrays.asList(
                new Word("fdas", "fdsaf"),
                new Word("fdas", "fdsaf"),
                new Word("fdas", "fdsaf"),
                new Word("fdas", "fdsaf"),
                new Word("fdas", "fdsaf")
        );*/
        /*final long[] id = new long[1];
        id[0] = -1;
        Word word = new Word("fdas", "fdsaf");*/
        /*Executors.newSingleThreadExecutor().execute(() -> fdasf = database.wordDAO().insertAll(words));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actionBar.setTitle(Arrays.toString(fdasf));*/

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
}