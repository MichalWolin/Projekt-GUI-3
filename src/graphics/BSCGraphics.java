package graphics;

import interfaces.StationListener;

import javax.swing.*;
import java.awt.*;

public class BSCGraphics extends JPanel {
    private StationListener listener;

    public void setListener(StationListener listener){
        this.listener = listener;
    }

    public void paintPanel(){
        if(listener != null){
            this.setLayout(new GridLayout(3,2));
            this.setMaximumSize(new Dimension(100, 50));
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.add(new JLabel("BSC No.: " + listener.getNumber()), BorderLayout.LINE_START);
            this.add(new JLabel("Processed: " + listener.getProcessedPDUs()), BorderLayout.CENTER);
            this.add(new JLabel("Awaiting: " + listener.getAwaitingPDUs()), BorderLayout.LINE_END);
        }
    }
}
