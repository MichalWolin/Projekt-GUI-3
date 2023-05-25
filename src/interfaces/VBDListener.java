package interfaces;

import logic.VBD;

import java.util.List;

public interface VBDListener {
    void addVBD();
    void removeVBD(VBD vbd);
    List<VBD> getVBDs();
}
