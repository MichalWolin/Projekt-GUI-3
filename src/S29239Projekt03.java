import graphics.*;
import logic.SenderLogic;

import javax.swing.*;
import java.awt.*;

public class S29239Projekt03 extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(S29239Projekt03::new);
    }
    public S29239Projekt03(){
        this.setLayout(new BorderLayout());

        SenderPanel senderPanel = new SenderPanel();
        SenderLogic senderLogic = new SenderLogic();
        senderPanel.setListener(senderLogic);
        senderPanel.paintPanel();

        this.getContentPane().add(senderPanel, BorderLayout.LINE_START);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 500);
        this.setVisible(true);
    }
}
