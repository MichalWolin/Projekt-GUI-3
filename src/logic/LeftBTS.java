package logic;

import logic.BTS;
import logic.PDU;

import java.util.concurrent.LinkedBlockingQueue;

public class LeftBTS extends BTS implements Runnable{
    private LinkedBlockingQueue<PDU> queue;
    private int processedPDUs;
    private int awaitingPDUs;
    private LayerLogic layerLogic;
    public LeftBTS(LayerLogic layerLogic, int BTSNumber){
        super(BTSNumber);
        this.layerLogic = layerLogic;
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
        System.out.println("otrzymalem");
        queue.add(pdu);
        layerLogic.update();
    }
}
