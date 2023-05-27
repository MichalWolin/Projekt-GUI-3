package interfaces;

public interface VRDListener {
    int getVRDNumber();
    int getProcessed();
    void setRemove(boolean remove);
    void remove();
    boolean getRemove();
}
