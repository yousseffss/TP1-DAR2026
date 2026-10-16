package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private static final int CONSTANTE = 5;

    public static void main(String[] args) {
        try {
            InetAddress adresse = (args.length > 0)
                    ? InetAddress.getByName(args[0])
                    : InetAddress.getLocalHost();
            int port = (args.length > 1) ? Integer.parseInt(args[1]) : 1234;


            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(adresse, port));
            System.out.println("Je suis un serveur en attente la connexion d'un client ");
            System.out.println("Ecoute sur " + adresse.getHostAddress() + ":" + port);

            Socket socket = serverSocket.accept();
            System.out.println("un client est connecté : " + socket.getInetAddress().getHostAddress());

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true) {
                int choix = in.readInt();
                if (choix == 0) break;
                int x = in.readInt();

                double resultat;
                switch (choix) {
                    case 1: resultat = x + CONSTANTE; break;
                    case 2: resultat = x - CONSTANTE; break;
                    case 3: resultat = x * CONSTANTE; break;
                    case 4: resultat = (double) x / CONSTANTE; break;
                    default:
                        out.writeBoolean(false);
                        out.writeUTF("Opération inconnue");
                        out.flush();
                        continue;
                }
                System.out.println("Opération " + choix + " sur x=" + x + " -> " + resultat);
                out.writeBoolean(true);
                out.writeDouble(resultat);
                out.flush();
            }
            System.out.println("Le client a quitté : fermeture des sockets");

            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
