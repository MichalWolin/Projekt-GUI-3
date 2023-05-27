package logic;

import interfaces.VBDListener;

public class VBD implements Runnable, VBDListener {
    private SenderLogic senderLogic;
    private String message;
    private int vbdNumber;
    private int frequency;
    private boolean isRunning;
    private boolean isStopped;
    public VBD(SenderLogic senderLogic, int vbdNumber){
        this.frequency = 5;
        this.senderLogic = senderLogic;
        this.vbdNumber = vbdNumber;
        this.isStopped = false;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while(!isStopped){
            synchronized (senderLogic) {
                while (!isRunning) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            try {
                int msgFrequency = frequency;
                Thread.sleep((long)msgFrequency * 1000);
                synchronized (this) {
                    if (isRunning && !isStopped) {
                        senderLogic.passPDU(new PDU(message));
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void myStart() {
        new Thread(this).start();
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public int getFrequency() {
        return frequency;
    }

    @Override
    public int getVBDNumber() {
        return vbdNumber;
    }

    @Override
    public boolean getIsRunning() {
        return isRunning;
    }

    @Override
    public void resume() {
        synchronized (this) {
            this.isRunning = true;
            this.notify();
        }
    }

    @Override
    public void suspend(){
        this.isRunning = false;
    }

    @Override
    public void remove() {
        this.isStopped = true;
        senderLogic.removeVBD(this);
    }
}
