package fr.ttvp.visuallifeconfigurator.view.Activities;

import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.Map;
import fr.ttvp.visuallifeconfigurator.model.MapLight;
import fr.ttvp.visuallifeconfigurator.controller.Simulator;
import fr.ttvp.visuallifeconfigurator.view.Views.Grid2DView;

public class MapPlayingActivity extends CustomActivity {

    public static final String ARG_MAP = "MapLight";
    public static final String ARG_AUTOMATA = "Automata";

    private boolean playing;
    private Automata automata;
    private Map map;
    private MapLight mapLight;
    private Simulator simulator;

    private BottomNavigationView bottomNavigationViewPlaying;
    private BottomNavigationView bottomNavigationViewPaused;

    private FrameLayout contentLayout;

    private Menu menuPaused;
    private Menu menuPlay;

    private MenuItem spaceBtn;
    private MenuItem playBtn;
    private MenuItem fwdBtn;
    private MenuItem dcrBtn;
    private MenuItem pauseBtn;
    private MenuItem incrBtn;

    private Grid2DView grid;

    @Override
    public int getContentView() {
        return R.layout.activity_map_playing;
    }

    @Override
    protected void initParameters() {
        this.playing   = false;
        this.mapLight  = (MapLight) getParameter(ARG_MAP);
        this.map       = mapLight.getMap();
        this.automata  = (Automata) getParameter(ARG_AUTOMATA);
        this.simulator = new Simulator(automata, map);
    }

    @Override
    protected void initComponents() {
        this.contentLayout = findViewById(R.id.map_playing_content);
        this.bottomNavigationViewPlaying = findViewById(R.id.map_playing_menu_playing);
        this.bottomNavigationViewPaused  = findViewById(R.id.map_playing_menu_paused);
        findViewById(R.id.menu_paused_item_space).setVisibility(View.INVISIBLE);

        this.menuPaused = this.bottomNavigationViewPaused.getMenu();
        this.menuPlay = this.bottomNavigationViewPlaying.getMenu();

        this.spaceBtn = menuPaused.findItem(R.id.menu_paused_item_space);
        this.playBtn  = menuPaused.findItem(R.id.menu_paused_item_play).setChecked(true);
        this.fwdBtn   = menuPaused.findItem(R.id.menu_paused_item_forward);
        this.dcrBtn   = menuPlay.findItem(R.id.menu_playing_item_smaller);
        this.pauseBtn = menuPlay.findItem(R.id.menu_playing_item_pause).setChecked(true);
        this.incrBtn  = menuPlay.findItem(R.id.menu_playing_item_bigger);

        this.grid = new Grid2DView(this, automata, simulator, this.bottomNavigationViewPaused);
        this.contentLayout.addView(grid);
    }

    @Override
    protected void initView() {
        this.showCorrespondingBottomNav();
    }

    @Override
    public String getToolbarTitle() {
        return mapLight.getName();
    }

    private void showCorrespondingBottomNav() {
        if(this.playing) {
            this.bottomNavigationViewPaused.setVisibility(View.GONE);
            this.bottomNavigationViewPlaying.setVisibility(View.VISIBLE);
        }
        else {
            this.bottomNavigationViewPaused.setVisibility(View.VISIBLE);
            this.bottomNavigationViewPlaying.setVisibility(View.GONE);
        }
    }

    public void clickPause(MenuItem item) {
        this.playing = false;
        item.setChecked(true);
        updateView();
        simulator.pause();
    }

    public void clickPlay(MenuItem item) {
        this.playing = true;
        item.setChecked(true);
        updateView();
        simulator.play();
    }

    public void clickForward(MenuItem item) {
        simulator.step();
    }

    public void clickSmaller(MenuItem item) {
        simulator.smaller();
    }

    public void clickBigger(MenuItem item) {
        simulator.bigger();
    }

    public void clickSpace(MenuItem item) {
        // nothing to do
    }

}
