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
import java.util.List;
import java.util.Objects;

import ua.opu.englishlearn.R;

public class GameActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ViewPager2 viewPager;

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

        /*viewPager = findViewById(R.id.gameActivityViewPager);
        viewPager.setUserInputEnabled(false);
        GameViewPagerAdapter viewPagerAdapter = new GameViewPagerAdapter(this);
//        viewPagerAdapter.addFragment();
        viewPager.setAdapter(viewPagerAdapter);*/
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