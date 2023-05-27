package graphics;

import interfaces.LayerListener;
import interfaces.UpdateListener;
import logic.BTS;
import logic.LeftBTS;
import logic.RightBTS;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class LayerPanel extends JPanel implements UpdateListener {
    private LayerListener listener;
    private LeftBTSPanel leftBTSPanel;
    private RightBTSPanel rightBTSPanel;
    private BSCLayerPanel bscLayerPanel;
    private class LeftBTSPanel extends BTSPanel{
        private JPanel leftContainer;
        @Override
        void update(){
            if(listener != null){
                leftContainer.removeAll();
                HashSet<LeftBTS> btsSet = listener.getLeftBTS();
                for(LeftBTS bts : btsSet){
                    BTSGraphics btsGraphics = new BTSGraphics();
                    btsGraphics.setListener(bts);
                    btsGraphics.paintBTS();
                    leftContainer.add(btsGraphics);
                }
                leftContainer.revalidate();
                leftContainer.repaint();
            }
        }

        public void paintPanel(){
            super.paintPanel();
            leftContainer = container;
        }
    }

    private class RightBTSPanel extends BTSPanel{
        private JPanel rightContainer;
        @Override
        void update(){
            if(listener != null){
                rightContainer.removeAll();
                HashSet<RightBTS> btsSet = listener.getRightBTS();
                for(RightBTS bts : btsSet){
                    BTSGraphics btsGraphics = new BTSGraphics();
                    btsGraphics.setListener(bts);
                    btsGraphics.paintBTS();
                    rightContainer.add(btsGraphics);
                }
                rightContainer.revalidate();
                rightContainer.repaint();
            }
        }

        public void paintPanel(){
            super.paintPanel();
            rightContainer = container;
        }
    }

    public void setListener(LayerListener listener){
        this.listener = listener;
    }

    public void paintPanel(){
        if(listener != null){
            this.setLayout(new BorderLayout());
            this.setPreferredSize(new Dimension(500, 500));

            leftBTSPanel = new LeftBTSPanel();
            leftBTSPanel.setListener(listener);
            leftBTSPanel.paintPanel();

            rightBTSPanel = new RightBTSPanel();
            rightBTSPanel.setListener(listener);
            rightBTSPanel.paintPanel();

            bscLayerPanel = new BSCLayerPanel();
            bscLayerPanel.setListener(listener);
            bscLayerPanel.paintPanel();

            this.add(leftBTSPanel, BorderLayout.WEST);
            this.add(bscLayerPanel, BorderLayout.CENTER);
            this.add(rightBTSPanel, BorderLayout.EAST);
        }
    }

    @Override
    public void update() {
        leftBTSPanel.update();
        rightBTSPanel.update();
        bscLayerPanel.update();
    }
}
