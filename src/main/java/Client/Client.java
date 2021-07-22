package Client;

import Client.view.MainFrame;

public class Client {
    private ClientSideConnection csc;

    public void connectToServer(){
        csc=new ClientSideConnection();
    }

    public static void main(String[] args) {
        ClientSideConnection csc=new ClientSideConnection();
        MainFrame graphicAgent=new MainFrame(csc);
        System.out.println("we are connected");
    }
}
