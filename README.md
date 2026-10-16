# TP1-DAR2026 — Sockets TCP en mode connecté

Samet Youssef  — LSI 3 — Développement d'applications réparties

Structure : `src/serverPackage/Server.java` et `src/clientPackage/Client.java`.
Chaque activité correspond à un commit/tag Git (`activite-1-1`, `activite-1-2`, `activite-1-3`).

## Compilation / exécution
```
javac -d out src/serverPackage/Server.java src/clientPackage/Client.java
java -cp out serverPackage.Server [IP] [port]     # défaut : IP réelle de la machine, port 1234
java -cp out clientPackage.Client [IP_serveur] [port]   # défaut : localhost, 1234
```

## Activité 1-1 — Première connexion
### Étape 1 : `git --version`
>  <img width="1481" height="760" alt="1" src="https://github.com/user-attachments/assets/a9ee7b48-d7fb-4912-9146-f13f24c9b08e" />

> Description : Git est installé, version affichée.

### Étape 2 : `java --version`
> <img width="1481" height="760" alt="1" src="https://github.com/user-attachments/assets/63f1abd9-ed67-47c5-a6d8-5f0da958b659" />

> Description : JDK >= 1.8 installé et présent dans le PATH.

### Étape 3 : Serveur
> <img width="1765" height="648" alt="serveur" src="https://github.com/user-attachments/assets/df7ecf8a-3f61-4444-a328-25e965c67970" />
  
> Interprétation : le serveur affiche « Je suis un serveur en attente… » puis reste bloqué sur `accept()` jusqu'à l'arrivée d'un client ; il affiche ensuite « un client est connecté » et ferme ses sockets.

### Étape 4 : Client
> <img width="1773" height="689" alt="client" src="https://github.com/user-attachments/assets/3f00c04b-70b8-4f05-9f72-d3b9a9a529f5" />

> Interprétation : le client affiche « pas encore connecté », ouvre un `Socket` vers `localhost:1234`, puis « je suis un client connecté ». Test ensuite avec une adresse IP à la place de `localhost`.

## Activité 1-2 — Service de calculatrice (x * 5)
- Le client saisit un entier `x`, l'envoie avec `DataOutputStream.writeInt`, le serveur le lit avec `DataInputStream.readInt`, calcule `x*5` et renvoie le résultat.
- `DataInputStream/DataOutputStream` transmettent les 4 octets de l'entier (complément à 2) : les **négatifs** sont donc correctement gérés.
- La boucle s'arrête quand l'utilisateur saisit `0` : les flux et sockets sont fermés des deux côtés.

> 

## Activité 1-3 — Deux machines + calculatrice améliorée
### Étape 1 : deux machines distinctes
- Serveur : `ServerSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 1234))` (ou IP passée en argument).
- Client : `socket.connect(new InetSocketAddress(ipServeur, 1234))`.
- Trouver l'IP du serveur : `ipconfig` (Windows) / `ip a` (Linux). Les deux machines doivent être sur le même réseau local, et le pare-feu du serveur doit autoriser le port 1234.

> 

### Étape 2 : menu 4 opérations
Menu : 1 Addition, 2 Soustraction, 3 Multiplication, 4 Division, 0 Quitter.
Protocole : le client envoie `choix` puis `x` ; le serveur calcule (`x op 5`) et renvoie un booléen de statut + le résultat (`double`).


