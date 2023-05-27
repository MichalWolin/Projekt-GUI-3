package graphics;

import interfaces.LayerListener;
import logic.BSC;
import logic.BSCLayer;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class BSCLayerPanel extends JPanel {
    private JPanel bscLayers;
    private LayerListener listener;
    public void setListener(LayerListener listener){
        this.listener = listener;
    }

    public void paintPanel(){
        if(listener != null){
            this.setLayout(new BorderLayout());
            this.setPreferredSize(new Dimension(300, 500));

            JScrollPane scrollPane = new JScrollPane();
            bscLayers = new JPanel();
            bscLayers.setLayout(new BoxLayout(bscLayers, BoxLayout.X_AXIS));
            scrollPane.setViewportView(bscLayers);

            JPanel buttonsContainer = new JPanel();
            buttonsContainer.setLayout(new GridLayout(1, 2));
            JButton addLayerButton = new JButton("+");
            addLayerButton.addActionListener(e -> listener.addBSCLayer());
            JButton removeLayerButton = new JButton("-");
            removeLayerButton.addActionListener(e -> listener.removeBSCLayer());
            buttonsContainer.add(addLayerButton);
            buttonsContainer.add(removeLayerButton);

            this.add(scrollPane, BorderLayout.CENTER);
            this.add(buttonsContainer, BorderLayout.SOUTH);
        }
    }

    public void update() {
        if (listener != null) {
            bscLayers.removeAll();
            for (BSCLayer layer : listener.getBSC()) {
                JPanel bscLayer = new JPanel();
                bscLayer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                bscLayer.setPreferredSize(new Dimension(100, 400));
                JPanel bscContainer = new JPanel();
                bscContainer.setLayout(new BoxLayout(bscContainer, BoxLayout.Y_AXIS));
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setViewportView(bscContainer);
                for (BSC bsc : layer.getBSCSet()) {
                    BSCGraphics bscGraphics = new BSCGraphics();
                    bscGraphics.setListener(bsc);
                    bscGraphics.paintPanel();
                    bscContainer.add(bscGraphics);
                }
                bscLayer.add(scrollPane);
                bscLayers.add(bscLayer);
            }
            bscLayers.revalidate();
            bscLayers.repaint();
        }
    }
}
