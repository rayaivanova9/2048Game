import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel {

    // ==================== CONSTANTS ====================
    private final int TILE_SIZE = 100;
    private final int GAP = 10;
    private final int TILE_COUNT = 4;
    private final int HEIGHT = GAP + (TILE_SIZE + GAP) * TILE_COUNT;
    private final int WIDTH = GAP + (TILE_SIZE + GAP) * TILE_COUNT;

    // ==================== FIELDS ====================
    public int[][] mat = new int[4][4];
    private int[][] pendingMatrix = null;
    private int score = 0;
    private boolean isAnimating = false;
    private Tile Tile = new Tile();
    private JLabel scoreValue;
    private board boardRef;
    private Runnable onGameOver;

    private java.util.List<AnimatedTile> animations = new java.util.ArrayList<>();
    private java.util.List<Timer> activeAnimations = new java.util.ArrayList<>();

    // ==================== CONSTRUCTOR ====================
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(0xbbada0));
        setFocusable(true);
        requestFocusInWindow();
        setupKeyListener();
    }

    // ==================== GETTERS & SETTERS ====================
    public int getScore() {
        return score;
    }

    public void setBoard(board b) {
        this.boardRef = b;
    }

    public void setOnGameOver(Runnable callback) {
        this.onGameOver = callback;
    }

    public void setScoreLabel(JLabel label) {
        this.scoreValue = label;
        if (scoreValue != null) scoreValue.setText(String.valueOf(score));
    }

    // ==================== RENDERING METHODS ====================
    @Override
    public void paint(Graphics graphics) {
        super.paintComponent(graphics);


        if (mat == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(new Color(0xcdc1b4));


        for (int i = 0; i < TILE_COUNT; i++) {
            for (int j = 0; j < TILE_COUNT; j++) {
                int x = GAP + j * (TILE_SIZE + GAP);
                int y = GAP + i * (TILE_SIZE + GAP);
                int value = mat[i][j];


                g2d.setColor(new Color(0xcdc1b4));
                g2d.fillRoundRect(x, y, TILE_SIZE, TILE_SIZE, 10, 10);

                // Skip if this tile is being animated
                boolean skip = false;
                for (AnimatedTile anim : animations) {
                    if (anim.sourceRow == i && anim.sourceCol == j) {
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

        //draw animated tiles
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

    public void setColorTile(Graphics g2d, int value) {
        if (value == 2) { g2d.setColor(new Color(0xEEE4DA)); }
        else if (value == 4) { g2d.setColor(new Color(0xEDE0C8)); }
        else if (value == 8) { g2d.setColor(new Color(0xF2B179)); }
        else if (value == 16) { g2d.setColor(new Color(0xF59563)); }
        else if (value == 32) { g2d.setColor(new Color(0xF67C5F)); }
        else if (value == 64) { g2d.setColor(new Color(0xF65E3B)); }
        else if (value == 128) { g2d.setColor(new Color(0xEDCF72)); }
        else if (value == 256) { g2d.setColor(new Color(0xEDCC61)); }
        else if (value == 512) { g2d.setColor(new Color(0xEDC850)); }
        else if (value == 1024) { g2d.setColor(new Color(0xEDC53F)); }
        else if (value == 2048) { g2d.setColor(new Color(0xEDC22E)); }
        else { g2d.setColor(new Color(0xEDC22E)); }
    }

    public void setColorText(Graphics g2d, int value) {
        if (value == 2) {
            g2d.setColor(new Color(0x776E65));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value == 4) {
            g2d.setColor(new Color(0x776E65));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value == 8) {
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value == 16) {
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value == 32) {
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value == 64) {
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
        } else if (value == 128) {
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
        } else if (value == 256) {
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
        } else if (value == 512) {
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
        } else if (value == 1024) {
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
        } else if (value == 2048) {
            g2d.setColor(new Color(0x776E65));
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
        } else {
            g2d.setColor(new Color(0xF9F6F2));
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
        }
    }

    // ==================== GAME LOGIC METHODS ====================
    public void resetBoard() {
        for (int i = 0; i < TILE_COUNT; i++) {
            for (int j = 0; j < TILE_COUNT; j++) {
                mat[i][j] = 0;
            }
        }
        score = 0;
        if (scoreValue != null) scoreValue.setText("0");
    }

    public void spawn() {
        spawn(null);
    }

    public void spawn(Runnable onComplete) {
        Random rand = new Random();
        int tries = 30;

        if (mat == null) {
            System.out.println("mat is null");
        } else {
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
                    anim.sourceRow = x;
                    anim.sourceCol = y;
                    animations.add(anim);

                    animateSpawn(anim, onComplete);
                    break;
                }
            }
        }
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

    // ==================== VALIDATION METHODS ====================
    private boolean canShiftUp() {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                if (mat[i][j] == 0) continue;

                // Check if tile can move up
                if (i > 0 && mat[i - 1][j] == 0) {
                    return true;
                }

                // Check if tile can merge up
                if (i > 0 && mat[i - 1][j] == mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canShiftDown() {
        for (int j = 0; j < 4; j++) {
            for (int i = 3; i >= 0; i--) {
                if (mat[i][j] == 0) continue;

                // Check if tile can move down
                if (i < 3 && mat[i + 1][j] == 0) {
                    return true;
                }

                // Check if tile can merge down
                if (i < 3 && mat[i + 1][j] == mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canShiftLeft() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mat[i][j] == 0) continue;

                // Check if tile can move left
                if (j > 0 && mat[i][j - 1] == 0) {
                    return true;
                }

                // Check if tile can merge left
                if (j > 0 && mat[i][j - 1] == mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canShiftRight() {
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                if (mat[i][j] == 0) continue;

                // Check if tile can move right
                if (j < 3 && mat[i][j + 1] == 0) {
                    return true;
                }

                // Check if tile can merge right
                if (j < 3 && mat[i][j + 1] == mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // ==================== SHIFT METHODS ====================
    public void shiftUp() {
        if (!canShiftUp()) {
            return;
        }

        int[][] oldMatrix = copyMatrix(mat);

        // Create animations for tiles that will move
        for (int j = 0; j < 4; j++) {
            int border = 0;
            int lastValue = 0;

            for (int i = 0; i < 4; i++) {
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
                    border++;
                } else {
                    border++;
                    if (i != border) {
                        animateShift(i, j, border, j, current);
                    }
                    lastValue = current;
                }
            }
        }

        // Calculate the new matrix state
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
            if (pendingMatrix != null) {
                mat = pendingMatrix;
                pendingMatrix = null;
                animateMerges();
            }
        });
    }

    public void shiftDown() {
        if (!canShiftDown()) {
            return;
        }

        int[][] oldMatrix = copyMatrix(mat);

        // Create animations for tiles that will move
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

        // Calculate the new matrix state
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
            if (pendingMatrix != null) {
                mat = pendingMatrix;
                pendingMatrix = null;
                animateMerges();
            }
        });
    }

    public void shiftLeft() {
        if (!canShiftLeft()) {
            return;
        }

        int[][] oldMatrix = copyMatrix(mat);

        // Create animations for tiles that will move
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

        // Calculate the new matrix state
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
            if (pendingMatrix != null) {
                mat = pendingMatrix;
                pendingMatrix = null;
                animateMerges();
            }
        });
    }

    public void shiftRight() {
        if (!canShiftRight()) {
            return;
        }

        int[][] oldMatrix = copyMatrix(mat);

        // Create animations for tiles that will move
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

        // Calculate the new matrix state
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
            if (pendingMatrix != null) {
                mat = pendingMatrix;
                pendingMatrix = null;
                animateMerges();
            }
        });
    }

    // ==================== ANIMATION METHODS ====================
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
        anim.sourceRow = fromRow;
        anim.sourceCol = fromCol;
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
        final boolean[] callbackExecuted = {false};
        checkTimer.addActionListener(e -> {
            if (activeAnimations.isEmpty() && !callbackExecuted[0]) {
                callbackExecuted[0] = true;
                ((Timer) e.getSource()).stop();
                callback.run();
            }
        });
        checkTimer.start();
    }

    private void animateMerges() {
        animations.clear();
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

    // ==================== UTILITY METHODS ====================
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

    // ==================== INPUT HANDLING ====================
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

                // Stop all active animations
                for (Timer t : activeAnimations) {
                    t.stop();
                }
                activeAnimations.clear();
                animations.clear();
                isAnimating = false;

                // Handle direction input
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
                    repaint();
                    if (boardRef != null) boardRef.updateScore();
                }
            }
        });
    }

    // ==================== INNER CLASSES ====================
    private class AnimatedTile {
        double x, y;                    // current position (in pixels)
        double targetX, targetY;        // target position (in pixels)
        double scale = 1.0;             // scale factor for animations
        int value;                      // tile value
        int sourceRow, sourceCol;       // source position in grid
    }
}