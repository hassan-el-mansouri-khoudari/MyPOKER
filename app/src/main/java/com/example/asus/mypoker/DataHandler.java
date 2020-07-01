package com.example.asus.mypoker;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataHandler {

    String filesDirPath;
    String data_folder_name = "Players";
    String data_file_name = "MyPokerData.txt";

    public DataHandler(String path) {
        this.filesDirPath = path;
    }

    public ArrayList<Player> getPlayersArrayList() {
        checkOrCreateDataLocation();

        ArrayList<Player> playersArrayList = new ArrayList<Player>();

        Gson gson = new Gson();
        //the file to be opened for reading
//        FileInputStream fis= new FileInputStream(filesDirPath+"/"+R.string.data_folder_name+"/"+R.string.data_file_name);
//        Scanner sc=new Scanner(filesDirPath +"/"+data_folder_name+"/"+data_file_name);    //file to be scanned
//        //returns true if there is another line to read
//        while(sc.hasNextLine())
//        {
//            String line = sc.nextLine();
//            Log.d("SCCC",line);
//            if (line.length() != 0) {
//                playersArrayList.add(gson.fromJson(line, Player.class));
//            }
//        }
//        sc.close();     //closes the scanner
        try {
            FileInputStream is;
            BufferedReader reader;
            File file = new File(filesDirPath + "/" + data_folder_name + "/" + data_file_name);
            if (file.exists()) {

                is = new FileInputStream(file);

                reader = new BufferedReader(new InputStreamReader(is));
                String line = null;

                line = reader.readLine();
                if (line != null)
                    Log.d("************getplayer", line);

                while (line != null) {

                    try {
                        playersArrayList.add(gson.fromJson(line, Player.class));
                    } catch (Exception e) {
                        Log.d("*************", "Can't convert line to JSON");
                    }

                    line = reader.readLine();
                    if (line != null)
                        Log.d("************getplayer", line);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return playersArrayList;
    }

    public void appendNewPlayer(String name, String surname, String pseudo) {

        checkOrCreateDataLocation();

        Writer output = null;
        try {
            output = new BufferedWriter(new FileWriter(filesDirPath + "/" + data_folder_name + "/" + data_file_name, true));  //clears file every time
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String playerInString = gson.toJson(new Player(name, surname, pseudo));
        try {
            output.append(playerInString + "\n");
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean addWinnings(ArrayList<String> pseudoList, ArrayList<Integer> winningList) {

        ArrayList<Player> playersToModify = new ArrayList<>();
        ArrayList<Player> oldPlayers = getPlayersArrayList();
        for (int i = 0; i < oldPlayers.size(); i++) {
            for (int j = 0; j < pseudoList.size(); j++) {
                if (oldPlayers.get(i).getPseudo().equals(pseudoList.get(j))) {
                    oldPlayers.get(i).addWinning(winningList.get(j));
                    playersToModify.add(oldPlayers.get(i));
                }
            }
        }
        return modifyPlayers(oldPlayers);


    }


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean modifyPlayers(ArrayList<Player> playersToModify) {
        checkOrCreateDataLocation();

        List<String> fileContent = null;
        Path path = Paths.get(filesDirPath + "/" + data_folder_name + "/" + data_file_name);
        try {
            fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Gson gson = new Gson();

        for (int i = 0; i < fileContent.size(); i++) {
            Player p = gson.fromJson(fileContent.get(i), Player.class);
            for (int j = 0; j < playersToModify.size(); j++) {
                if (p.getPseudo().equals(playersToModify.get(j).getPseudo())) {
                    fileContent.set(i, gson.toJson(playersToModify.get(j)));
                }
            }
        }

        try {
            Files.write(path, fileContent, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void checkOrCreateDataLocation() {

        String dataFolderPath = filesDirPath + "/" + data_folder_name;
        File folder = new File(dataFolderPath);
        if (!folder.exists()) {
            new File(dataFolderPath).mkdir();
        } else {
            File file = new File(dataFolderPath + "/" + data_file_name);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
