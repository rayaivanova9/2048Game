import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class board {
    private JPanel board;
    private JLabel scoreLabel;
    private JLabel scoreValue;
    private JLabel title;
    private JButton viewHighScoresButton;
    private JButton startGame;
    private JButton logInButton;
    private JLabel nameField;
    private JLabel highScoreLabel;
    private String username = "";
    private GamePanel gamePanel;

    public void updateScore() {
        if (scoreValue != null && gamePanel != null) {
            scoreValue.setText(String.valueOf(gamePanel.getScore()));
        }
    }

    public static void setNewHighScore(int score) {
        GameFrame.board.highScoreLabel.setText(String.valueOf(score));
    }

    public void startNewGame() {
        gamePanel.resetBoard();
        gamePanel.spawn();
        gamePanel.spawn();
        gamePanel.repaint();
        scoreValue.setText("0"); // reset score
        gamePanel.requestFocusInWindow();// regain key focus
        gamePanel.resetScore();
    }


    public void drawTiles (Graphics g, int[][] mat) {

        Graphics2D g2 = (Graphics2D) g;
        GamePanel GamePanel = new GamePanel();

        Tile Tile = new Tile();
        mat = GamePanel.mat;

        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                if(mat[i][j] == 0){
                    continue;
                } else {
                    g2.setColor(Color.blue);
                    int x = i*100 + (i+1)*10;
                    int y = j*100 + (j+1)*10;
                    g2.fillRoundRect( x, y, 100, 100, 10, 10 );
                    g2.setColor( Color.black );
                    g.drawString( "" + Tile.getValue(), x + 50 - 3, y + 50 );
                }
            }
        }
    }

    public board(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gamePanel.setScoreLabel(scoreValue);
        board.setBackground(new Color(0xE6D7CD));
        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
        startGame.setFocusable(false);
        username = Main.form.getUsername();
        Main.form.dispose();
        logIn(username);
        nameField.setText(username);
        highScoreLabel.setText(String.valueOf(connect.getHighScore(username)));

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.resetBoard();
                gamePanel.spawn();
                gamePanel.repaint();
                scoreValue.setText("0"); // reset score
                gamePanel.requestFocusInWindow();// regain key focus
                gamePanel.resetScore();
                //startGame.setEnabled(false);
            }
        });

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter your username:");
                if (username != null) {
                    logIn(username);
                    nameField.setText(username);
                    gamePanel.resetBoard();
                    gamePanel.spawn();
                    gamePanel.repaint();
                    scoreValue.setText("0"); // reset score
                    gamePanel.requestFocusInWindow();// regain key focus
                    gamePanel.resetScore();
                    highScoreLabel.setText(String.valueOf(connect.getHighScore(username)));
                }
            }
        });

        viewHighScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(highScores::new);
            }
        });
    }

    public JPanel getBoard() {
        return board;
    }

    public static void logIn(String username) {
        String nameQuery = "SELECT * FROM high_scores WHERE username LIKE ?";

        if (!username.isEmpty()) {
            if (connect.getRowCount(nameQuery, username) == -1) {
                System.out.println("SQL Error.");
            } else if (connect.getRowCount(nameQuery, username) == 0) {
                String newUsernameQuery = "INSERT INTO high_scores (username, score) VALUES (?, 0)";
                connect.getName(newUsernameQuery, username);
            } else {

            }
        } else {

        }
    }

    public String getUsername() {
        return username;
    }
}
