package logic;

public class VBD implements Runnable{
    private String message;
    private int vbdNumber;
    private int frequency;
    private boolean isRunning;
    private boolean isStopped;
    public VBD(int vbdNumber){
        this.vbdNumber = vbdNumber;
        this.isStopped = false;
        this.isRunning = true;
    }

    public int getVbdNumber() {
        return vbdNumber;
    }

    @Override
    public void run() {
        while(!isStopped){
            synchronized (this) {
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
                        System.out.println("wyslalem wiadomosc " + msgFrequency);
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void suspendVBD(){
        this.isRunning = false;
    }

    public void resumeVBD(){
        synchronized (this) {
            this.isRunning = true;
            this.notify();
        }
    }

    public void stopVBD(){
        this.isStopped = true;
    }

    public int getFrequency() {
        return frequency;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public String getMessage(){
        return message;
    }
}
