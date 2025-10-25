import java.sql.*;


public class connect {

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

    public static void highScore (int currentScore) {
        String username = GameFrame.board.getUsername();
        String query = "SELECT score FROM high_scores WHERE username LIKE ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/20252711/IdeaProjects/2048Game/identifier.sqlite");
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int currentHighScore = rs.getInt(1);
                System.out.println(currentScore + " " + currentHighScore);

                if (currentScore > currentHighScore) {
                    String query2 = "UPDATE high_scores SET score = ? WHERE username LIKE ?";

                    try (PreparedStatement pstmt2 = connection.prepareStatement(query2)) {

                        pstmt2.setInt(1, currentScore);
                        pstmt2.setString(2, username);
                        pstmt2.executeUpdate();
                        board.setNewHighScore(currentScore);


                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("SQL Error.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error.");
        }
    }

    public static int getHighScore(String username) {
        String query = "SELECT score FROM high_scores WHERE username LIKE ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/20252711/IdeaProjects/2048Game/identifier.sqlite");
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error.");
        }
        return -1;
    }
}
