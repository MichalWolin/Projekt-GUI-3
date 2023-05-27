package graphics;

import interfaces.VRDListener;

import javax.swing.*;

public class VRDGraphics extends JPanel {
    private VRDListener listener;
    private ReceiverPanel receiverPanel;
    public VRDGraphics(ReceiverPanel receiverPanel){
        this.receiverPanel = receiverPanel;
    }
    public void setListener(VRDListener listener){
        this.listener = listener;
    }

    public void paintVRD(){
        if(listener != null){
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
            this.add(Box.createVerticalStrut(1));

            this.add(new JLabel("Terminate VRD"));
            JButton terminateButton = new JButton("Terminate");
            terminateButton.addActionListener(e -> {
                receiverPanel.remove(this);
                listener.remove();
            });
            this.add(terminateButton);
            this.add(new JLabel("Processesed: " + listener.getProcessed()));
            this.add(new JLabel("Remove every 10s?"));
            JCheckBox removeBox = new JCheckBox();
            removeBox.setSelected(listener.getRemove());
            removeBox.addActionListener(e -> listener.setRemove(removeBox.isSelected()));
            this.add(removeBox);
        }
    }
}
