package com.game;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.github.cliftonlabs.json_simple.JsonObject;

public class Methods{
    static String[] vector;
    static String[] userLine;
    static ArrayList<String> log;

    private static String splitClient(String line){
        userLine = line.split("ClientUserinfoChanged: ");
        String[] player =  userLine[userLine.length -1].split("");
        return player[0];
    }

    private static void includePlayerNumberArray(Integer playerNumber){
        if (!App.playersNumber.contains(playerNumber)){
            App.playersNumber.add(playerNumber);
        }   
    }

    private static void includePlayerNameArray(String name){
        if (!name.isEmpty()){
            if(App.playersName.isEmpty()){
                App.playersName.add(name);
                App.playerKills.put(name, 0);
            }else if (!App.playersName.contains(name)){
                App.playersName.add(name);
                App.playerKills.put(name, 0);
            }
        }
    }

    private static String getName(){
        String name = userLine[userLine.length -1];
        String[] userName = name.split("n\\\\|\\\\t");
        if(!(userName.length <= 1)){
            return userName[1];
        }
        return null;
    }

    private static String splitKillLine(String line){
        String[] status = line.split(":");
        if(!(status.length <= 2)){
            status = status[2].split(" ");
            return status[2];
        }
        return null;
    }

    private static void scoreKill(Integer playerStatus, boolean status){
        int index = App.playersNumber.indexOf(playerStatus);
        String name = App.playersName.get(index);
        int value = (status == true) ? 1 : -1;
        int previousScore = (int)  App.playerKills.get(name); 
        int less_value = previousScore + value;
        App.playerKills.put(name, less_value);
    }

    private static void weaponCountKill(String weapon){
        if (!App.weapons.contains(weapon)){
            App.weapons.add(weapon);
            App.killsByMeans.put(weapon, 1);
        }else{
            int indexWeapon = App.weapons.indexOf(weapon);
            String nameWeapons = App.weapons.get(indexWeapon);
            int previousValue = (int) App.killsByMeans.get(nameWeapons);
            App.killsByMeans.put(nameWeapons, previousValue + 1);
        }
    }

   public static ArrayList<String> splitLogGame(){
        String path = "./log/quake.log";
        try {
            String[] logGames = Files.readString(Paths.get(path)).split("InitGame:");
            log = new ArrayList<>(Arrays.asList(logGames));
            return log;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] splitLine(int i){
        return log.get(i).split("\n");
    }

    public static void gameName(int i){
        String gameName = "game_" + Integer.toString(i);
        App.newJson = new JsonObject();
        App.json.put(gameName, App.newJson);
    }

    public static void gameLogicFilters(String[] gameBlock){
        for(String line : gameBlock){
            if(line.contains("ClientUserinfoChanged:")){
                playerName(line);               
            }else if(line.contains("Kill:") && line.contains("<world>")){
                killPlayer(false, line); 
            }else if(line.contains("Kill:") && !line.contains("<world>")){
                killPlayer(true, line);  
            }
        }
    }

    public static void playerName(String line){
        String playerNumber = splitClient(line);
        if(!playerNumber.isEmpty()){
            includePlayerNumberArray(Integer.parseInt(playerNumber));
            String name = getName();
            includePlayerNameArray(name);
        }
    }

    public static void killPlayer(boolean status, String line){
        String playerStatus = splitKillLine(line);
        String[] weaponLine = line.split(" ");
        String weapon = weaponLine[weaponLine.length -1];
        if(!playerStatus.isEmpty()){
            scoreKill(Integer.parseInt(playerStatus), status);
            App.totalKills += 1;
            weaponCountKill(weapon);
        }
    }

    public static void jsonStructure(JsonObject json){
        json.put("total_kills", App.totalKills);
        json.put("players", App.playersName);
        json.put("kills", App.playerKills);
        json.put("kills_by_means", App.killsByMeans);
    }

    public static void createJsonFile() throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // organazingJsonOerde();
        String result = gson.toJson(App.json);
        FileWriter fileWriter = new FileWriter("./results/result.json");
        fileWriter.write(result);
        fileWriter.close();
    }
}