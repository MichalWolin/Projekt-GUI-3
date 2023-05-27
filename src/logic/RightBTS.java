package logic;

import java.util.concurrent.LinkedBlockingQueue;

public class RightBTS extends BTS implements Runnable{
    private LinkedBlockingQueue<PDU> queue;
    private int processedPDUs;
    private int awaitingPDUs;
    public RightBTS(int BTSNumber){
        super(BTSNumber);
        queue = new LinkedBlockingQueue<>();
        processedPDUs = 0;
        awaitingPDUs = queue.size();
    }
    @Override
    public void run() {

    }

    @Override
    public int getProcessedPDUs() {
        return processedPDUs;
    }

    @Override
    public int getAwaitingPDUs() {
        return awaitingPDUs;
    }

    @Override
    public void queuePDU(PDU pdu) {
        queue.add(pdu);
    }
}
