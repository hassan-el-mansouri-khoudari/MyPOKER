package com.example.asus.mypoker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class RankingsActivity extends AppCompatActivity {


    static ArrayList<String> pseudosChecked = new ArrayList<>();
    static int timeInterval = Integer.MAX_VALUE;
    static int method = R.id.total;

    private Spinner timeIntervalSpinner;
    private RadioGroup rg;
    private MyAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_rankings:
                    return true;
                case R.id.navigation_add:
                    Intent intent_add = new Intent(RankingsActivity.this, AddActivity.class);
                    startActivity((intent_add));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_players:
                    Intent intent_players = new Intent(RankingsActivity.this, PlayersActivity.class);
                    startActivity((intent_players));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_settings:
                    Intent intent_settings = new Intent(RankingsActivity.this, SettingsActivity.class);
                    startActivity((intent_settings));overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        }
    };


    public ArrayList<Double> getWinnings(ArrayList<String> selectedPseudosArrayList, int timeInterval, String method) {
        ArrayList<Double> winnings = new ArrayList<Double>();
        ArrayList<Player> playersArrayList = new DataHandler(getFilesDir().getAbsolutePath()).getPlayersArrayList();
        if (selectedPseudosArrayList.size() != 0) {
            for (int i = 0; i < selectedPseudosArrayList.size(); i++) {
                for (int j = 0; j < playersArrayList.size(); j++) {
                    if (selectedPseudosArrayList.get(i).equals(playersArrayList.get(j).getPseudo())) {
                        winnings.add(playersArrayList.get(j).getPlayerWinnings(timeInterval, method));
                    }
                }
            }
        }
        return winnings;
    }

    /**
     *
     * @param method recalculates the rankings using the method 'MEAN' or 'TOTAL' winnings
     * @param pseudosArrayList recalculates the rankings including only the players with the given pseudos
     * @param timeInterval recalculates the rankings includings the last @timeInteval games of each players
     */
    public void updateRankings(int method, ArrayList<String> pseudosArrayList, int timeInterval) {
        ListView rankingsList;
        String playersList[] = new String[pseudosArrayList.size()];
        playersList = pseudosArrayList.toArray(playersList);
        ArrayList<Double> winnings;

        if (method == R.id.mean){
             winnings = getWinnings(pseudosArrayList, timeInterval, "MEAN");
        } else {
            winnings = getWinnings(pseudosArrayList, timeInterval, "TOTAL");
        }

        HashMap<String, Double> rankingsHashMap = new HashMap<String, Double>();
        for (int i = 0; i<playersList.length; i++){
            rankingsHashMap.put(playersList[i],winnings.get(i));
        }
        HashMap<String,Double> rankingsHashMapSorted = sortByValue(rankingsHashMap);
        int count = 0;
        for (Map.Entry<String, Double> entry : rankingsHashMapSorted.entrySet()) {
            winnings.add(entry.getValue());
            winnings.remove(0);
            playersList[count] = entry.getKey();
            count ++;
        }

        rankingsList = findViewById(R.id.rankingsView);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), playersList, winnings);
        rankingsList.setAdapter(customAdapter);
    }

    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list =
                new LinkedList<Map.Entry<String, Double> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);
        //Set NavigationViewListener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_rankings);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        pseudosChecked = new ArrayList<>();
        method = R.id.total;
        timeInterval = Integer.MAX_VALUE;


        final RadioGroup methodrg = findViewById(R.id.method);
        methodrg.check(R.id.total);
        this.rg = methodrg;




        ArrayList<Player> playersArrayList = new DataHandler(getFilesDir().getAbsolutePath()).getPlayersArrayList();
        Log.d("*********size",Integer.toString(playersArrayList.size()));
        Spinner spinner_players = findViewById(R.id.players);

        ArrayList<StateVO> listVOs = new ArrayList<>();

        StateVO stateVOShadow = new StateVO();
        stateVOShadow.setTitle("Choose players");
        stateVOShadow.setSelected(false);
        listVOs.add(stateVOShadow);

        for (int i = 0; i < playersArrayList.size(); i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(playersArrayList.get(i).getPseudo());
            stateVO.setSelected(true);
            listVOs.add(stateVO);
        }
        final MyAdapter myAdapter = new MyAdapter(RankingsActivity.this, 0, listVOs, this);
        spinner_players.setAdapter(myAdapter);


        final Spinner timeIntervalSpinner = findViewById(R.id.timeInterval);
        final String[] list_timeInterval = {"Last game", "Last 3 games", "Last 7 games", "Last 15 games", "All"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_timeInterval);
        timeIntervalSpinner.setAdapter(arrayAdapter);
        //Last seven games is default


        timeIntervalSpinner.setSelection(4);
        this.timeIntervalSpinner = timeIntervalSpinner;


    }

    @Override
    protected void onStart() {
        super.onStart();
        updateRankings(method, pseudosChecked, timeInterval);


        this.timeIntervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                Log.d("posssssii",Integer.toString(position));
                switch (position){
                    case 0:
                        timeInterval = 1;
                        break;
                    case 1:
                        timeInterval = 3;
                        break;
                    case 2:
                        timeInterval = 7;
                        break;
                    case 3:
                        timeInterval = 15;
                        break;
                    case 4:
                        timeInterval = Integer.MAX_VALUE;
                        break;
                }
                Log.d("intervvvvvvv",Integer.toString(timeInterval));

                updateRankings(method, pseudosChecked, timeInterval);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // This overrides the radiogroup onCheckListener
        this.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                method = rg.getCheckedRadioButtonId();
                updateRankings(method, pseudosChecked, timeInterval);
            }
        });


    }
}
