package com.example.asus.mypoker;

import android.util.Log;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static android.bluetooth.BluetoothHidDeviceAppQosSettings.MAX;

public class Player {

    private String name;
    private String surname;
    private String pseudo;
    //Maybe it's better if color is a HEX number
    private String color;
//    private Hashtable profitsByDate;

    private List<Integer> profits;


    public Player(String name, String surname, String pseudo) {
        this.name = name;
        this.surname = surname;
        this.pseudo = pseudo;
        this.color = color;
        this.profits = new ArrayList<Integer>();
    }

    public double getPlayerWinnings(int timeInterval, String method) {
        double winnings;
        if (method == "MEAN") {
            winnings = getMeanProfit(timeInterval);
        } else {
            winnings = getTotalProfit(timeInterval);
        }
        return winnings;
    }

    public double getTotalProfit(int timeInterval) {
        int size = this.profits.size();
        Log.d("ffffffffffffProfits", this.profits.toString());
        Log.d("ffffffffffffsize", Integer.toString(this.profits.size()));

        int total = 0;
        if (size != 0) {

            for (int i = size - 1; i >= (size - Math.min(timeInterval, size)); i--) {
                total += this.profits.get(i);
                Log.d("fffffffffIteration", Integer.toString(i));
                Log.d("fffffffffTotal", Integer.toString(total));
            }
            Log.d("fffffffffTotalFinal", Integer.toString(total));

            return total;
        }
        return 0;
    }

    public double getMeanProfit(int timeInterval) {
        Log.d("\n\nfffffffmeanplayer", this.pseudo);
        int size = this.profits.size();
        if (size != 0) {
            Log.d("fffffffffReturofFunct", Double.toString(getTotalProfit(timeInterval)));
            Log.d("ffffffffftimeINte", Integer.toString(timeInterval));
            Log.d("fffffffffMinSize", Double.toString(Math.min(timeInterval, size)));

            return Math.round(getTotalProfit(timeInterval) / Math.min(timeInterval, size));
        }
        return 0;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getColor() {
        return color;
    }

    public List<Integer> getProfits() {
        return profits;
    }

    public void addWinning(Integer cash) {
        this.profits.add(cash);
    }
}
