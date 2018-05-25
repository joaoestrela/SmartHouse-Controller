package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.fragment.LightsFragment;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.fragment.MusicFragment;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_ITEM = "arg_selected_item";
    private int mSelectedItem;
    BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNav = findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });
        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = mBottomNav.getMenu().getItem(0);
        }
        selectFragment(selectedItem);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    private void selectFragment(MenuItem item) {
        Fragment frag = null;
        if(mSelectedItem == item.getItemId()) return;
        switch (item.getItemId()) {
            case R.id.navigation_lights:
                frag = LightsFragment.newInstance();
                break;
            case R.id.navigation_music:
                frag = MusicFragment.newInstance();
                break;
            case R.id.navigation_settings:
                frag = SettingsFragment.newInstance();
                break;
        }
        mSelectedItem = item.getItemId();
        updateToolbarText(item.getTitle());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (frag != null) {
            ft.replace(R.id.container, frag);
            ft.commit();
        }
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name)+ " - " + text);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Are you sure you want to exit?");
        dlgAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dlgAlert.setNegativeButton("No", null);
        dlgAlert.create().show();
    }

}
