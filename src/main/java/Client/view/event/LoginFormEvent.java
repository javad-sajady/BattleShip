package Client.view.event;

import java.io.IOException;
import java.util.EventObject;

public class LoginFormEvent extends EventObject {
    private String name;
    private String pass;

    public LoginFormEvent(Object source, String name, String pass) {
        super(source);
        this.name=name;
        this.pass=pass;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return pass;
    }

    //    it just used for the saving
//    public Account toAccount() throws IOException {
//        return new FileManager().getName(name);
//    }


}