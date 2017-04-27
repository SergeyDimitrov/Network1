import exception.WrongMatrixFormatException;
import socket.SocketUtils;
import socket.SocketWrapper;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SocketUtils.PORT_NUMBER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                SocketWrapper socket = new SocketWrapper(serverSocket.accept());
                Matrix a = Matrix.readFromSocket(socket);
                Matrix b = Matrix.readFromSocket(socket);
                Matrix.writeToSocket(Matrix.multiply(a, b), socket);
            } catch (WrongMatrixFormatException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
