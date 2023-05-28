package logic;

import interfaces.ReceiverListener;
import interfaces.VBDPanelListener;

import java.util.ArrayList;
import java.util.List;

public class SenderLogic implements VBDPanelListener {
    private List<VBD> vbdList;
    private int vbdNumber;
    private LayerLogic layerLogic;
    private ReceiverListener listener;

    public SenderLogic(LayerLogic layerLogic){
        this.vbdNumber = 100_000_000;
        vbdList = new ArrayList<>();
        this.layerLogic = layerLogic;
    }

    public void setListener(ReceiverListener listener){
        this.listener = listener;
    }

    public int getReceiver(){
        if(listener != null){
            List<VRD> list = listener.getVRDList();
            if(list.size() == 0){
                return 0;
            }else{
                return list.get((int)(Math.random()*list.size())).getVRDNumber();
            }
        }
        return 0;
    }

    @Override
    public void addVBD() {
        vbdNumber += (int)(Math.random()*1000);
        VBD vbd = new VBD(this, vbdNumber);
        vbd.myStart();
        vbdList.add(vbd);
    }

    @Override
    public List<VBD> getVBDs() {
        return vbdList;
    }

    public void removeVBD(VBD vbd) {
        vbdList.remove(vbd);
    }

    public void passPDU(PDU pdu){
        layerLogic.queuePDU(pdu);
    }
}
