package com.example.asus.mypoker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_rankings:
                    Intent intent_rankings = new Intent(SettingsActivity.this, RankingsActivity.class);
                    startActivity((intent_rankings));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_add:
                    Intent intent_add = new Intent(SettingsActivity.this, AddActivity.class);
                    startActivity((intent_add));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_players:
                    Intent intent_players = new Intent(SettingsActivity.this, PlayersActivity.class);
                    startActivity((intent_players));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_settings:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.d("**Info","**SettingsActivity")        ;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_settings);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


}
