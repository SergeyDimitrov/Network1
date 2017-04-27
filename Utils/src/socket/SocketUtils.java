package socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class SocketUtils {
    public static final int PORT_NUMBER = 8888;

    public static Socket connectToLocal() throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        return new Socket(inetAddress, PORT_NUMBER);
    }
}
