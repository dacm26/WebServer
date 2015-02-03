/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpServer;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Daniel
 */
public class httpServer {

    public static void main(String args[]) throws Exception {
        ServerSocket soc = new ServerSocket(8080);
         while (true) {
         Socket inSoc = soc.accept();
         HttpRequest request = new HttpRequest(inSoc);
         request.process();
         }
        /*BufferedReader bf = null;
        bf = new BufferedReader(new InputStreamReader(new FileInputStream("./src/resources/index.html")));
        String line;
        do {
            line = bf.readLine();
            System.out.println(line);
        } while (line != null);
        bf.close();*/
        

    }
}
