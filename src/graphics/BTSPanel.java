package graphics;

import interfaces.LayerListener;
import logic.BTS;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public abstract class BTSPanel extends JPanel {
    private LayerListener listener;
    protected JPanel container;

    public void setListener(LayerListener listener){
        this.listener = listener;
    }

    public void paintPanel(){
        if(listener != null){
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setPreferredSize(new Dimension(100, 420));
            JScrollPane scrollPane = new JScrollPane();
            container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            scrollPane.setViewportView(container);

            this.add(scrollPane);
        }
    }

    abstract void update();
}
