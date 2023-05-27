package logic;

import interfaces.VRDListener;

public class VRD implements Runnable, VRDListener {
    private ReceiverLogic receiverLogic;
    private int vrdNumber;
    private int processedPDUs;
    private boolean remove;
    private boolean isRunning;

    public VRD(int vrdNumber, ReceiverLogic receiverLogic){
        this.vrdNumber = vrdNumber;
        this.receiverLogic = receiverLogic;
        this.isRunning = true;
        remove = false;
        processedPDUs = 0;
    }
    @Override
    public void run() {
        while(isRunning){
            synchronized (this){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(remove){
                    processedPDUs = 0;
                }
            }
        }
    }

    @Override
    public int getVRDNumber() {
        return vrdNumber;
    }

    @Override
    public int getProcessed() {
        return processedPDUs;
    }

    @Override
    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    @Override
    public void remove() {
        isRunning = false;
        receiverLogic.removeVRD(this);
    }

    @Override
    public boolean getRemove() {
        return remove;
    }

    public void myStart() {
        new Thread(this).start();
    }

    public void addProcessedPDU(){
        processedPDUs++;
    }
}
