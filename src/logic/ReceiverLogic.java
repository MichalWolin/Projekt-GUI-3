package logic;

import graphics.ReceiverPanel;
import interfaces.UpdateListener;
import interfaces.VRDPanelListener;

import java.util.ArrayList;
import java.util.List;

public class ReceiverLogic implements VRDPanelListener {
    private List<VRD> vrdList;
    private int vrdNumber;
    private UpdateListener listener;
    public ReceiverLogic(){
        this.vrdNumber = 999_999_999;
        vrdList = new ArrayList<>();
    }

    public void setListener(UpdateListener listener){
        this.listener = listener;
    }

    @Override
    public void addVRD() {
        vrdNumber -= (int)(Math.random()*1000);
        VRD vrd = new VRD(vrdNumber, this);
        vrd.myStart();
        vrdList.add(vrd);
    }

    @Override
    public List<VRD> getVRDs() {
        return vrdList;
    }

    public void removeVRD(VRD vrd){
        vrdList.remove(vrd);
    }

    public void receivePDU(){
        int random = (int)(Math.random()*vrdList.size());
        vrdList.get(random).addProcessedPDU();
        listener.update();
    }
}
