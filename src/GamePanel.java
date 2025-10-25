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
        setFocusable(true);
        requestFocusInWindow();
        setupKeyListener();

    }
    private JLabel scoreValue;

    private int score = 0;

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }

    public void setScoreLabel(JLabel label) {
        this.scoreValue = label;
        if (scoreValue != null) scoreValue.setText(String.valueOf(score));
    }
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
        Random rand = new Random();
        int tries = 30;
        for (int i = 0; i < tries; i++) {
            int x = rand.nextInt(4);
            int y = rand.nextInt(4);
            if (mat[x][y] == 0) {
                mat[x][y] = Tile.getValue();
                break;
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
    public void shiftUp() {
        for (int j = 0; j < 4; j++) {
            int border = 0;
            int lastValue = 0;
            int[] newCol = new int[4];

            for (int i = 0; i < 4; i++) {
                int current = mat[i][j];
                if (current == 0) continue;

                if (lastValue == 0) {
                    lastValue = current;
                } else if (lastValue == current) {
                    newCol[border++] = lastValue * 2;
                    score += lastValue * 2;
                    lastValue = 0;
                } else {
                    newCol[border++] = lastValue;
                    lastValue = current;
                }
            }

            if (lastValue != 0) newCol[border] = lastValue;

            for (int i = 0; i < 4; i++) {
                mat[i][j] = newCol[i];
            }
        }
        if (scoreValue != null) {
            scoreValue.setText(String.valueOf(score));
            connect.highScore(Integer.parseInt(scoreValue.getText()));
        }
    }
    public void shiftDown() {
        for (int j = 0; j < 4; j++) {
            int border = 3;
            int lastValue = 0;
            int[] newCol = new int[4];

            for (int i = 3; i >= 0; i--) {
                int current = mat[i][j];
                if (current == 0) continue;

                if (lastValue == 0) {
                    lastValue = current;
                } else if (lastValue == current) {
                    newCol[border--] = lastValue * 2;
                    score += lastValue * 2;
                    lastValue = 0;
                } else {
                    newCol[border--] = lastValue;
                    lastValue = current;
                }
            }

            if (lastValue != 0) newCol[border] = lastValue;

            for (int i = 0; i < 4; i++) {
                mat[i][j] = newCol[i];
            }
        }
        if (scoreValue != null) {
            scoreValue.setText(String.valueOf(score));
            connect.highScore(Integer.parseInt(scoreValue.getText()));
        }
    }
    public void shiftRight() {
        for (int i = 0; i < 4; i++) {
            int border = 3;
            int lastValue = 0;
            int[] newRow = new int[4];

            for (int j = 3; j >= 0; j--) {
                int current = mat[i][j];
                if (current == 0) continue;

                if (lastValue == 0) {
                    lastValue = current;
                } else if (lastValue == current) {
                    newRow[border--] = lastValue * 2;
                    score += lastValue * 2;
                    lastValue = 0;
                } else {
                    newRow[border--] = lastValue;
                    lastValue = current;
                }
            }

            if (lastValue != 0) newRow[border] = lastValue;
            mat[i] = newRow;
        }
        if (scoreValue != null) {
            scoreValue.setText(String.valueOf(score));
            connect.highScore(Integer.parseInt(scoreValue.getText()));
        }
    }
    public void shiftLeft() {
        for (int i = 0; i < 4; i++) {
            int border = 0;
            int lastValue = 0;
            int[] newRow = new int[4];

            for (int j = 0; j < 4; j++) {
                int current = mat[i][j];
                if (current == 0) continue;

                if (lastValue == 0) {
                    lastValue = current;
                } else if (lastValue == current) {
                    newRow[border++] = lastValue * 2;
                    score += lastValue * 2;
                    lastValue = 0;
                } else {
                    newRow[border++] = lastValue;
                    lastValue = current;
                }
            }

            if (lastValue != 0) newRow[border] = lastValue;
            mat[i] = newRow;
        }
        if (scoreValue != null) {
            scoreValue.setText(String.valueOf(score));
            connect.highScore(Integer.parseInt(scoreValue.getText()));
        }
    }
    private void test(){
        for(int j = 0; j < 4; j++){
            System.out.println();
            for(int i = 0; i < 4; i++){
                System.out.print(mat[j][i] + " ");
            }
        }
    }
    private board boardRef;

    public void setBoard(board b) {
        this.boardRef = b;
    }
    private void setupKeyListener() {
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent){
                int key = keyEvent.getKeyCode();
                if (keyEvent.getKeyChar() == 'w' || key == KeyEvent.VK_UP) {

                    shiftUp();
                    spawn();
                    repaint();
                    test();
                    if (boardRef != null) boardRef.updateScore();
                    System.out.println("pressed up");
                } else if (keyEvent.getKeyChar() == 'a' || key == KeyEvent.VK_LEFT) {
                    shiftLeft();
                    spawn();
                    repaint();
                    test();
                    if (boardRef != null) boardRef.updateScore();
                    System.out.println("pressed left");
                } else if (keyEvent.getKeyChar() == 's' || key == KeyEvent.VK_DOWN) {
                    shiftDown();
                    spawn();
                    repaint();
                    test();
                    if (boardRef != null) boardRef.updateScore();
                    System.out.println("pressed down");
                } else if (keyEvent.getKeyChar() == 'd' || key == KeyEvent.VK_RIGHT) {
                    shiftRight();
                    spawn();
                    repaint();
                    test();
                    if (boardRef != null) boardRef.updateScore();
                    System.out.println("pressed right");
                }
            }
        });

    }




}
