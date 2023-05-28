package logic;

import interfaces.StationListener;

import java.util.concurrent.LinkedBlockingQueue;

public class BSC implements StationListener, Runnable {
    private LinkedBlockingQueue<PDU> queue;
    private int BSCNumber;
    private int processedPDUs;
    private BSCLayer bscLayer;
    private boolean isRunning;
    public BSC(BSCLayer bscLayer, int BSCNumber){
        this.bscLayer = bscLayer;
        this.BSCNumber = BSCNumber;
        this.isRunning = true;
        queue = new LinkedBlockingQueue<>();
        processedPDUs = 0;
    }
    @Override
    public int getNumber() {
        return BSCNumber;
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
    public void run() {
        while(isRunning){
            synchronized (queue){
                try {
                    PDU pdu = queue.take();
                    Thread.sleep((int)(Math.random() * 10000) + 5000);
                    bscLayer.passPDU(pdu);
                    processedPDUs++;
                    bscLayer.update();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void receivePDU(PDU pdu) {
        queue.add(pdu);
        bscLayer.update();
    }

    public void myStop(){
        isRunning = false;
    }

    public LinkedBlockingQueue<PDU> getQueue(){
        return queue;
    }
}
