package Client.listtener.loginCreate;

import Client.ClientSideConnection;
import Client.listtener.FormListener;
import Client.view.event.LoginFormEvent;
import Client.view.event.RegistrationFormEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LoginFormListener implements FormListener {

    public boolean nameBoolean;
    public boolean passBoolean;
    public ClientSideConnection csc;

    public LoginFormListener(ClientSideConnection csc) throws IOException {
        this.csc=csc;
        nameBoolean = false;
        passBoolean = false;
    }

    public boolean isNameBoolean() {
        return nameBoolean;
    }

    public boolean isPassBoolean() {
        return passBoolean;
    }

    @Override
    public void eventOccurred(LoginFormEvent formEvent) throws IOException {
        String s="Login"+"["+formEvent.getName()+"["+formEvent.getPassword();
        csc.sendEvent(s);
        String t= csc.receiveEvent();
        ArrayList<String> A = new ArrayList<String>(Arrays.asList(t.split(String.valueOf("\\["))));
        if(A.get(2).equals("true")){
            nameBoolean = true;
        }
        if(A.get(3).equals("true")){
            passBoolean=true;
        }
    }

    @Override
    public void eventOccurred(RegistrationFormEvent formEvent) throws IOException {

    }

}
