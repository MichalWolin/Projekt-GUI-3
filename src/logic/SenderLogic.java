package logic;

import interfaces.VBDListener;

import java.util.ArrayList;
import java.util.List;

public class SenderLogic implements VBDListener {
    private List<VBD> vbdList;
    private int vbdNumber;
    public SenderLogic(){
        this.vbdNumber = 1_000_000;
        vbdList = new ArrayList<>();
    }

    @Override
    public void addVBD() {
        vbdNumber += (int)(Math.random()*1000);
        VBD vbd = new VBD(vbdNumber);
        vbdList.add(vbd);
    }

    @Override
    public void removeVBD(VBD vbd) {
        vbdList.remove(vbd);
    }

    @Override
    public List<VBD> getVBDs() {
        return vbdList;
    }
}
