package interfaces;

import logic.*;

import java.util.HashSet;
import java.util.List;

public interface LayerListener {
    HashSet<LeftBTS> getLeftBTS();
    HashSet<RightBTS> getRightBTS();
    void addBSCLayer();
    void removeBSCLayer();
    List<BSCLayer> getBSC();
}
