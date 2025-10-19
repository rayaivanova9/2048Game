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

    private GamePanel gamePanel;

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
        board.setBackground(new Color(0xE6D7CD));

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.resetBoard();
                gamePanel.spawn();
                gamePanel.repaint();

            }
        });
    }

    public JPanel getBoard() {
        return board;
    }
}
