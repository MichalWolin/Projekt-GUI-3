import graphics.*;
import logic.LayerLogic;
import logic.ReceiverLogic;
import logic.SenderLogic;

import javax.swing.*;
import java.awt.*;

public class S29239Projekt03 extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(S29239Projekt03::new);
    }
    public S29239Projekt03(){
        this.setLayout(new BorderLayout());

        ReceiverPanel receiverPanel = new ReceiverPanel();
        ReceiverLogic receiverLogic = new ReceiverLogic();
        receiverPanel.setListener(receiverLogic);
        receiverPanel.paintPanel();
        receiverLogic.setListener(receiverPanel);

        LayerPanel layerPanel = new LayerPanel();
        LayerLogic layerLogic = new LayerLogic(receiverLogic);
        layerPanel.setListener(layerLogic);
        layerPanel.paintPanel();
        layerLogic.setListener(layerPanel);

        SenderPanel senderPanel = new SenderPanel();
        SenderLogic senderLogic = new SenderLogic(layerLogic);
        senderPanel.setListener(senderLogic);
        senderPanel.paintPanel();

        this.getContentPane().add(senderPanel, BorderLayout.LINE_START);
        this.getContentPane().add(layerPanel, BorderLayout.CENTER);
        this.getContentPane().add(receiverPanel, BorderLayout.LINE_END);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 500);
        this.setVisible(true);
    }
}
