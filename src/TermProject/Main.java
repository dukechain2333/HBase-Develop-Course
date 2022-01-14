package TermProject;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;


public class Main {
    public static void main(String[] args) throws IOException {
        CreateTable createTable = new CreateTable();
        createTable.createPlayerInfo();
        createTable.createGameInfo();
        createTable.createPlayingInfo();
        createTable.createUsingInfo();
        createTable.closeConn();
        Game game = new Game();
        game.showLogin();

        String userId = "test1";
        String searchDate = "2022-01-14";
        List<Cell> cells = checkStatus(userId);
        int times = calculateStatus(cells, searchDate);
        System.out.println("userId: " + userId + " played " + times + " times of games on " + searchDate);

        AdjustData adjustData = new AdjustData();
        adjustData.putData("UsingInfo", userId, "PlayingStatus", "PlayTimes", String.valueOf(times));

    }

    public static List<Cell> checkStatus(String userId) throws IOException {
        Configuration config = HBaseConfiguration.create();
        HTable table = new HTable(config, "PlayingInfo");
        Get get = new Get(Bytes.toBytes(userId));
        get.setMaxVersions();
        get.addColumn("PlayingGames".getBytes(), "gameId".getBytes());
        Result result = table.get(get);
        List<Cell> cells = result.listCells();

        return cells;
    }

    public static int calculateStatus(List<Cell> cells, String date) throws IOException {
        int times = 0;

        try {
            for (Cell ce : cells) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                String statusDate = format.format(ce.getTimestamp());
                if (date.equals(statusDate)) {
                    times++;
                }
            }
        } catch (NullPointerException e) {
            return times;
        }

        return times;
    }
}
