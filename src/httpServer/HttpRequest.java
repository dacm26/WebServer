/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpServer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author Daniel
 */
public class HttpRequest {

    private Socket ClientConn;

    public HttpRequest(Socket ClientConn) throws Exception {
        this.ClientConn = ClientConn;

    }

    public void process() throws Exception {
        DataInputStream din = new DataInputStream(ClientConn.getInputStream());
        PrintWriter out = new PrintWriter(this.ClientConn.getOutputStream());
        String request = din.readLine().trim();
        System.out.println("Request: " + request);
        StringTokenizer st = new StringTokenizer(request);
        String header = st.nextToken();
        String path = "./src/resources/";
        if (header.equals("GET")) {
            String fileName = st.nextToken();
            fileName = fileName.substring(1, fileName.length());
            System.out.println("File Name: " + fileName);
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
                StatusLine = "HTTP/1.0 200 OK";
                ContentTypeLine = "Content-type: text/html";
                BufferedReader bf = null;
                bf = new BufferedReader(new InputStreamReader(fin));
                String line;
                StringBuilder sb=new StringBuilder();
                line = bf.readLine();
                while (line != null){
                    sb.append(line);
                    line = bf.readLine();
                }
                bf.close();
                sb.append("\r\n");
                ContentBody = sb.toString();
                ContentLengthLine = (new Integer(ContentBody.length()).toString()) + "\r\n";
                /*
                 ContentLengthLine = "Content-Length: " + (new Integer(fin.available()).toString());
                 /*Aqui tiene que ir el archivo html*/
            } else {
                StatusLine = "HTTP/1.0 200 OK\r\n";
                ContentTypeLine = "Content-type: text/html\r\n";
                ContentBody = "<HTML>"
                        + "<HEAD><TITLE>404 Not Found</TITLE></HEAD>"
                        + "<BODY>404 Not Found"
                        + "</BODY></HTML>\r\n";
                ContentLengthLine = (new Integer(ContentBody.length()).toString()) + "\r\n";
            }

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println("Content-Length: " + ContentBody.length());
            out.println();
            out.println(ContentBody);//Aqui va el response
            out.flush();

            out.close();
            ClientConn.close();

        }

    }
}
