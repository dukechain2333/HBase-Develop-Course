package TermProject;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CreateTable createTable = new CreateTable();
        createTable.createPlayerInfo();
        createTable.createGameInfo();
        createTable.createPlayingInfo();
        Game game = new Game();
        game.showLogin();
    }
}
