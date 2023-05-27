package interfaces;

import logic.*;

import java.util.HashSet;

public interface LayerListener {
    void addBCSLayer();
    HashSet<LeftBTS> getLeftBTS();
    HashSet<RightBTS> getRightBTS();
}
