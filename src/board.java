import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class board {
    private JPanel board;
    private JLabel scoreLabel;
    private JLabel scoreValue;
    private JLabel title;
    private JButton viewHighScoresButton;
    private JButton startGame;

    private GamePanel gamePanel;
    public void updateScore() {
        if (scoreValue != null && gamePanel != null) {
            scoreValue.setText(String.valueOf(gamePanel.getScore()));
        }
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
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.resetBoard();
                gamePanel.spawn();
                gamePanel.repaint();
                scoreValue.setText("0"); // reset score
                gamePanel.requestFocusInWindow(); // regain key focus
                //startGame.setEnabled(false);


            }




        });
    }

    public JPanel getBoard() {
        return board;
    }
}
