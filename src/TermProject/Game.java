package TermProject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class Game {
    public void showLogin() {
        JFrame frame = new JFrame("191650126 Login");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);


        JButton login_as_user = new JButton("login as User");
        login_as_user.setBounds(10, 80, 150, 25);
        login_as_user.addActionListener((actionEvent -> {
            try {
                showUserInfo(userText.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            frame.dispose();
        }));
        panel.add(login_as_user);

        JButton login_as_game = new JButton("login as Game");
        login_as_game.setBounds(10, 110, 150, 25);
        login_as_game.addActionListener((actionEvent -> {
            try {
                showGameInfo(userText.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            frame.dispose();
        }));
        panel.add(login_as_game);

        frame.setVisible(true);
    }

    private void showUserInfo(String userId) throws IOException {
        JFrame frame = new JFrame("191650126 User Info");
        frame.setSize(1000, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gbaglayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        frame.setLayout(gbaglayout);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;


        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1000, 50);
        gbaglayout.setConstraints(panel, constraints);
        frame.add(panel);

        JLabel userLabel = new JLabel("Hello " + userId + "! These are the games you may get interested.");
        panel.add(userLabel);

        JPanel panel2 = new JPanel(new GridLayout(3, 3));
        gbaglayout.setConstraints(panel2, constraints);
        frame.add(panel2);

        int game = 0;
        for (int y = 100; y < 400; y += 100) {
            for (int x = 100; x < 900; x += 300) {
                game += 1;
                JPanel jPanel = new JPanel();
                JLabel label = new JLabel("Game" + game);
                JButton play = new JButton("Play");
                int finalGame = game;
                play.addActionListener(actionEvent -> {
                    try {
                        AdjustData adjustData = new AdjustData();
                        String rowKey = userId;
                        adjustData.putData("PlayingInfo", rowKey, "PlayingGames", "gameId", "Game" + finalGame);
                        adjustData.closeConn();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                JButton subscribe = new JButton("Subscribe");
                subscribe.addActionListener(actionEvent -> {
                    try {
                        AdjustData adjustData = new AdjustData();
                        String rowKey = userId;
                        adjustData.putData("PlayerInfo", userId, "Followings", "gameId", "Game" + finalGame);
                        adjustData.putData("GameInfo", "Game" + finalGame, "Followers", "userId", userId);
                        adjustData.closeConn();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                jPanel.add(label);
                jPanel.add(play);
                jPanel.add(subscribe);
                panel2.add(jPanel);
            }
        }
        JPanel panel3 = new JPanel(new GridLayout(3, 3));
        gbaglayout.setConstraints(panel3, constraints);
        frame.add(panel3);

        JLabel userLabel2 = new JLabel("Those are the games you are following.");
        panel3.add(userLabel2);

        JPanel panel4 = new JPanel(new GridLayout(3, 3));
        gbaglayout.setConstraints(panel4, constraints);
        frame.add(panel4);

        Configuration config = HBaseConfiguration.create();
        HTable table = new HTable(config, "PlayerInfo");
        Get get = new Get(Bytes.toBytes(userId));
        get.setMaxVersions();
        get.addColumn("Followings".getBytes(), "gameId".getBytes());
        Result result = table.get(get);
        java.util.List<Cell> cells = result.listCells();

        try {
            for (Cell ce : cells) {
                JLabel jLabel = new JLabel(Bytes.toString(ce.getValueArray(), ce.getValueOffset(), ce.getValueLength()));
                panel4.add(jLabel);
            }
        } catch (NullPointerException e) {
            System.out.println(" ");
        }

        frame.setVisible(true);
    }

    private void showGameInfo(String gameId) throws IOException {
        JFrame frame = new JFrame("191650126 Game Info");
        frame.setSize(1000, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gbaglayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        frame.setLayout(gbaglayout);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;

        JPanel panel = new JPanel();
        gbaglayout.setConstraints(panel, constraints);
        frame.add(panel);

        JLabel userLabel = new JLabel("Hello " + gameId + "! These are the followers of game:");
        panel.add(userLabel);

        Configuration config = HBaseConfiguration.create();
        HTable table = new HTable(config, "GameInfo");
        Get get = new Get(Bytes.toBytes(gameId));
        get.setMaxVersions();
        get.addColumn("Followers".getBytes(), "userId".getBytes());
        Result result = table.get(get);
        java.util.List<Cell> cells = result.listCells();

        JPanel panel2 = new JPanel(new GridLayout(3, 3));
        gbaglayout.setConstraints(panel2, constraints);
        frame.add(panel2);

        for (Cell ce : cells) {
            JLabel jLabel = new JLabel(Bytes.toString(ce.getValueArray(), ce.getValueOffset(), ce.getValueLength()));
            panel2.add(jLabel);
        }
        frame.setVisible(true);
    }
}
