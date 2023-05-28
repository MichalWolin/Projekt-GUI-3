import graphics.*;
import logic.LayerLogic;
import logic.ReceiverLogic;
import logic.SenderLogic;
import logic.VBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
        senderLogic.setListener(receiverLogic);

        this.getContentPane().add(senderPanel, BorderLayout.LINE_START);
        this.getContentPane().add(layerPanel, BorderLayout.CENTER);
        this.getContentPane().add(receiverPanel, BorderLayout.LINE_END);

        this.setSize(1200, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                File file = new File("src/information.txt");
                List<VBD> list = senderLogic.getVBDs();

                if(list.size() != 0){
                    try{
                        FileOutputStream fos = new FileOutputStream(file);

                        for(VBD vbd : list){
                            int vbdNumber = vbd.getVBDNumber();
                            int sent = vbd.getSent();
                            String message = vbd.getMessage();

                            for (int i = 3; i >= 0; i--) {
                                fos.write(vbdNumber >> (i * 8));
                            }

                            for (int i = 1; i >= 0; i--) {
                                fos.write(sent >> (i * 8));
                            }

                            byte[] messageBytes = message.getBytes();
                            fos.write(messageBytes.length);
                            fos.write(messageBytes);
                        }
                    }catch (IOException e){
                        throw new RuntimeException(e);
                    }
                }
                System.exit(0);
            }
        });
    }
}
