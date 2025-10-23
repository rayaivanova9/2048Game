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
    private Runnable onGameOver;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(0xbbada0));
        setFocusable(true);
        requestFocusInWindow();
        setupKeyListener();

    }
    private JLabel scoreValue;

    private int score = 0;


    public void setBoard(board b) {
        this.boardRef = b;
    }


    public int getScore() {
        return score;
    }

    public void setOnGameOver(Runnable callback) {
        this.onGameOver = callback;
    }

    public void setScoreLabel(JLabel label) {
        this.scoreValue = label;
        if (scoreValue != null) scoreValue.setText(String.valueOf(score));
    }


    public void setColorText(Graphics g2d, int value) {
        if (value==2){
            g2d.setColor(new Color(0x776E65));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value==4){
            g2d.setColor(new Color(0x776E65));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value==8){
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value==16){
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value==32){
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value==64){
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value==128){
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
        } else if (value==256){
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
        } else if (value==512){
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
        } else if (value==1024){
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
        } else if (value==2048){
            g2d.setColor(new Color(0x776E65));
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
        } else {
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
        }

    }

    public void setColorTile(Graphics g2d, int value) {
        if (value==2){ g2d.setColor(new Color(0xEEE4DA));}
        else if (value==4){g2d.setColor(new Color(0xEDE0C8));}
        else if(value==8){g2d.setColor(new Color(0xF2B179));}
        else if(value==16){g2d.setColor(new Color(0xF59563));}
        else if(value==32){g2d.setColor(new Color(0xF67C5F));}
        else if(value==64){g2d.setColor(new Color(0xF65E3B));}
        else if(value==128){g2d.setColor(new Color(0xEDCF72));}
        else if(value==256){g2d.setColor(new Color(0xEDCC61));}
        else if(value==512){g2d.setColor(new Color(0xEDC850));}
        else if(value==1024){g2d.setColor(new Color(0xEDC53F));}
        else if(value==2048){g2d.setColor(new Color(0xEDC22E));}
        else {g2d.setColor(new Color(0xEDC22E));}
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

                int value = mat[i][j];
                if (value != 0){
                    setColorTile(g2d, value);
                    g2d.fillRoundRect(x, y, TILE_SIZE, TILE_SIZE, 10, 10);
                    setColorText(g2d, value);
                    FontMetrics fm = g2d.getFontMetrics();
                    String text = String.valueOf(value);
                    int numX = x + (TILE_SIZE - fm.stringWidth(text)) / 2;
                    int numY = y + (TILE_SIZE + fm.getAscent()) / 2 - 8;
                    g2d.drawString(text, numX, numY);
                } else {
                    g2d.setColor(new Color(0xcdc1b4));
                    g2d.fillRoundRect(x, y, TILE_SIZE, TILE_SIZE, 10, 10);
                }
            }
        }
    }

    private int[][] copyMatrix(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    private boolean areDifferent(int[][] a, int[][] b) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (a[i][j] != b[i][j]) return true;
            }
        }
        return false;
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
        score = 0;
        if (scoreValue != null) scoreValue.setText("0");

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



    private void setupKeyListener() {
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                int[][] before = copyMatrix(mat);

                if (keyEvent.getKeyChar() == 'w' || key == KeyEvent.VK_UP) {
                    shiftUp();
                } else if (keyEvent.getKeyChar() == 'a' || key == KeyEvent.VK_LEFT) {
                    shiftLeft();
                } else if (keyEvent.getKeyChar() == 's' || key == KeyEvent.VK_DOWN) {
                    shiftDown();
                } else if (keyEvent.getKeyChar() == 'd' || key == KeyEvent.VK_RIGHT) {
                    shiftRight();
                }

                if (areDifferent(before, mat)) {
                    spawn();
                    repaint();
                    if (boardRef != null) boardRef.updateScore();
                    if (isGameOver()) {
                        JOptionPane.showMessageDialog(GamePanel.this, "Game Over! Final score: " + score);
                        if (onGameOver != null) {
                            onGameOver.run(); // re-enable Start Game button
                        }
                    }
                }
            }
        });
    }
    private boolean isGameOver() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mat[i][j] == 0) return false;
            }
        }


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int current = mat[i][j];
                if (i < 3 && mat[i + 1][j] == current) return false;
                if (j < 3 && mat[i][j + 1] == current) return false;
            }
        }

        return true;
    }





}
