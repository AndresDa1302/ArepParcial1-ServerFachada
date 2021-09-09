package edu.eci.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFachada {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Fachada fachada=new Fachada();
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35001.");
            System.exit(1);
        }
        
        try {
            System.out.println("Listo para recibir ...");
            while (true) {
                Socket clientSocket;
                clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                String recurso;
                PrintWriter out;
                while ((inputLine = in.readLine()) != null) {

                    System.out.println(inputLine);
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    String respuesta=fachada.getOperation(inputLine);
                    out.println(respuesta);
                    out.close();
                    
                    if (inputLine.contains("GET")) {
                        recurso = inputLine.split(" ")[1];
                        recurso = recurso.substring(1);
                        System.out.println(recurso);
                    }
                    if(!in.ready()){
                         break;
                    }
                }
                in.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35001; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
