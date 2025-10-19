import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameFrame extends JFrame{

    public GameFrame() {
        setTitle("2048");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        GamePanel panel1 = new GamePanel();
        JPanel panel2 = new JPanel();

        panel2.setPreferredSize(new Dimension(100,100));
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panel1, BorderLayout.SOUTH);
        pack();
        //setSize(600,600);
        setVisible(true);
    }
}
