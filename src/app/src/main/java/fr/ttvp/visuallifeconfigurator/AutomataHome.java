package fr.ttvp.visuallifeconfigurator;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.AutomataLight;
import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.model.Persitance;

public class AutomataHome extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private Automata automata;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automata_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        automata = ((AutomataLight) getIntent().getSerializableExtra("AutomataLight")).getRealAutomata();
        System.out.println(automata);

        toolbar.setTitle(automata.getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_automata_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public Automata getAutomata() {
        return this.automata;
    }

    public static class HomeTabCells extends Fragment {

        private Automata automata;

        public static HomeTabCells createInstance(Automata automata) {
            HomeTabCells htc = new HomeTabCells();
            Bundle args = new Bundle();
            args.putSerializable("Automata", automata);
            htc.setArguments(args);
            return htc;
        }

        public HomeTabCells() {
            super();
            /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });*/
        }

        @Override
        public void onCreate(Bundle b) {
            super.onCreate(b);
            automata = (Automata) getArguments().getSerializable("Automata");
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_automata_home_cells, container, false);
            System.out.println(automata.getName());
            LinearLayout ll = view.findViewById(R.id.contents);

            for (Cell cell : automata.getCells()) {
                View toAdd = new AutomataCellInListView(view.getContext(), automata, cell);
                if(cell.isDefaultCell()) {
                    ll.addView(toAdd, 1);
                }
                else {
                    ll.addView(toAdd);
                }
            }
            return view;
        }
    }

    public static class HomeTabConfiguration extends Fragment {

        public HomeTabConfiguration() {
            Bundle args = new Bundle();
            this.setArguments(args);
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_automata_home_configuration, container, false);

            //ll.addView(new AutomataCellInListView(view.getContext()));
            return view;
        }
    }


    public static class HomeTabPlay extends Fragment {

        public HomeTabPlay() {
            Bundle args = new Bundle();
            this.setArguments(args);
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_automata_home_play, container, false);

            return view;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private AutomataHome automataHome;

        public SectionsPagerAdapter(AutomataHome automataHome, FragmentManager fm) {
            super(fm);
            this.automataHome = automataHome;
        }

        @Override
        public Fragment getItem(int position) {
            Automata automata = automataHome.getAutomata();

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 0) {
                return HomeTabCells.createInstance(automata);
            }
            else if(position == 1) {
                return new HomeTabConfiguration();
            }
            else {
                return new HomeTabPlay();
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
