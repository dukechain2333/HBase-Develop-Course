package TermProject;

import javax.swing.*;

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
            showUserInfo(userText.getText());
            frame.dispose();
        }));
        panel.add(login_as_user);

        JButton login_as_game = new JButton("login as Game");
        login_as_game.setBounds(10, 110, 150, 25);
        login_as_game.addActionListener((actionEvent -> {
            showGameInfo(userText.getText());
            frame.dispose();
        }));
        panel.add(login_as_game);

        frame.setVisible(true);
    }

    private void showUserInfo(String userid) {
        JFrame frame = new JFrame("191650126 User Info");
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        
        frame.setVisible(true);
    }

    private void showGameInfo(String gameId) {
        JFrame frame = new JFrame("191650126 Game Info");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        frame.setVisible(true);
    }
}
