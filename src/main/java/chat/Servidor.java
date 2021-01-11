package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class Servidor {
    static Vector<ManejoCliente> Clientes = new Vector<ManejoCliente>();
    static int i = 0;
    public static void main(String [] args) throws IOException {
        int port = 7777;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket;

        while(true){
            socket = serverSocket.accept();
            System.out.println("Nuevo cliente entrado: " + socket);

            DataInputStream Dis = new DataInputStream(socket.getInputStream());
            DataOutputStream Dos = new DataOutputStream(socket.getOutputStream());

            ManejoCliente match = new ManejoCliente(socket,"cliente " + i, Dis,Dos);
            Thread thread = new Thread(match);

            System.out.println("Cliente conectado ¡Saludalo!");
            Clientes.add(match);
            thread.start();
            i++;
        }
    }
}

class ManejoCliente implements Runnable{
    String nombre;
    Socket socket;
    boolean conectado;
    DataInputStream Dis;
    DataOutputStream Dos;

    public ManejoCliente(Socket socket, String nombre, DataInputStream Dis, DataOutputStream Dos){
        this.Dis = Dis;
        this.Dos = Dos;
        this.socket = socket;
        this.nombre = nombre;
        this.conectado = true;
    }

    public void run(){
        String received;
        while(true){
            try{
                received = Dis.readUTF();
                System.out.println(received);
                if(received.equals("desconectado")){
                    this.conectado = false;
                    this.socket.close();
                    break;
                }

                StringTokenizer stringToken = new StringTokenizer(received,"-");
                String messageToSend = stringToken.nextToken();
                String client = stringToken.nextToken();

                for(ManejoCliente toSearch : Servidor.Clientes){
                    if(toSearch.nombre.equals(client) && toSearch.conectado == true){
                        toSearch.Dos.writeUTF(this.nombre + " : " + messageToSend);
                        break;
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        try{
            this.Dis.close();
            this.Dos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
