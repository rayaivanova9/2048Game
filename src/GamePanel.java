import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;


public class GamePanel extends JPanel {
    private final int TILE_SIZE = 100;
    private final int GAP = 10;
    private final int TILE_COUNT = 4;
    private final int HEIGHT = GAP + (TILE_SIZE + GAP)*TILE_COUNT;
    private final int WIDTH = GAP + (TILE_SIZE + GAP)*TILE_COUNT;
    Tile Tile = new Tile();
    public int[][] mat = new int[4][4];
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(0xbbada0));
    }

    public int score;
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0xcdc1b4));

        for (int i = 0; i < TILE_COUNT; i++) {
            for (int j = 0; j < TILE_COUNT; j++) {
                int x = GAP + j * (TILE_SIZE + GAP);
                int y = GAP + i * (TILE_SIZE + GAP);
                g2d.setColor(new Color(0xcdc1b4));
                g2d.fillRoundRect(x, y, TILE_SIZE, TILE_SIZE, 10, 10);
                int value = mat[i][j];
                if (value != 0){
                    g2d.setColor(Color.black);
                    g2d.setFont(new Font("Arial", Font.BOLD, 24));
                    String text = String.valueOf(value);
                    FontMetrics fm = g2d.getFontMetrics();
                    int numX = x + (TILE_SIZE - fm.stringWidth(text)) / 2;
                    int numY = y + (TILE_SIZE + fm.getAscent()) / 2 - 8;
                    g2d.drawString(text, numX, numY);
                }
            }
        }
    }
    public void spawn() {
        int tries = 30;
        Random rand = new Random();
        int numOfTiles = rand.nextInt(2) + 1;
        for (int j = 0; j < numOfTiles; j++){
            for(int i =0; i<tries; i++){
                if(mat[rand.nextInt(4)][rand.nextInt(4)] == 0){
                    mat[rand.nextInt(4)][rand.nextInt(4)] = Tile.getValue();
                    break;
                } else {
                    tries++;
                }
            }
        }

    }
    public void resetBoard(){
        for (int i = 0; i < TILE_COUNT; i++) {
            for (int j = 0; j < TILE_COUNT; j++) {
                mat[i][j] = 0;
            }
        }
    }
    public void shiftUp(){
        for(int i = 0; i < 4; i++){
            int border = 0;
            for(int j = 0; j < 4; j++){
                if(mat[i][j] != 0){
                    int initial = mat[border][j];
                    int compare = mat[i][j];
                    if(initial == 0 || initial == compare && i >= border){
                        int addScore = initial + compare;
                        score += addScore;
                        initial = addScore;
                        compare = 0;
                    }
                } else {
                    border ++;
                }
            }
        }
    }
    public void shiftDown(){
        for(int i = 0; i < 4; i++){
            int border = 3;
            for(int j = 0; j < 4; j++){
                if(mat[i][j] != 0){
                    int initial = mat[border][j];
                    int compare = mat[i][j];
                    if(initial == 0 || initial == compare && i <= border){
                        int addScore = initial + compare;
                        score += addScore;
                        initial = addScore;
                        compare = 0;
                    }
                } else {
                    border --;
                }
            }
        }
    }
    public void shiftRight(){
        for(int i = 0; i < 4; i++){
            int border = 3;
            for(int j = 0; j < 4; j++){
                if(mat[i][j] != 0){
                    int initial = mat[i][border];
                    int compare = mat[i][j];
                    if(initial == 0 || initial == compare && j <= border){
                        int addScore = initial + compare;
                        score += addScore;
                        initial = addScore;
                        compare = 0;
                    }
                } else {
                    border --;
                }
            }
        }
    }
    public void shiftLeft(){
        for(int i = 0; i < 4; i++){
            int border = 0;
            for(int j = 0; j < 4; j++){
                if(mat[i][j] != 0){
                    int initial = mat[i][border];
                    int compare = mat[i][j];
                    if(initial == 0 || initial == compare && j >= border){
                        int addScore = initial + compare;
                        score += addScore;
                        initial = addScore;
                        compare = 0;
                    }
                } else {
                    border ++;
                }
            }
        }
    }

    public void keyPressed(KeyEvent keyEvent){
        int key = keyEvent.getKeyCode();

        if (keyEvent.getKeyChar() == 'w' || key == KeyEvent.VK_UP) {
            shiftUp();
        } else if (keyEvent.getKeyChar() == 'a' || key == KeyEvent.VK_LEFT) {
            shiftLeft();
        } else if (keyEvent.getKeyChar() == 's' || key == KeyEvent.VK_DOWN) {
            shiftDown();
        } else if (keyEvent.getKeyChar() == 'd' || key == KeyEvent.VK_RIGHT) {
            shiftRight();
        }
    }

}
