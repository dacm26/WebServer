/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Daniel
 */
public class httpServer {

    public static void main(String args[]) {
        ArrayList<Socket> sockets = new ArrayList<>();
        Socket socket;
        int portNumber = 8080;
        boolean listening = true;
         
        try { 
            ServerSocket serverSocket = new ServerSocket(portNumber);
            while (listening) {
                System.out.println("Esperando por clientes");
                socket = serverSocket.accept();                                 
                System.out.println("Puerto: " + socket.getPort());
                sockets.add(socket);                
                new HttpRequest(socket).start();
            }
        } catch (IOException e) {
            System.err.println("No se puede escuchar en puerto # " + portNumber);
            System.exit(-1);
        } catch (Exception ex) {
            Logger.getLogger(httpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
