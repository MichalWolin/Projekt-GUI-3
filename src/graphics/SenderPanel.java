package graphics;

import interfaces.VBDListener;
import logic.VBD;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SenderPanel extends JPanel{
    private VBDListener listener;
    private JPanel container;
    public void setListener(VBDListener listener) {
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
            addVBDButton.addActionListener(e -> addVBD());

            this.add(scrollPane, BorderLayout.CENTER);
            this.add(addVBDButton, BorderLayout.PAGE_END);
        }
    }

    public void addVBD(){
        if(listener != null){
            listener.addVBD();
            update();
        }
    }

    public void update(){
        if(listener != null){
            container.removeAll();
            List<VBD> vbdList = listener.getVBDs();
            for(VBD vbd : vbdList){
                container.add(new VBDGraphics(this, vbd));
                new Thread(vbd).start();
            }
            container.revalidate();
            container.repaint();
        }
    }

    public void removeVBD(VBDGraphics vbdg, VBD vbd){
        if(listener != null){
            listener.removeVBD(vbd);
            container.remove(vbdg);
            container.revalidate();
            container.repaint();
        }
    }
}
