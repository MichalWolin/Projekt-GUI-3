package logic;

import interfaces.ReceiverListener;
import interfaces.UpdateListener;
import interfaces.VRDPanelListener;

import java.util.ArrayList;
import java.util.List;

public class ReceiverLogic implements VRDPanelListener, ReceiverListener {
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

    public void receivePDU(PDU pdu) {
        try {
            if (vrdList.size() == 0 || pdu.getPDU().substring(0, 2).equals("00")) {
                throw new ReceiverException("Dany odbiorca nie istnieje!");
            }
            String length = pdu.getPDU().substring(0,2);
            int numberLength = Integer.parseInt(length) + 3;

            String phoneNumber = pdu.getPDU().substring(4, numberLength+5).replace("F", "");
            String reversedPhoneNumber = reversePairs(phoneNumber);

            boolean foundMatchingVrd = false;
            for (VRD vrd : vrdList) {
                if (vrd.getVRDNumber() == Integer.parseInt(reversedPhoneNumber)) {
                    vrd.addProcessedPDU();
                    listener.update();
                    foundMatchingVrd = true;
                    break;
                }
            }

            if (!foundMatchingVrd) {
                throw new ReceiverException("Dany odbiorca nie istnieje!");
            }
        } catch (ReceiverException e) {
            System.out.println(e.getMessage());
        }
    }

    private String reversePairs(String phoneNumber) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < phoneNumber.length(); i += 2) {
            if (i + 1 < phoneNumber.length()) {
                sb.append(phoneNumber.charAt(i + 1));
            }
            sb.append(phoneNumber.charAt(i));
        }
        return sb.toString();
    }




    @Override
    public List<VRD> getVRDList() {
        return vrdList;
    }
}
