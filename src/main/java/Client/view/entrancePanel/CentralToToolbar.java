package Client.view.entrancePanel;

import Client.ClientSideConnection;
import Client.listtener.StringListener;
import Client.view.CentralPanel;

public class CentralToToolbar implements StringListener {
    private CentralPanel centralPanel;
    private RegistrationView registrationView;
    private LoginView loginView;
    private Boolean firstTime;
    private ClientSideConnection csc;

    public CentralToToolbar(CentralPanel centerPanel,ClientSideConnection csc) {
        this.csc=csc;
        this.centralPanel = centerPanel;
        registrationView = new RegistrationView(this,csc);
        loginView = new LoginView(this,csc);
    }

    public void stringEventOccurred(String string) {
        if (string.equals("registration")) {
            firstTime=true;
            regPaint(registrationView);
        }
        if (string.equals("login")) {
            firstTime=true;
            logPaint(loginView);
        }
    }

    public CentralPanel getCentralPanel() {
        return centralPanel;
    }

    public void regPaint(RegistrationView registrationView) {
        if (registrationView.isNameBool() || registrationView.isPassBool() || firstTime) {
            centralPanel.setVisible(false);
            centralPanel.removeAll();
            centralPanel.add(new RegistrationView(registrationView));
            centralPanel.setVisible(true);
            firstTime=false;
        } else {
            csc.setAccount(csc.receiveEvent());
            centralPanel.mainPanel.HomePage(csc);
        }
    }

    public void logPaint(LoginView loginView) {
        if (loginView.isNameBool() || loginView.isPassBool() || firstTime) {
            centralPanel.setVisible(false);
            centralPanel.removeAll();
            centralPanel.add(new LoginView(loginView));
            centralPanel.setVisible(true);
            firstTime=false;
        } else {
            csc.setAccount(csc.receiveEvent());
            centralPanel.mainPanel.HomePage(csc);
        }
    }

    @Override
    public String toString() {
        return "CenterToToolbarStringListener2{" +
                "centerPanel=" + centralPanel +"\n"+
                ", registrationView=" + registrationView +"\n"+
                ", loginView=" + loginView +"\n"+
                ", firstTime=" + firstTime +"\n"+
                '}';
    }
}
