package br.com.tracun.app;

import br.com.tracun.auxiliaryClasses.ManageDB;
import br.com.tracun.objectClasses.Request;
import br.com.tracun.objectClasses.Response;
import br.com.tracun.auxiliaryClasses.Status;
import java.net.*;
import java.io.*;

public class DBController {

    static Socket serverSocket;
    static ServerSocket controllerSocket;
    static Socket clientSocket;
    static Socket serverMirror;
    static Request request;
    static int serverPort;
    static File file;
    static final String PATH_BACKUP = "backup\\";

    static FileInputStream is;
    static ObjectInputStream ois;

    public DBController() {
        try {
            controllerSocket = new ServerSocket(9400);
            System.out.println("Controller Says: Controller no ar");
        } catch (IOException e) {
            System.out.println("Controller Says: Nao criei o Controller... - controller");
        }
    }

    public static void main(String[] args) throws Exception {
        new DBController();
        Object req;

        while (true) {

            consisteArquivos();
            System.out.println("-----------------------------------------------------------------------------------------------");

            try {
                if (connect()) {

                    req = receive(clientSocket);
                    Response response = (Response) callServer(req);
                    send(clientSocket, response);

                } else {
                    // desconexao
                    serverSocket.close();
                    clientSocket.close();
                    controllerSocket.close();
                }

            } catch (IOException e) {
                System.out.println("Controller Says: Nao encerrou a conexao corretamente - " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //Tentar se conectar com o servidor principal, se nao conseguir, tenta conexao no auxiliar
    public static Object callServer(Object obj) {

        request = (Request) obj;
        System.out.println("Controller Says: Tipo obj - callServer: " + request.getObj().getClass());
        serverPort = chooseServer(request.getObj());
        System.out.println("Controller Says: serverPortAux - " + serverPort);

        if (serverPort == -1) {
            System.out.println("Controller Says: Porta auxiliar nao encontrada - callServer");
            return new Response(Status.CODE500);
        }

        int mirrorServerPort = callAuxServer(serverPort);
        System.out.println("Controller Says: Porta do mirror - " + mirrorServerPort);

        try {
            serverSocket = new Socket("localhost", serverPort);

            System.out.println("Controller Says: callServer - Server escolhido foi o principal - " + serverSocket);
            send(serverSocket, request);
            obj = receive(serverSocket);
            serverSocket.close();

            if (request.getFolder() == null && !request.isIsBackup()) {
                System.out.println("Controller Says: Realizando operacao no server principal - callServer: " + makeCopy(request, mirrorServerPort).getStatus());
            }
            return obj;

        } catch (IOException e) {

            if (request.getFolder() == null) {
                mirrorServerPort = serverPort;
                callAuxServer(serverPort);
            }
            System.out.println("Controller Says: Erro callServer, tentando servidor auxiliar - " + e.getMessage());
        }

        try {
            serverPort = callAuxServer(serverPort);
            serverSocket = new Socket("localhost", serverPort);

            System.out.println("Controller Says: server escolhido foi o auxiliar - " + serverSocket);
            send(serverSocket, request);
            obj = receive(serverSocket);
            serverSocket.close();

            if (request.getFolder() == null && !request.isIsBackup()) {
                System.out.println("Controller Says: Realizando operacao no server auxiliar - callServer: " + makeCopy(request, mirrorServerPort).getStatus());
            }
            return obj;

        } catch (IOException e) {
            System.out.println("Controller Says: Erro callServer, principal e auxiliar fora do ar - " + e.getMessage());
            e.printStackTrace();
            return new Response(Status.CODE503);
        }
    }

    //Realiza a copia no servidor definido no protocolo
    static Response makeCopy(Request req, int serverPort) {
        System.out.println("Controller Says: Entrei no makeCopy");
        
        Response responseServerMirror;
        int auxServer = callAuxServer(serverPort);

        try {

            serverMirror = new Socket("localhost", auxServer);
            send(serverMirror, req);
            responseServerMirror = (Response) receive(serverMirror);

            if (!responseServerMirror.getStatus().equals(Status.CODE200)) {
                //Deixa em uma fila para os arquivos serem copiados posteriormente
                if (request.getKey() != null) {
                    System.out.println("Controller Says: request.setToServerPort - " + serverPort);
                    request.setToServerPort(serverPort);
                    request.setIsBackup(true);
                    file = new File(PATH_BACKUP + request.getKey() + ".ser");
                    ManageDB.saveObj(file, request);
                }
            }

            return responseServerMirror;

        } catch (IOException e) {
            //Deixa em uma fila para os arquivos serem copiados posteriormente
            if (request.getKey() != null) {
                System.out.println("Controller Says: request.setToServerPort - " + serverPort);
                request.setToServerPort(serverPort);

                file = new File(PATH_BACKUP + request.getKey() + ".ser");
                ManageDB.saveObj(file, request);
            }

            e.printStackTrace();

            return new Response(Status.CODE503);
        }
    }

    //Mantem os arquivos nos servidores atualizados, mesmo que algum tenha ficado off
    public static void consisteArquivos() {
        System.out.println("Controller Says: Entrei no consisteArquivos");
        try {
            File file = new File("backup\\");

            File[] listFiles;
            listFiles = file.listFiles();
            Socket socket;
            Response response;

            for (File listFile : listFiles) {
                is = new FileInputStream(listFile);
                ois = new ObjectInputStream(is);
                Request req = (Request) ois.readObject();
                System.out.println("Controller Says: Porta do backup - " + req.getToServerPort());
                socket = new Socket("localhost", req.getToServerPort());
                send(socket, req);
                response = (Response) receive(socket);

                if (response.getStatus().equals(Status.CODE200) || response.getStatus().equals(Status.CODE409)) {
                    is.close();
                    ois.close();
                    System.out.println("Controller Says: Excluindo arquivo \\backup - " + req.getKey() + ".ser");
                    file = new File("backup\\" + req.getKey() + ".ser");
                    System.out.println("Controller Says: file exist - " + file.exists());
                    System.out.println("Controller Says: nome do arquivo a deletar - " + file.getName());
                    System.out.println("Controller Says: " + file.toPath() + " FILE>DELETE - " + file.delete());
                }

                System.out.println("Controller Says: Resposta da tentativa de acertar arquivos - " + response.getStatus());

                is.close();
                ois.close();
            }
        } catch (Exception e) {

            try {
                is.close();
                ois.close();
            } catch (IOException io) {
                System.out.println("Controller Says: Erro ao fechar conexao com arquivo");
            }

            System.out.println("Controller Says: Erro no consisteArquivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int chooseServer(Object obj) {

        switch (obj.getClass().getName()) {
            case "objectClasses.Note":
                return 9500;
            case "objectClasses.User":
                return 9600;
            case "objectClasses.Contact":
                return 9700;
            default:
                return -1;
        }
    }

    public static void send(Socket socket, Object obj) {
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Controller Says: Excecao no OutputStream(send) - " + e.getMessage());
        }
    }

    public static Object receive(Socket socket) {
        ObjectInputStream in;
        Object obj = null;

        try {
            in = new ObjectInputStream(socket.getInputStream());
            obj = in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Controller Says: Excecao no InputStream(receive) - " + e.getMessage());
        }
        return obj;
    }

    //Retorna o servidor auxiliar do servidor principal 
    private static int callAuxServer(int socketPort) {
        switch (socketPort) {
            case 9500:
                return 9600;
            case 9600:
                return 9700;
            case 9700:
                return 9500;
            default:
                return 9500;
        }
    }

    static boolean connect() {
        boolean ret;
        try {
            clientSocket = controllerSocket.accept();
            return true;
        } catch (IOException e) {
            System.out.println("Controller Says: Erro de connect - " + e.getMessage());
            return false;
        }
    }
}
