package graphics;

import interfaces.VBDListener;
import logic.VBD;

import javax.swing.*;
import java.awt.*;

public class VBDGraphics extends JPanel {
    private VBDListener listener;
    private SenderPanel senderPanel;
    public VBDGraphics(SenderPanel senderPanel){
        this.senderPanel = senderPanel;
    }

    public void paintVBD(){
        if(listener != null){
            if(listener.getMessage() == null) {
                listener.setMessage(JOptionPane.showInputDialog("Enter desired message:", ""));
            }

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            this.add(new JLabel("Frequency (in seconds)"));
            JSlider slider = new JSlider();
            slider.setMinimum(1);
            slider.setMaximum(10);
            slider.setMajorTickSpacing(1);
            int frequency = listener.getFrequency();
            if(frequency != 0){
                slider.setValue(frequency);
            }else{
                slider.setValue(1);
                listener.setFrequency(1);
            }
            slider.addChangeListener(e -> listener.setFrequency(slider.getValue()));
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            this.add(slider);

            this.add(new JLabel("Terminate VBD"));
            JButton terminateButton = new JButton("Terminate");
            this.add(terminateButton);
            terminateButton.addActionListener(e -> {
                senderPanel.removeVBD(this);
                listener.remove();
            });

            this.add(new JLabel("Device number"));
            JTextField vbdNumberTF = new JTextField("" + listener.getVBDNumber());
            vbdNumberTF.setEditable(false);
            Dimension preferredSize = new Dimension(100, 30);
            vbdNumberTF.setPreferredSize(preferredSize);
            vbdNumberTF.setMaximumSize(preferredSize);
            this.add(vbdNumberTF);

            this.add(new JLabel("State"));
            JComboBox<String> stateBox;
            if(listener.getIsRunning()){
                stateBox = new JComboBox<>(new String[]{"ACTIVE", "WAITING"});
            }else{
                stateBox = new JComboBox<>(new String[]{"WAITING", "ACTIVE"});
            }
            stateBox.addActionListener(e -> {
                String state = (String)(stateBox.getSelectedItem());
                if(state.equals("ACTIVE")){
                    listener.resume();
                } else {
                    listener.suspend();
                }
            });
            stateBox.setMaximumSize(preferredSize);
            this.add(stateBox);

            listener.myStart();
        }
    }

    public void setListener(VBDListener listener){
        this.listener = listener;
    }
}
