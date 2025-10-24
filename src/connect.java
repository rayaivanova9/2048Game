import java.sql.*;


public class connect {
    private PreparedStatement pstmt;

    public static int getRowCount(String query, String username) {
        //String testQuery = "INSERT INTO high_scores (username, score) VALUES ('thebest', 4218688)";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/20252711/IdeaProjects/2048Game/identifier.sqlite");
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            int rowCount = 0;
            if (rs.next()) {
                rowCount++;
            }

            return (rowCount);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error.");
        }
        return -1;
    }

    public static void getName(String query, String username) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/20252711/IdeaProjects/2048Game/identifier.sqlite");
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error.");
        }
    }

    public static void highScore (int score, String username) {
        String query = "SELECT"
    }
}
