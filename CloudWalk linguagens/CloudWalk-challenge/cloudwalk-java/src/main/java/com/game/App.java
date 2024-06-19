package com.game;

import java.io.IOException;
import java.util.ArrayList;

import com.github.cliftonlabs.json_simple.JsonObject;


public class App extends Methods {
    static JsonObject json = new JsonObject();
    static JsonObject newJson;
    static JsonObject playerKills;
    static JsonObject killsByMeans;
    static ArrayList<String> game = new ArrayList<String>();
    static ArrayList<String> playersName;
    static ArrayList<Integer> playersNumber;
    static ArrayList<String> weapons;
    static int totalKills;
    public static void main(String[] args) throws IOException{
        game = splitLogGame();
        for(int i = 1; i < game.size(); i++){
            String [] game_block = splitLine(i);
            gameName(i);

            playerKills = new JsonObject();
            killsByMeans = new JsonObject();
            playersName = new ArrayList<String>();
            playersNumber = new ArrayList<Integer>();
            weapons = new ArrayList<String>();
            totalKills = 0;

            gameLogicFilters(game_block);
            jsonStructure(newJson);
        }
        createJsonFile();
    }
}
