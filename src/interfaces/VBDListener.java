package interfaces;

public interface VBDListener {
    void setMessage(String message);
    String getMessage();
    void setFrequency(int frequency);
    int getFrequency();
    int getVBDNumber();
    boolean getIsRunning();
    void resume();
    void suspend();
    void remove();
}
