package logic;

import java.util.concurrent.LinkedBlockingQueue;

public class RightBTS extends BTS implements Runnable{
    private LinkedBlockingQueue<PDU> queue;
    private int processedPDUs;
    private LayerLogic layerLogic;
    public RightBTS(LayerLogic layerLogic, int BTSNumber){
        super(BTSNumber);
        this.layerLogic = layerLogic;
        queue = new LinkedBlockingQueue<>();
        processedPDUs = 0;
        new Thread(this).start();
    }
    @Override
    public void run() {
        while (true) {
            synchronized(queue){
                try {
                    PDU pdu = queue.take();
                    Thread.sleep(3000);
                    layerLogic.passToVRD(pdu);
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
