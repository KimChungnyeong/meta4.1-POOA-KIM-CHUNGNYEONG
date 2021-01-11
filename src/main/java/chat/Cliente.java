package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    static int puerto = 7777;

    public static void main (String [] args) throws UnknownHostException, IOException {
        InetAddress IP = InetAddress.getByName("localhost");
        final Scanner leer = new Scanner(System.in);
        Socket socket = new Socket(IP, puerto);

        final DataInputStream Dis = new DataInputStream(socket.getInputStream());
        final DataOutputStream Dos = new DataOutputStream(socket.getOutputStream());

        Thread sendMessage = new Thread(new Runnable() {
            public void run() {
                while(true){
                    String message = leer.nextLine();
                    try{
                        Dos.writeUTF(message);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread readMessage = new Thread(new Runnable() {
            public void run() {
                while(true){
                    try{
                        String message = Dis.readUTF();
                        System.out.println(message);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
