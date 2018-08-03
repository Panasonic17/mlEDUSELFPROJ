package data.source;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SocketTestSource {

    public static double function(double x) {
        int a = 3;
        int b = 0;
        return a * x + b + Math.random() * 0;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Random random = new Random();

        int portNumber = 9999;

        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        System.out.println("accept");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

        while (true) {

            Thread.sleep(3000);
//            System.out.println("send");
            out.println(function(random.nextInt(100)));
        }
    }
}
