package clientPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        String ipServeur = (args.length > 0) ? args[0] : "localhost";
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 1234;

        try {
            System.out.println("Je suis un client pas encore connecté…");
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ipServeur, port), 5000); // timeout 5 s
            System.out.println("je suis un client connecté à " + ipServeur + ":" + port);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);

            int choix;
            do {
                System.out.println("\n===== Calculatrice =====");
                System.out.println("1. Addition");
                System.out.println("2. Soustraction");
                System.out.println("3. Multiplication");
                System.out.println("4. Division");
                System.out.println("0. Quitter");
                System.out.print("Votre choix : ");
                choix = sc.nextInt();

                if (choix == 0) {
                    out.writeInt(0);
                    out.flush();
                } else if (choix >= 1 && choix <= 4) {
                    System.out.print("Entrez un entier x : ");
                    int x = sc.nextInt();
                    out.writeInt(choix);
                    out.writeInt(x);
                    out.flush();

                    if (in.readBoolean()) {
                        System.out.println("Résultat = " + in.readDouble());
                    } else {
                        System.out.println("Erreur : " + in.readUTF());
                    }
                } else {
                    System.out.println("Choix invalide.");
                }
            } while (choix != 0);

            System.out.println("Fermeture de la connexion.");
            in.close();
            out.close();
            socket.close();
            sc.close();
        } catch (IOException e) {
            System.out.println("Impossible de joindre le serveur : " + e.getMessage());
        }
    }
}
