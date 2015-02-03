/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class httpServer {

    public static void main(String args[]) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(8080);
            new HttpRequest(serverSocket).start();

        } catch (IOException ex) {
            Logger.getLogger(httpServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(httpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
