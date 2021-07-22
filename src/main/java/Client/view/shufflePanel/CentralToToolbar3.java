package Client.view.shufflePanel;

import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.view.CentralPanel;

public class CentralToToolbar3 implements StringListener {
    private CentralPanel centerPanel;
    private ClientSideConnection csc;
    public ToolsBar3 toolsBar;
    private ShipPaint shipPaint;
    private String lastString;

    public CentralToToolbar3(CentralPanel centralPanel, ClientSideConnection csc) {
        this.centerPanel = centralPanel;
        this.csc=csc;
        lastString="";
    }
    public void setInf(ToolsBar3 toolsBar, ShipPaint shipPaint){
        this.toolsBar=toolsBar;
        this.shipPaint = shipPaint;
    }

    public CentralPanel getCenterPanel() {
        return centerPanel;
    }

    @Override
    public void stringEventOccurred(String string) {
        if (string.equals("shuffle")) {
            csc.sendEvent("Shuffle");
            String s=csc.receiveEvent();
            if(!s.equals(lastString)){
                lastString=s;
                toolsBar.setTimer();
                shipPaint.DecodeShip(s);
            }
        }
        if (string.equals("ready")) {
            csc.sendEvent("Ready");
            String s=csc.receiveEvent();
            if(s.equals("Wait")){
                s=csc.receiveEvent();
            }
            centerPanel.mainPanel.GamePage(csc);
            centerPanel.mainPanel.GameBegin();
        }
    }

}
