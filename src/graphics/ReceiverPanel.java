package graphics;

import interfaces.UpdateListener;
import interfaces.VBDPanelListener;
import interfaces.VRDPanelListener;
import logic.VRD;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReceiverPanel extends JPanel implements UpdateListener {
    private VRDPanelListener listener;
    private JPanel container;

    public void setListener(VRDPanelListener listener){
        this.listener = listener;
    }

    public void paintPanel(){
        if(listener != null){
            this.setLayout(new BorderLayout());
            this.setPreferredSize(new Dimension(250, 500));

            JScrollPane scrollPane = new JScrollPane();
            container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            scrollPane.setViewportView(container);

            JButton addVBDButton = new JButton("ADD");
            addVBDButton.addActionListener(e -> addVRD());

            this.add(scrollPane, BorderLayout.CENTER);
            this.add(addVBDButton, BorderLayout.PAGE_END);
        }
    }

    private void addVRD() {
        if(listener != null){
            listener.addVRD();
            update();
        }
    }

    @Override
    public void update(){
        if(listener != null){
            container.removeAll();
            List<VRD> vrdList = listener.getVRDs();
            for(VRD vrd : vrdList){
                VRDGraphics vrdGraphics = new VRDGraphics(this);
                vrdGraphics.setListener(vrd);
                vrdGraphics.paintVRD();
                container.add(vrdGraphics);
            }
            container.revalidate();
            container.repaint();
        }
    }

    public void remove(VRDGraphics vrdg){
        if(listener != null){
            container.remove(vrdg);
            container.revalidate();
            container.repaint();
        }
    }
}
