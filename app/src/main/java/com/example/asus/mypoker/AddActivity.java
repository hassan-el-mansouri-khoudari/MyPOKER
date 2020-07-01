package com.example.asus.mypoker;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddActivity extends AppCompatActivity {

    static int screenWidth;
    static int screenHeight;

    static ArrayList<EditText> winnings;
    static ArrayList<Spinner> spinners;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_rankings:
                    Intent intent_rankings = new Intent(AddActivity.this, RankingsActivity.class);
                    startActivity((intent_rankings));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_add:
                    return true;
                case R.id.navigation_players:
                    Intent intent_players = new Intent(AddActivity.this, PlayersActivity.class);
                    startActivity((intent_players));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_settings:
                    Intent intent_settings = new Intent(AddActivity.this, SettingsActivity.class);
                    startActivity((intent_settings));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("**Info", "**AddActivityStarted");

        setContentView(R.layout.activity_add);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_add);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        winnings = new ArrayList<EditText>();
        spinners = new ArrayList<Spinner>();

        ConstraintLayout cL = (ConstraintLayout) findViewById(R.id.cL);


        final FrameLayout screen = (FrameLayout) findViewById(R.id.screen);
        final LinearLayout player1 = findViewById(R.id.player1);
        final LinearLayout player2 = findViewById(R.id.player2);
        final LinearLayout player3 = findViewById(R.id.player3);
        final LinearLayout player4 = findViewById(R.id.player4);
        final LinearLayout player5 = findViewById(R.id.player5);
        final LinearLayout player6 = findViewById(R.id.player6);

        screen.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                screen.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                screenHeight = screen.getHeight(); //height is ready
                screenWidth = screen.getWidth(); //height is ready
                Log.d("******", Integer.toString(screenHeight));
                Log.d("******", Integer.toString(screenWidth));


                player1.setX(Math.round(388 * screenWidth / 557));
                player1.setY(Math.round(120 * screenHeight / 841));

                player2.setX(Math.round(210 * screenWidth / 557));
                player2.setY(Math.round(41 * screenHeight / 841));

                player3.setX(Math.round(50 * screenWidth / 557));
                player3.setY(Math.round(120 * screenHeight / 841));

                player4.setX(Math.round(50 * screenWidth / 557));
                player4.setY(Math.round(623 * screenHeight / 841));

                player6.setX(Math.round(388 * screenWidth / 557));
                player6.setY(Math.round(623 * screenHeight / 841));

                player5.setX(Math.round(210 * screenWidth / 557));
                player5.setY(Math.round(723 * screenHeight / 841));

            }
        });


        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);
        Spinner spinner4 = findViewById(R.id.spinner4);
        Spinner spinner5 = findViewById(R.id.spinner5);
        Spinner spinner6 = findViewById(R.id.spinner6);


        spinners.add(spinner1);
        spinners.add(spinner2);
        spinners.add(spinner3);
        spinners.add(spinner4);
        spinners.add(spinner5);
        spinners.add(spinner6);

        EditText winnings1 = findViewById(R.id.winnings1);
        EditText winnings2 = findViewById(R.id.winnings2);
        EditText winnings3 = findViewById(R.id.winnings3);
        EditText winnings4 = findViewById(R.id.winnings4);
        EditText winnings5 = findViewById(R.id.winnings5);
        EditText winnings6 = findViewById(R.id.winnings6);

        winnings.add(winnings1);
        winnings.add(winnings2);
        winnings.add(winnings3);
        winnings.add(winnings4);
        winnings.add(winnings5);
        winnings.add(winnings6);

        ArrayList<Player> playerArrayList = new DataHandler(getFilesDir().getAbsolutePath()).getPlayersArrayList();
        final String[] playersPseudos = new String[playerArrayList.size()];
        for (int i = 0; i < playerArrayList.size(); i++) {
            playersPseudos[i] = playerArrayList.get(i).getPseudo();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, playersPseudos);
        spinner1.setAdapter(arrayAdapter);
        spinner2.setAdapter(arrayAdapter);
        spinner3.setAdapter(arrayAdapter);
        spinner4.setAdapter(arrayAdapter);
        spinner5.setAdapter(arrayAdapter);
        spinner6.setAdapter(arrayAdapter);

        Button register = (Button) findViewById(R.id.register);
        final EditText totalMoneyText = (EditText) findViewById(R.id.totalMoney);
        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                ArrayList<Integer> values = new ArrayList<Integer>();
                ArrayList<String> players = new ArrayList<String>();
                Integer totalMoney = Integer.valueOf(totalMoneyText.getText().toString());
                for (int i = 0; i < winnings.size(); i++) {
                    Log.d("***it******", Integer.toString(i));
                    Log.d("***it******", players.toString());

                    if (winnings.get(i).getText().toString().length() != 0) {
                        Log.d("value of winnings ", winnings.get(i).getText().toString());
                        Log.d("length", Integer.toString(winnings.get(i).getText().toString().length()));
                        if (players.contains(spinners.get(i).getSelectedItem().toString())) {
                            Toast.makeText(AddActivity.this, spinners.get(i).getSelectedItem().toString() + " is entered twice !", Toast.LENGTH_LONG).show();
                            return;
                        }
                        values.add(Integer.valueOf(winnings.get(i).getText().toString()));
                        players.add(spinners.get(i).getSelectedItem().toString());
                    }
                }
                // Calculate the profits of each player where they each split evenly the totalMoney at the beginning
                ArrayList<Integer> profits = new ArrayList<>();
                for(int i =0; i<values.size();i++){
                    profits.add(values.get(i)-Math.round(totalMoney/values.size()));
                }

                Integer sumOfValues = 0;
                for (int i = 0; i < values.size(); i++) {
                    sumOfValues += values.get(i);
                }
                Log.d("*****************", values.toString());
                Log.d("*****************", Integer.toString(sumOfValues));
                if (!sumOfValues.equals(totalMoney)) {
                    Toast.makeText(AddActivity.this, "The values entered do not sum to " + Integer.toString(totalMoney), Toast.LENGTH_LONG).show();
                    return;
                } else {
                    boolean success = new DataHandler(getFilesDir().getAbsolutePath()).addWinnings(players, profits);
                    if (success) {
                        Toast.makeText(AddActivity.this, "Added game ! Check your ranking", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddActivity.this, "Error when entering new game ! Try again.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }


}
