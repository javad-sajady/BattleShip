package Client;

import java.io.IOException;
import java.net.Socket;

public class ClientWait {

    public static void waitForServer(Socket socket) {
        long start = System.currentTimeMillis();
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("client wait Exception " + e);
            }
            try {
                if(socket.getInputStream().available()==0){
                    continue;
                }
            }catch (IOException e){
                System.out.println("IOExeption in client wait "+e);
                break;
            }
            int a=0;
            break;
        }
    }
}
