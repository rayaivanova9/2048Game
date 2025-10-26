import java.sql.*;


public class connect {

    public connect() {
        String query = "INSERT INTO high_scores (username, score) VALUES ('thebest', 4218688)";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/maris/IdeaProjects/2048Game/identifier.sqlite");
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            System.out.println(pstmt.executeUpdate());

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error.");
        }
    }
}
