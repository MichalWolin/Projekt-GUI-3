package logic;

import interfaces.LayerListener;
import interfaces.UpdateListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LayerLogic implements LayerListener {
    private UpdateListener listener;
    private HashSet<LeftBTS> leftBTSSet;
    private HashSet<RightBTS> rightBTSSet;
    private List<BSCLayer> bscLayerList;
    private ReceiverLogic receiverLogic;
    private int number;
    public LayerLogic(ReceiverLogic receiverLogic){
        this.receiverLogic = receiverLogic;
        number = 1000;
        leftBTSSet = new HashSet<>();
        rightBTSSet = new HashSet<>();
        bscLayerList = new ArrayList<>();
    }
    public void setListener(UpdateListener listener){
        this.listener = listener;
        addLeftBTS();
        addRightBTS();
        addBSCLayer();
    }

    @Override
    public HashSet<LeftBTS> getLeftBTS() {
        return leftBTSSet;
    }

    @Override
    public HashSet<RightBTS> getRightBTS() {
        return rightBTSSet;
    }

    @Override
    public void addBSCLayer() {
        bscLayerList.add(new BSCLayer(this));
        update();
    }

    @Override
    public void removeBSCLayer() {
        if(bscLayerList.size() > 1){
            HashSet<BSC> list = bscLayerList.get(bscLayerList.size() - 1).getBSCSet();
            for(BSC bsc : list){
                bsc.myStop();
                LinkedBlockingQueue<PDU> queue = bsc.getQueue();
                for(PDU pdu : queue){
                    passToBTS(pdu);
                }
            }
            bscLayerList.remove(bscLayerList.size() - 1);
        }
        update();
    }

    @Override
    public List<BSCLayer> getBSC() {
        return bscLayerList;
    }

    public void queuePDU(PDU pdu){
        if(leftBTSSet.stream().allMatch(bts -> bts.getAwaitingPDUs() > 5)){
            addLeftBTS();
        }

        LeftBTS bts = leftBTSSet.stream().min(
                Comparator.comparingInt(LeftBTS::getAwaitingPDUs)).orElse(null);
        if(bts != null){
            bts.queuePDU(pdu);
        }
    }

    public void receivePDU(PDU pdu){
        bscLayerList.get(0).receivePDU(pdu);
    }

    public void passPDU(BSCLayer current, PDU pdu){
        int curr = bscLayerList.indexOf(current);
        if(curr >= 0 && curr < bscLayerList.size() - 1){
            BSCLayer next = bscLayerList.get(curr + 1);
            next.receivePDU(pdu);
        }else{
            passToBTS(pdu);
        }
    }

    public void passToBTS(PDU pdu){
        if(rightBTSSet.stream().allMatch(bts -> bts.getAwaitingPDUs() > 5)){
            addRightBTS();
        }

        RightBTS bts = rightBTSSet.stream().min(
                Comparator.comparingInt(RightBTS::getAwaitingPDUs)).orElse(null);
        if(bts != null){
            bts.queuePDU(pdu);
        }
    }

    public void passToVRD(PDU pdu){
        receiverLogic.receivePDU(pdu);
    }

    public void addLeftBTS(){
        number += (int)(Math.random()*10);
        LeftBTS leftBTS = new LeftBTS(this, number);
        this.leftBTSSet.add(leftBTS);
        new Thread(leftBTS).start();
    }

    public void addRightBTS(){
        number += (int)(Math.random()*10);
        RightBTS rightBTS = new RightBTS(this, number);
        this.rightBTSSet.add(rightBTS);
        new Thread(rightBTS).start();
    }

    public void update(){
        if(listener != null){
            listener.update();
        }
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }
}
