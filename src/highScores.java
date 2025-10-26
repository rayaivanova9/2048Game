import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class highScores extends JFrame{
    private JTable table1;
    private JPanel panel;
    private JScrollPane scrollPane;
    public static DefaultTableModel model = new DefaultTableModel();
    private ArrayList<String[]> columns;

    public highScores() {
        setSize(400, 600);
        setContentPane(panel);
        setVisible(true);
        scrollPane.getViewport().setBackground(new Color(255, 240, 230));
        scrollPane.getVerticalScrollBar().setBackground(Color.GRAY);
        table1.setRowHeight(30);
        table1.setModel(model);

        JTableHeader header = table1.getTableHeader();
        header.setBackground(new Color(230, 215, 205));
        header.setForeground(new Color(53, 39, 35));
        header.setFont(new Font("Droid Sans", Font.BOLD, 14));

        columns = connect.executeTable();
        updateTable();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                model.setRowCount(0);
                model.setColumnCount(0);
            }
        });
    }

    private void updateTable() {
        model.setRowCount(0);
        for (String[] column : columns) {
            model.addRow(column);
        }
    }
}
