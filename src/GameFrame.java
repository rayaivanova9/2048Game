import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.stream.events.StartDocument;
import java.awt.*;

public class GameFrame extends JFrame{
    static board board;

    public GameFrame() {
        setTitle("2048");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        add(mainPanel);
        GamePanel grid = new GamePanel();
        board = new board(grid);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(board.getBoard());
        mainPanel.add(Box.createVerticalStrut(10));

        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        mainPanel.add(grid);
        mainPanel.add(Box.createVerticalStrut(10));

        mainPanel.setBackground(new Color(0xE6D7CD));
        pack();
        setVisible(true);
    }
}
