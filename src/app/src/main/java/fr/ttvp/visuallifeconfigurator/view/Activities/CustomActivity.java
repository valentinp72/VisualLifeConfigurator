package fr.ttvp.visuallifeconfigurator.view.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fr.ttvp.visuallifeconfigurator.R;

public abstract class CustomActivity extends AppCompatActivity {

    private Map<String, Serializable> results;
    protected ActionBar actionBar;

    public abstract int getContentView();

    protected void initParameters() {

    }

    protected void onChildActivityFinish(int resultCode, Intent data) {

    }

    protected abstract void initComponents();

    protected abstract void initView();

    public abstract String getToolbarTitle();

    public CustomActivity() {
        this.results = new HashMap<>();
    }

    public boolean toolbarBackButton() {
        return true;
    }

    public int getToolbarBackgroundColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    public int getToolbarTextColor() {
        return getResources().getColor(R.color.colorClicked);
    }

    public Serializable getParameter(String name) {
        return getIntent().getSerializableExtra(name);
    }

    public void addResult(String name, Serializable data) {
        this.results.put(name, data);
    }

    private String getHTMLColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }


    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getContentView());
        this.actionBar = getSupportActionBar();

        if(this.toolbarBackButton()) {
            this.actionBar.setDisplayHomeAsUpEnabled(true);
            this.actionBar.setDisplayShowHomeEnabled(true);
        }

        initParameters();
        initComponents();
        updateView();
    }

    protected final void updateView() {
        initToolbar();
        initView();
    }

    private final void initToolbar() {
        String askedTitle = this.getToolbarTitle();
        int textColor = this.getToolbarTextColor();
        int backColor = this.getToolbarBackgroundColor();
        int tintColor = this.manipulateColor(backColor, 0.8f);

        // title
        CharSequence title = Html.fromHtml("<font color='" + getHTMLColor(textColor) + "'>" + askedTitle + "</font>");
        this.actionBar.setTitle(title);

        // back arrow
        final Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        backArrow.setColorFilter(textColor, PorterDuff.Mode.SRC_ATOP);
        this.actionBar.setHomeAsUpIndicator(backArrow);

        // background
        this.actionBar.setBackgroundDrawable(new ColorDrawable(backColor));

        // status
        getWindow().setStatusBarColor(tintColor);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public final void finish() {
        Intent data = new Intent();
        for(Map.Entry<String, Serializable> item : this.results.entrySet()) {
            data.putExtra(item.getKey(), item.getValue());
        }
        setResult(RESULT_OK, data);
        super.finish();
    }

    public void launchActivity(Intent intent, int code) {
        this.startActivityForResult(intent, code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            onChildActivityFinish(requestCode, data);
            updateView();
        }
    }

    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

}
