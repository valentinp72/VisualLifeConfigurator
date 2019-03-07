package fr.ttvp.visuallifeconfigurator.view.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.Serializable;

import fr.ttvp.visuallifeconfigurator.model.Automata;

public class TabsAdapter extends FragmentPagerAdapter implements Serializable {

    private AutomataHome automataHome;

    public TabsAdapter(AutomataHome automataHome, FragmentManager fm) {
        super(fm);
        this.automataHome = automataHome;
    }

    @Override
    public Fragment getItem(int position) {
        Automata automata = automataHome.getAutomata();

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0) {
            return HomeTab.createInstance(automata, automataHome, new HomeTabCells());
        }
        else if(position == 1) {
            return HomeTab.createInstance(automata, automataHome, new HomeTabConfiguration());
        }
        else {
            return HomeTab.createInstance(automata, automataHome, new HomeTabPlay());
        }

    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

}
