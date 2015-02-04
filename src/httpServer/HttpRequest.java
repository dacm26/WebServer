/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpServer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class HttpRequest extends Thread {

    private Socket socket;

    public HttpRequest(Socket socket) throws Exception {
        this.socket = socket;

    }

    @Override
    public void run() {
        DataInputStream din = null;
        PrintWriter out = null;
        try {
            din = new DataInputStream(this.socket.getInputStream());
            out = new PrintWriter(this.socket.getOutputStream());
            while (true) {
                String request = din.readLine().trim();
                //System.out.println("Request: " + request);
                StringTokenizer st = new StringTokenizer(request);
                String header = st.nextToken();
                String path = "./src/resources/";
                if (header.equals("GET")) {
                    String fileName = st.nextToken();
                    fileName = fileName.substring(1, fileName.length());
                    FileInputStream fin = null;
                    boolean fileExist = true;
                    try {
                        fin = new FileInputStream(path + fileName);
                    } catch (Exception ex) {
                        fileExist = false;
                    }

                    String ServerLine = "Simple HTTP Server";
                    String StatusLine = null;
                    String ContentTypeLine = null;
                    String ContentLengthLine = null;
                    String ContentBody = null;

                    if (fileExist) {
                        StatusLine = "HTTP/1.0 200 OK\r\n";
                        ContentTypeLine = "Content-type: text/html\r\n";
                        BufferedReader bf = null;
                        bf = new BufferedReader(new InputStreamReader(fin));
                        String line;
                        StringBuilder sb = new StringBuilder();
                        line = bf.readLine();
                        while (line != null) {
                            sb.append(line);
                            line = bf.readLine();
                        }
                        bf.close();
                        sb.append("\r\n");
                        ContentBody = sb.toString();
                        ContentLengthLine = (new Integer(ContentBody.length()).toString()) + "\r\n";
                    } else {
                        StatusLine = "HTTP/1.0 400 OK\r\n";
                        ContentTypeLine = "Content-type: text/html\r\n";
                        ContentBody = "<HTML>"
                                + "<HEAD><TITLE>404 Not Found</TITLE></HEAD>"
                                + "<BODY>404 Not Found"
                                + "</BODY></HTML>\r\n";
                        ContentLengthLine = (new Integer(ContentBody.length()).toString()) + "\r\n";
                    }

                    out.print(StatusLine);
                    out.print(ContentTypeLine);
                    out.println("Content-Length: " + ContentBody.length());
                    out.println();
                    out.println(ContentBody);//Aqui va el response
                    out.flush();

                    

                }

            }

        } catch (IOException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NoSuchElementException ex){
        }finally {
            try {
                din.close();
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
