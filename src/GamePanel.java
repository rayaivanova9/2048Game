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
    private int[][] pendingMatrix = null;
    private Runnable onGameOver;
    private java.util.List<AnimatedTile> animations = new java.util.ArrayList<>();
    private java.util.List<Timer> activeAnimations = new java.util.ArrayList<>();
    private boolean isAnimating = false;
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

                g2d.setColor(new Color(0xcdc1b4));
                g2d.fillRoundRect(x, y, TILE_SIZE, TILE_SIZE, 10, 10);

                boolean skip = false;
                for (AnimatedTile anim : animations) {
                    int animRow = (int) ((anim.y - GAP) / (TILE_SIZE + GAP));
                    int animCol = (int) ((anim.x - GAP) / (TILE_SIZE + GAP));
                    if (animRow == i && animCol == j) {
                        skip = true;
                        break;
                    }
                }
                if (skip) continue;

                if (value != 0) {
                    setColorTile(g2d, value);
                    g2d.fillRoundRect(x, y, TILE_SIZE, TILE_SIZE, 10, 10);
                    setColorText(g2d, value);
                    String text = String.valueOf(value);
                    FontMetrics fm = g2d.getFontMetrics();
                    int numX = x + (TILE_SIZE - fm.stringWidth(text)) / 2;
                    int numY = y + (TILE_SIZE + fm.getAscent()) / 2 - 8;
                    g2d.drawString(text, numX, numY);
                }
            }
        }
        for (AnimatedTile anim : animations) {
            int size = (int) (TILE_SIZE * anim.scale);
            int offset = (TILE_SIZE - size) / 2;
            setColorTile(g2d, anim.value);
            g2d.fillRoundRect((int) anim.x + offset, (int) anim.y + offset, size, size, 10, 10);
            setColorText(g2d, anim.value);
            String text = String.valueOf(anim.value);
            FontMetrics fm = g2d.getFontMetrics();
            int numX = (int) anim.x + offset + (size - fm.stringWidth(text)) / 2;
            int numY = (int) anim.y + offset + (size + fm.getAscent()) / 2 - 8;
            g2d.drawString(text, numX, numY);
        }
    }


    private int[][] copyMatrix(int[][] original) {
        if (original == null) {
            return null;
        }
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
        spawn(null);
    }

    public void spawn(Runnable onComplete) {
        Random rand = new Random();
        int tries = 30;
        for (int i = 0; i < tries; i++) {
            int x = rand.nextInt(4);
            int y = rand.nextInt(4);
            if (mat[x][y] == 0) {
                int value = Tile.getValue();
                mat[x][y] = value;


                AnimatedTile anim = new AnimatedTile();
                anim.value = value;
                anim.x = GAP + y * (TILE_SIZE + GAP);
                anim.y = GAP + x * (TILE_SIZE + GAP);
                anim.scale = 0.1;
                animations.add(anim);

                animateSpawn(anim, onComplete);
                break;
            }
        }
    }
    private void animateSpawn(AnimatedTile anim, Runnable onComplete) {
        isAnimating = true;
        int steps = 10;
        double stepSize = (1.0 - anim.scale) / steps;
        Timer timer = new Timer(8, null);
        activeAnimations.add(timer);

        timer.addActionListener(e -> {
            anim.scale += stepSize;
            repaint();

            if (anim.scale >= 1.0) {
                ((Timer) e.getSource()).stop();
                animations.remove(anim);
                activeAnimations.remove(timer);
                isAnimating = false;
                repaint();

                if (onComplete != null) {
                    onComplete.run();
                }
            }
        });
        timer.start();
    }

    private void animateMerge(int row, int col, int value) {
        AnimatedTile anim = new AnimatedTile();
        anim.x = GAP + col * (TILE_SIZE + GAP);
        anim.y = GAP + row * (TILE_SIZE + GAP);
        anim.value = value;
        anim.scale = 1.0;
        animations.add(anim);

        int steps = 10;
        double maxScale = 1.2;
        double step = (maxScale - 1.0) / (steps / 2);
        boolean[] shrinking = {false};
        Timer timer = new Timer(8, null);
        activeAnimations.add(timer);

        timer.addActionListener(e -> {
            if (!shrinking[0]) {
                anim.scale += step;
                if (anim.scale >= maxScale) shrinking[0] = true;
            } else {
                anim.scale -= step;
                if (anim.scale <= 1.0) {
                    ((Timer) e.getSource()).stop();
                    activeAnimations.remove(timer);
                    animations.remove(anim);
                }
            }
            repaint();
        });
        timer.start();
    }
    private void animateShift(int fromRow, int fromCol, int toRow, int toCol, int value) {
        AnimatedTile anim = new AnimatedTile();
        anim.x = GAP + fromCol * (TILE_SIZE + GAP);
        anim.y = GAP + fromRow * (TILE_SIZE + GAP);
        anim.targetX = GAP + toCol * (TILE_SIZE + GAP);
        anim.targetY = GAP + toRow * (TILE_SIZE + GAP);
        anim.value = value;
        anim.scale = 1.0;
        animations.add(anim);

        int steps = 10;
        double stepX = (anim.targetX - anim.x) / steps;
        double stepY = (anim.targetY - anim.y) / steps;

        Timer timer = new Timer(15, null);
        activeAnimations.add(timer);

        final int[] currentStep = {0};
        timer.addActionListener(e -> {
            currentStep[0]++;
            anim.x += stepX;
            anim.y += stepY;
            repaint();

            if (currentStep[0] >= steps) {
                anim.x = anim.targetX;
                anim.y = anim.targetY;
                ((Timer) e.getSource()).stop();
                activeAnimations.remove(timer);
            }
        });
        timer.start();
    }

    private void waitForShiftAnimations(Runnable callback) {
        if (activeAnimations.isEmpty()) {
            callback.run();
            return;
        }

        Timer checkTimer = new Timer(20, null);
        checkTimer.addActionListener(e -> {
            if (activeAnimations.isEmpty()) {
                ((Timer) e.getSource()).stop();
                callback.run();
            }
        });
        checkTimer.start();
    }

    private void animateMerges() {
        animations.clear();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mat[i][j] != 0) {
                    // Check if this was a merged tile (you'd need to track this during shift)
                    // For now, we'll skip this or implement merge tracking
                }
            }
        }
        repaint();

        // Spawn new tile after merge animations complete, then check game over
        spawn(() -> {
            if (isGameOver()) {
                JOptionPane.showMessageDialog(GamePanel.this, "Game Over! Final score: " + score);
                if (onGameOver != null) {
                    onGameOver.run();
                }
            }
        });
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
        // First, create animations for tiles that will move
        for (int j = 0; j < 4; j++) {
            int border = 0;
            int lastValue = 0;

            for (int i = 0; i < 4; i++) {
                int current = mat[i][j];
                if (current == 0) continue;

                if (lastValue == 0) {
                    // This tile will move to border position
                    if (i != border) {
                        animateShift(i, j, border, j, current);
                    }
                    lastValue = current;
                } else if (lastValue == current) {
                    // This tile will merge with previous
                    animateShift(i, j, border, j, current);
                    int mergedValue = lastValue * 2;
                    score += mergedValue;
                    lastValue = 0;
                    border++;
                } else {
                    // Previous tile is placed, this one will move
                    border++;
                    if (i != border) {
                        animateShift(i, j, border, j, current);
                    }
                    lastValue = current;
                }
            }
        }

        // Calculate the new matrix state but don't apply it yet
        pendingMatrix = new int[4][4];
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
                    int mergedValue = lastValue * 2;
                    newCol[border++] = mergedValue;
                    lastValue = 0;
                } else {
                    newCol[border++] = lastValue;
                    lastValue = current;
                }
            }

            if (lastValue != 0) newCol[border] = lastValue;

            for (int i = 0; i < 4; i++) {
                pendingMatrix[i][j] = newCol[i];
            }
        }

        if (scoreValue != null) {
            scoreValue.setText(String.valueOf(score));
        }

        waitForShiftAnimations(() -> {
            mat = pendingMatrix;
            pendingMatrix = null;
            animateMerges();
        });
    }
    public void shiftDown() {
        // First, create animations for tiles that will move
        for (int j = 0; j < 4; j++) {
            int border = 3;
            int lastValue = 0;

            for (int i = 3; i >= 0; i--) {
                int current = mat[i][j];
                if (current == 0) continue;

                if (lastValue == 0) {
                    if (i != border) {
                        animateShift(i, j, border, j, current);
                    }
                    lastValue = current;
                } else if (lastValue == current) {
                    animateShift(i, j, border, j, current);
                    int mergedValue = lastValue * 2;
                    score += mergedValue;
                    lastValue = 0;
                    border--;
                } else {
                    border--;
                    if (i != border) {
                        animateShift(i, j, border, j, current);
                    }
                    lastValue = current;
                }
            }
        }

        // Calculate the new matrix state but don't apply it yet
        pendingMatrix = new int[4][4];
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
                    int mergedValue = lastValue * 2;
                    newCol[border--] = mergedValue;
                    lastValue = 0;
                } else {
                    newCol[border--] = lastValue;
                    lastValue = current;
                }
            }

            if (lastValue != 0) newCol[border] = lastValue;

            for (int i = 0; i < 4; i++) {
                pendingMatrix[i][j] = newCol[i];
            }
        }

        if (scoreValue != null) {
            scoreValue.setText(String.valueOf(score));
        }

        waitForShiftAnimations(() -> {
            mat = pendingMatrix;
            pendingMatrix = null;
            animateMerges();
        });
    }
    public void shiftRight() {
        // First, create animations for tiles that will move
        for (int i = 0; i < 4; i++) {
            int border = 3;
            int lastValue = 0;

            for (int j = 3; j >= 0; j--) {
                int current = mat[i][j];
                if (current == 0) continue;

                if (lastValue == 0) {
                    if (j != border) {
                        animateShift(i, j, i, border, current);
                    }
                    lastValue = current;
                } else if (lastValue == current) {
                    animateShift(i, j, i, border, current);
                    int mergedValue = lastValue * 2;
                    score += mergedValue;
                    lastValue = 0;
                    border--;
                } else {
                    border--;
                    if (j != border) {
                        animateShift(i, j, i, border, current);
                    }
                    lastValue = current;
                }
            }
        }

        // Calculate the new matrix state but don't apply it yet
        pendingMatrix = new int[4][4];
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
                    int mergedValue = lastValue * 2;
                    newRow[border--] = mergedValue;
                    lastValue = 0;
                } else {
                    newRow[border--] = lastValue;
                    lastValue = current;
                }
            }

            if (lastValue != 0) newRow[border] = lastValue;
            pendingMatrix[i] = newRow;
        }

        if (scoreValue != null) {
            scoreValue.setText(String.valueOf(score));
        }

        waitForShiftAnimations(() -> {
            mat = pendingMatrix;
            pendingMatrix = null;
            animateMerges();
        });
    }
    public void shiftLeft() {
        int[][] oldMatrix = copyMatrix(mat);

        // First, create animations for tiles that will move
        for (int i = 0; i < 4; i++) {
            int border = 0;
            int lastValue = 0;

            for (int j = 0; j < 4; j++) {
                int current = mat[i][j];
                if (current == 0) continue;

                if (lastValue == 0) {
                    if (j != border) {
                        animateShift(i, j, i, border, current);
                    }
                    lastValue = current;
                } else if (lastValue == current) {
                    animateShift(i, j, i, border, current);
                    int mergedValue = lastValue * 2;
                    score += mergedValue;
                    lastValue = 0;
                    border++;
                } else {
                    border++;
                    if (j != border) {
                        animateShift(i, j, i, border, current);
                    }
                    lastValue = current;
                }
            }
        }

        // Calculate the new matrix state but don't apply it yet
        pendingMatrix = new int[4][4];
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
                    int mergedValue = lastValue * 2;
                    newRow[border++] = mergedValue;
                    lastValue = 0;
                } else {
                    newRow[border++] = lastValue;
                    lastValue = current;
                }
            }

            if (lastValue != 0) newRow[border] = lastValue;
            pendingMatrix[i] = newRow;
        }

        if (scoreValue != null) {
            scoreValue.setText(String.valueOf(score));
        }

        waitForShiftAnimations(() -> {
            // Apply the matrix change after shift animations complete
            mat = pendingMatrix;
            pendingMatrix = null;
            animateMerges();
        });
    }
    private board boardRef;



    private void setupKeyListener() {
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                // Guard against null mat - game not initialized yet
                if (mat == null) {
                    return;
                }

                int key = keyEvent.getKeyCode();
                int[][] before = copyMatrix(mat);
                for (Timer t : activeAnimations) {
                    t.stop();
                }
                activeAnimations.clear();
                animations.clear();
                isAnimating = false;

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
                    // Spawn and game over check now handled in animation chain
                    repaint();
                    if (boardRef != null) boardRef.updateScore();
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
    private class AnimatedTile {
        double x, y;       // current position (in pixels)
        double targetX, targetY;
        double scale = 1.0;
        int value;
    }




}