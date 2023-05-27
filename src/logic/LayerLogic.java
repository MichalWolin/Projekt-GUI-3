package logic;

import interfaces.LayerListener;
import interfaces.UpdateListener;

import java.util.Comparator;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

public class LayerLogic implements LayerListener {
    private UpdateListener listener;
    private HashSet<LeftBTS> leftBTSSet;
    private HashSet<RightBTS> rightBTSSet;
    private int BTSNumber;



    public LayerLogic(){
        BTSNumber = 1000;
        leftBTSSet = new HashSet<>();
        rightBTSSet = new HashSet<>();
    }
    public void setListener(UpdateListener listener){
        this.listener = listener;
        addLeftBTS();
        addRightBTS();
    }
    @Override
    public void addBCSLayer() {

    }

    @Override
    public HashSet<LeftBTS> getLeftBTS() {
        return leftBTSSet;
    }

    @Override
    public HashSet<RightBTS> getRightBTS() {
        return rightBTSSet;
    }

    public void queuePDU(PDU pdu){
        if(leftBTSSet.stream().allMatch(bts -> bts.getAwaitingPDUs() > 5)){
            addLeftBTS();
        }

        LeftBTS bts = leftBTSSet.stream().min(
                Comparator.comparingInt(LeftBTS::getAwaitingPDUs)).orElse(null);
        if(bts != null){
            bts.queuePDU(pdu);
            listener.update();
        }
    }

    public void addLeftBTS(){
        BTSNumber += (int)(Math.random()*1000);
        this.leftBTSSet.add(new LeftBTS(this, BTSNumber));
        update();
    }

    public void addRightBTS(){
        BTSNumber += (int)(Math.random()*1000);
        this.rightBTSSet.add(new RightBTS(BTSNumber));
        update();
    }

    public void update(){
        if(listener != null){
            listener.update();
        }
    }
}
