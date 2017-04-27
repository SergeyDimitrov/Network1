import exception.WrongMatricesSizesException;
import exception.WrongMatrixFormatException;
import socket.SocketUtils;
import socket.SocketWrapper;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            Matrix a = Matrix.readFromFile("C:\\Users\\pb\\IdeaProjects\\Network1\\Client\\a.txt");
            Matrix b = Matrix.readFromFile("C:\\Users\\pb\\IdeaProjects\\Network1\\Client\\b.txt");
            Matrix.checkForMultiplicable(a, b);
            Socket socket = SocketUtils.connectToLocal();
            SocketWrapper socketWrapper = new SocketWrapper(socket);
            Matrix.writeToSocket(a, socketWrapper);
            Matrix.writeToSocket(b, socketWrapper);
            Matrix.writeToFile(Matrix.readFromSocket(socketWrapper), "C:\\Users\\pb\\IdeaProjects\\Network1\\Client\\c.txt");
        } catch (WrongMatrixFormatException e) {
            FileUtils.writeToFile("C:\\Users\\pb\\IdeaProjects\\Network1\\Client\\c.txt", "Wrong matrix format: " + e.getMessage());
        } catch (IOException e) {
            FileUtils.writeToFile("C:\\Users\\pb\\IdeaProjects\\Network1\\Client\\c.txt", "Unexpected error occurred");
        } catch (WrongMatricesSizesException e) {
            FileUtils.writeToFile("C:\\Users\\pb\\IdeaProjects\\Network1\\Client\\c.txt", "Matrices cannot be multiplied");
        }
    }
}
