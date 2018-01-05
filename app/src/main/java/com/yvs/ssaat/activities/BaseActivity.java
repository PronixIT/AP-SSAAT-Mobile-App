package com.yvs.ssaat.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.yvs.ssaat.MainActivity;
import com.yvs.ssaat.R;
import com.yvs.ssaat.session.SessionManager;


/**
 * Created by surya on 9/15/2017.
 */

public class BaseActivity extends AppCompatActivity {

    public FrameLayout frame_base;
    public Toolbar toolbar;
    private ListView list_items;
    private SlidingPaneLayout pane;
    private Intent intent;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base);
        frame_base = (FrameLayout) findViewById(R.id.frame_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sessionManager =  new SessionManager(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu rmenu) {
        getMenuInflater().inflate(R.menu.menu_main, rmenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.dashboard:
                intent = new Intent(BaseActivity.this, MainActivity.class);
                startActivity(intent);
                BaseActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;

            case R.id.profile:
                intent = new Intent(BaseActivity.this, ProfileActivity.class);
                startActivity(intent);
                BaseActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;

            case R.id.reports:
                intent = new Intent(BaseActivity.this, ReportsMain.class);
                startActivity(intent);
                BaseActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;

            case R.id.logout:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(BaseActivity.this);
                alertDialog.setTitle("SSAAT");
                alertDialog.setMessage("Do you want to Logout Now.. ?");
                alertDialog.setIcon(R.mipmap.ic_launcher);
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.logoutUser();
                        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        BaseActivity.this.finish();
                    }
                });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
