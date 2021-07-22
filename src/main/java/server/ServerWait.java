package server;

import java.io.IOException;
import java.net.Socket;

public class ServerWait {

    public static void waitForClient(Socket socket) {
        long start = System.currentTimeMillis();
        while (true) {
            try {

                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("server wait Exception " + e);
            }
            try {
                if(socket.getInputStream().available()==0){
                    continue;
                }
            }catch (IOException e){
                System.out.println("IOExeption in server wait "+e);
                break;
            }
            int a=0;
            break;
        }
    }
}
