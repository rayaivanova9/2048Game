import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final int TILE_SIZE = 100;
    private final int GAP = 10;
    private final int TILE_COUNT = 4;
    private final int HEIGHT = GAP + (TILE_SIZE + GAP)*TILE_COUNT;
    private final int WIDTH = GAP + (TILE_SIZE + GAP)*TILE_COUNT;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(0xbbada0));
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
                g2d.fillRoundRect(x, y, TILE_SIZE, TILE_SIZE, 10, 10);
            }
        }
    }

}
