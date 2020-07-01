package com.example.asus.mypoker;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String playersList[];
    ArrayList<Double> winningsList;
//    int pictures[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] playersList, ArrayList<Double> winningsList) {
        this.context = context;
        this.playersList = playersList;
//        this.pictures = pictures;
        this.winningsList = winningsList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return playersList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);

        TextView winnings = (TextView) view.findViewById(R.id.winnings);
        TextView rank = (TextView) view.findViewById(R.id.rank);
        TextView players = (TextView) view.findViewById(R.id.pseudo);

//        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        players.setText(playersList[i]);
        winnings.setText(new Double(winningsList.get(i)).toString());

//        icon.setImageResource(pictures[i]);

        rank.setText(new Integer(i+1).toString());
        return view;
    }
}
