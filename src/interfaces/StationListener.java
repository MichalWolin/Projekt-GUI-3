package interfaces;

public interface StationListener {
    int getBTSNumber();
    int getProcessedPDUs();
    int getAwaitingPDUs();
}
