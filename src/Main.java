import javax.swing.*;

public class Main {
    public static logInForm form;

    public static void main(String[] args) {
        form = new logInForm();
        SwingUtilities.invokeLater(() -> form.setVisible(true));;
    }
}
