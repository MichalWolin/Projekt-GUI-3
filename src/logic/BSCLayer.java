package logic;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class BSCLayer {
    private HashSet<BSC> bscSet;
    private LayerLogic layerLogic;

    public BSCLayer(LayerLogic layerLogic){
        this.layerLogic = layerLogic;
        bscSet = new HashSet<>();
        addBSC();
    }

    public void addBSC(){
        layerLogic.setNumber(layerLogic.getNumber() + (int)(Math.random() * 10));
        BSC bsc = new BSC(this, layerLogic.getNumber());
        new Thread(bsc).start();
        bscSet.add(bsc);
        layerLogic.update();
    }

    public HashSet<BSC> getBSCSet(){
        return bscSet;
    }


    public void receivePDU(PDU pdu) {
        if(bscSet.stream().allMatch(bts -> bts.getAwaitingPDUs() > 5)){
            addBSC();
        }

        BSC bsc = bscSet.stream().min(Comparator.comparingInt(BSC::getAwaitingPDUs)).orElse(null);
        if(bsc != null){
            bsc.receivePDU(pdu);
        }
    }

    public void passPDU(PDU pdu){
        layerLogic.passPDU(this, pdu);
    }

    public void update(){
        layerLogic.update();
    }
}
