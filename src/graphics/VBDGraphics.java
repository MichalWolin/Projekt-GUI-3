package graphics;

import logic.VBD;

import javax.swing.*;
import java.awt.*;

public class VBDGraphics extends JPanel {
    public VBDGraphics(SenderPanel senderPanel, VBD vbd){
        //TODO jezeli jest frequency 0 to zsetowac, inaczej pobrac wartosc
        String message = vbd.getMessage();
        if(message == null) {
            vbd.setMessage(JOptionPane.showInputDialog("Enter desired message:", ""));
        }

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.add(new JLabel("Frequency (in seconds)"));
        JSlider slider = new JSlider();
        slider.setMinimum(1);
        slider.setMaximum(10);
        slider.setMajorTickSpacing(1);
        slider.setValue(1);
        vbd.setFrequency(slider.getValue());
        slider.addChangeListener(e -> vbd.setFrequency(slider.getValue()));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        this.add(slider);

        this.add(new JLabel("Terminate VBD"));
        JButton terminateButton = new JButton("Terminate");
        this.add(terminateButton);
        terminateButton.addActionListener(e -> {
            senderPanel.removeVBD(this, vbd);
        });

        this.add(new JLabel("Device number"));
        JTextField vbdNumberTF = new JTextField("" + vbd.getVbdNumber());
        vbdNumberTF.setEditable(false);
        Dimension preferredSize = new Dimension(100, 30);
        vbdNumberTF.setPreferredSize(preferredSize);
        vbdNumberTF.setMaximumSize(preferredSize);
        this.add(vbdNumberTF);

        this.add(new JLabel("State"));
        JComboBox<String> stateBox = new JComboBox<>(new String[]{"ACTIVE", "WAITING"});
        stateBox.addActionListener(e -> {
            String state = (String)(stateBox.getSelectedItem());
            if(state.equals("ACTIVE")){
                vbd.resumeVBD();
            } else {
                vbd.suspendVBD();
            }
        });
        stateBox.setMaximumSize(preferredSize);
        this.add(stateBox);
    }
}
