import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class logInForm extends JFrame{
    private JTextField nameField;
    private JButton startGameButton;
    private JPanel panel;
    private String username;

    public logInForm() {
        setSize(500, 250);
        setContentPane(panel);

        startGameButton.setBackground(new Color(220,195,175));
        startGameButton.setEnabled(false);

        nameField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateStartButton() {
                startGameButton.setEnabled(!nameField.getText().trim().isEmpty());
                if (!nameField.getText().trim().isEmpty()) {
                    startGameButton.setBackground(new Color(99,69,61));
                } else {
                    startGameButton.setBackground(new Color(220,195,175));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateStartButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateStartButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateStartButton();
            }
        });

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = nameField.getText();
                SwingUtilities.invokeLater(GameFrame::new);
            }
        });
    }
    public String getUsername() {
        return username;
    }
}
