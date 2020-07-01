package com.example.asus.mypoker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class PlayersActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_rankings:
                    Intent intent_rankings = new Intent(PlayersActivity.this, RankingsActivity.class);
                    startActivity((intent_rankings));
                    overridePendingTransition(0, 0);

                    return true;
                case R.id.navigation_add:
                    Intent intent_add = new Intent(PlayersActivity.this, AddActivity.class);
                    startActivity((intent_add));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_players:
                    return true;
                case R.id.navigation_settings:
                    Intent intent_settings = new Intent(PlayersActivity.this, SettingsActivity.class);
                    startActivity((intent_settings));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        }
    };

    public boolean checkPseudo(String pseudo) {
        if (pseudo.length()==0)
            return false;
        ArrayList<Player> playerArrayList = new DataHandler(getFilesDir().getAbsolutePath()).getPlayersArrayList();
        if (playerArrayList.size() != 0) {
            for (int i = 0; i < playerArrayList.size(); i++) {
                if (playerArrayList.get(i).getPseudo().equals(pseudo)) {
                    return false;
                }
            }
        }
        Log.d("DEBUG/CheckPseudo()", "List is empty");
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("**Info", "**PlayersActivityStarted");
        setContentView(R.layout.activity_players);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_players);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FloatingActionButton addPlayerButton = (FloatingActionButton) findViewById(R.id.addPlayerBtn);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Add new player's info");

                LayoutInflater inflater = LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
                View viewInflated = inflater.inflate(R.layout.player_signup_dialog, null);

                final EditText name = (EditText) viewInflated.findViewById(R.id.name);
                final EditText surname = (EditText) viewInflated.findViewById(R.id.surname);
                final EditText pseudoName = (EditText) viewInflated.findViewById(R.id.pseudo);

                builder.setView(viewInflated);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pseudo = pseudoName.getText().toString();
                        if (checkPseudo(pseudo)) {
                            new DataHandler(getFilesDir().getAbsolutePath()).appendNewPlayer(name.getText().toString(), surname.getText().toString(), pseudoName.getText().toString());
                        } else {
                            Toast.makeText(PlayersActivity.this, "Invalid Pseudo! Couldn't register new player", Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });


    }


}
