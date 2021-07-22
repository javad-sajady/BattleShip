package Client;

import server.logic.Account;

import java.io.*;
import java.net.Socket;

public class ClientSideConnection {

    private Socket socket;
    private BufferedReader dataIn;
    private PrintWriter dataOut;
    private String id;
    private Account account;
    private String rival;

    public ClientSideConnection() {
        this.account= new Account("","");
        try {
            socket = new Socket("localhost", 8000);
            dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            id =receiveEvent();
            System.out.println("connected to server as : " + id);
        } catch (IOException e) {
            System.out.println("io Exception from csc " + e);
        }
    }

    public void sendEvent(String s) {
        s = id + "[" + s;
        try {
//            OutputStream os =socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(s);
            oos.flush();
//            os.flush();
        } catch (IOException e) {
            System.out.println("IOE Exception from csc sendEvent " + e);
        }
//        System.out.println("we send " + s);
    }

    public String receiveEvent() {
        ClientWait.waitForServer(socket);
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String s=(String) ois.readObject();
//            System.out.println("we receive :"+s);
            return s;
        } catch (IOException e) {
            System.out.println("IOE Exception from csc receiveEvent " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException from csc receiveEvent " + e);
        }
        return null;
    }

    public Socket getSocket() {
        return socket;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(String a) {
        String[] A=a.split("\\[");
        account.SetInf(A[0],A[1],Integer.valueOf(A[2]),Integer.valueOf(A[3]));
    }

    public String getRival() {
        return rival;
    }

    public void setRival(String rival) {
        this.rival = rival;
    }



    @Override
    public String toString() {
        return "ClientSideConnection{" +
                "socket=" + socket +
                ", dataIn=" + dataIn +
                ", dataOut=" + dataOut +
                ", id=" + id +
                '}';
    }
}
