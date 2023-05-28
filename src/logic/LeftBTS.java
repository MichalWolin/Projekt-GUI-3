package logic;

import logic.BTS;
import logic.PDU;

import java.util.concurrent.LinkedBlockingQueue;

public class LeftBTS extends BTS implements Runnable{
    private LinkedBlockingQueue<PDU> queue;
    private int processedPDUs;
    private LayerLogic layerLogic;
    public LeftBTS(LayerLogic layerLogic, int BTSNumber){
        super(BTSNumber);
        this.layerLogic = layerLogic;
        queue = new LinkedBlockingQueue<>();
        processedPDUs = 0;
    }
    @Override
    public void run() {
        while (true) {
            synchronized(queue){
                try {
                    PDU pdu = queue.take();
                    Thread.sleep(3000);
                    layerLogic.receivePDU(pdu);
                    processedPDUs++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public int getProcessedPDUs() {
        return processedPDUs;
    }

    @Override
    public int getAwaitingPDUs() {
        return queue.size();
    }

    @Override
    public void queuePDU(PDU pdu) {
        queue.add(pdu);
        layerLogic.update();
    }
}
