package bankingMain;

import bankingGUI.MainFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);

            frame.setLocationRelativeTo(null);
        });
    }
}