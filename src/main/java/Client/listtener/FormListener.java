package Client.listtener;

import Client.view.event.LoginFormEvent;
import Client.view.event.RegistrationFormEvent;

import java.io.IOException;

public interface FormListener {

    void eventOccurred(RegistrationFormEvent formEvent) throws IOException;
//    here we should check the information

    void eventOccurred(LoginFormEvent formEvent) throws IOException;
}
