package logic;

import interfaces.StationListener;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class BTS implements StationListener {
    private int BTSNumber;
    public BTS(int BTSNumber){
        this.BTSNumber = BTSNumber;
    }
    @Override
    public int getNumber() {
        return BTSNumber;
    }

    public abstract int getProcessedPDUs();

    public abstract int getAwaitingPDUs();

    public abstract void queuePDU(PDU pdu);
}
