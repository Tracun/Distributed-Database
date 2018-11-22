package br.com.tracun.app;

import br.com.tracun.objectClasses.Response;
import br.com.tracun.objectClasses.Request;
import br.com.tracun.auxiliaryClasses.ManageDB;
import br.com.tracun.auxiliaryClasses.Status;
import java.io.*;
import java.net.*;

public class Server2 {

    static final String PATH_DB2 = "db2\\";
    static final String PATH_USERS = "db2\\Users\\";
    static final int MIRROR_PORT = 9700;
    static ServerSocket serverSocket;
    static Socket serverMirror;
    static Socket clientSocket;
    static DBController controller;
    static FileOutputStream fos = null;
    static ObjectOutputStream oos = null;
    static String fileKey = null;
    static File file = null;
    static Request req = null;
    static Response resp = null;

    static FileWriter fileWriter = null;
    static BufferedWriter gravarNoArq = null;

    public Server2() {

        try {
            serverSocket = new ServerSocket(9600);
            System.out.println("Server 2 says: DB Server Socket 2 no ar");
        } catch (IOException e) {
            System.out.println("Server 2 says: Nao criei o server socket...");
        }
    }

    public static void main(String args[]) throws IOException {
        new Server2();

        while (true) {
            System.out.println("-----------------------------------------------------------------------------------------------");
            if (connect()) {

                System.out.println("Server 2 says: Aceitei conexao");
                try {

                    Response response;
                    req = (Request) controller.receive(clientSocket);

                    //Verifica a operacao escolhida para tomar uma decisao                    
                    switch (req.getOperation()) {
                        case "create":
                            response = ManageDB.create(req.getObj(), PATH_DB2);
                            sendResponse(response);
                            break;
                        case "update":
                            response = ManageDB.update(req.getObj(), PATH_DB2);
                            sendResponse(response);
                            break;
                        case "delete":
                            response = ManageDB.delete(req.getObj(), PATH_DB2);
                            sendResponse(response);
                            break;
                        case "consult":
                            System.out.println("consult: " + req.getObj().getClass().toString());
                            response = ManageDB.consult(req.getObj(), PATH_DB2);
                            sendResponse(response);
                            break;
                        case "consultAll":
                            response = ManageDB.consultAll(PATH_DB2 + req.getFolder());
                            sendResponse(response);
                            break;
                        default:
                            sendResponse(new Response(Status.CODE400));
                            System.out.println("Server 2 says: Operacao solicitada invalida");
                    }

                    System.out.println("Server 2 says: Operacao solicitada - " + req.getOperation());

                } catch (Exception e) {
                    clientSocket.close();
                    serverSocket.close();
                    sendResponse(new Response(Status.CODE500));
                    System.out.println("Server 2 says: Erro Main- " + e.getMessage());
                    e.printStackTrace();
                }

            } else {
                try {// desconexao // desconexao
                    clientSocket.close();
                    serverSocket.close();
                } catch (Exception e) {
                    System.out.println("Server 2 says: Nao encerrou a conexao corretamente - :" + e.getMessage());
                }
            }

        }
    }

    static void sendResponse(Response response) {
        controller.send(clientSocket, response);
    }

    //Aceita a conexao do cliente
    static boolean connect() {
        boolean ret;
        try {
            clientSocket = new Socket();
            clientSocket = serverSocket.accept();
            return true;
        } catch (Exception e) {
            System.out.println("Server 2 says: Erro de connect - " + e.getMessage());
            return false;
        }
    }
}
