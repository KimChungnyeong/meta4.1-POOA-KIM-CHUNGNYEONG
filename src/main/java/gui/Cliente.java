package gui;

import kanvan.FlujoTrabajo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
    private FlujoTrabajo flujoTrabajo = null;
    private String HOST = "192.168.1.12";
    private int PUERTO = 666;

    public Cliente(String HOST, int PUERTO) {
        this.HOST = HOST;
        this.PUERTO = PUERTO;
        this.flujoTrabajo = new FlujoTrabajo("Default");
    }
    public FlujoTrabajo getFlujoDeTrabajo() {
        try{
            Socket socket = new Socket(HOST, PUERTO);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("GET FLU");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            flujoTrabajo = (FlujoTrabajo) objectInputStream.readObject();
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return flujoTrabajo;
    }

    public void setFlujoDeTrabajo(FlujoTrabajo flujoDeTrabajo){
        this.flujoTrabajo = flujoDeTrabajo;
    }

    public void enviarMensaje(String mensaje) {
        try {
            Socket socket = new Socket(HOST, PUERTO);
            System.out.println("Cliente conectado");

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(mensaje);
            System.out.println("El cliente envio el siguiente mensaje: " + mensaje);

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();

            if (object.getClass().getName().equalsIgnoreCase("FlujoDeTrabajo")) {
                this.flujoTrabajo = (FlujoTrabajo) object;
                System.out.println("El servidor respondio el siguiente flujo de trabajo: " + this.flujoTrabajo);
            } else {
                System.out.println("El servidor respondio el siguiente objeto: " + object);
            }
            //Cerrarmos objetos.
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
            System.out.println("Cliente desconectado");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
