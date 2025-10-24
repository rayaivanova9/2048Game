import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);

        String query = "SELECT * FROM high_scores WHERE username LIKE ?";
        String username = "nana";
        System.out.println(connect.getRowCount(query, username));
    }
}
