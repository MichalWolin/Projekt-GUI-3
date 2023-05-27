import graphics.*;
import logic.LayerLogic;
import logic.SenderLogic;

import javax.swing.*;
import java.awt.*;

public class S29239Projekt03 extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(S29239Projekt03::new);
    }
    public S29239Projekt03(){
        this.setLayout(new BorderLayout());

        LayerPanel layerPanel = new LayerPanel();
        LayerLogic layerLogic = new LayerLogic();
        layerPanel.setListener(layerLogic);
        layerPanel.paintPanel();
        layerLogic.setListener(layerPanel);

        SenderPanel senderPanel = new SenderPanel();
        SenderLogic senderLogic = new SenderLogic(layerLogic);
        senderPanel.setListener(senderLogic);
        senderPanel.paintPanel();

        this.getContentPane().add(senderPanel, BorderLayout.LINE_START);
        this.getContentPane().add(layerPanel, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 500);
        this.setVisible(true);
    }
}
