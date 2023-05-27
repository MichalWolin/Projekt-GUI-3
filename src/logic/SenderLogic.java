package logic;

import interfaces.VBDPanelListener;

import java.util.ArrayList;
import java.util.List;

public class SenderLogic implements VBDPanelListener {
    private List<VBD> vbdList;
    private int vbdNumber;
    private LayerLogic layerLogic;

    public SenderLogic(LayerLogic layerLogic){
        this.vbdNumber = 1_000_000;
        vbdList = new ArrayList<>();
        this.layerLogic = layerLogic;
    }

    @Override
    public void addVBD() {
        vbdNumber += (int)(Math.random()*1000);
        VBD vbd = new VBD(this, vbdNumber);
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
