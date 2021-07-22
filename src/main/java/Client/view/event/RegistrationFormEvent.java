package Client.view.event;

import java.io.IOException;
import java.util.EventObject;

public class RegistrationFormEvent extends EventObject {
    private String name;
    private String password1;
    private String password2;

    public RegistrationFormEvent(Object source, String name, String password1, String password2) {
        super(source);
        this.name=name;
        this.password1=password1;
        this.password2 = password2;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }

}