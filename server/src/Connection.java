import Exceptions.InvalidCredentialsException;
import Exceptions.SignUpException;
import Service.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Connection extends UnicastRemoteObject implements IConnection {
    CredentialsFile cf;
    ArrayList<Client> clientList;
    IVODService movies = new VODService();

    protected Connection() throws RemoteException, IOException {
        super();
        //il s'agit du fichier qui enregistrer tout les identifiants et mails des clients déja inscrits
        cf = new CredentialsFile("./src/credentials.txt");
        clientList = cf.readFile();
    }

    public boolean signUp(String mail, String pwd) throws RemoteException, SignUpException {
        try {
            //on vérifie que l'adresse mail n'est pas déja présente dans la liste des clients inscrits
            SignUpException.controle(mail, clientList);
            //si le client n'existe pas déja, on le crée
            Client newClient = new Client(mail, pwd);
            //puis on l'ajoute à la liste des clients inscrits
            clientList.add(newClient);
            //on écrit les identifiants dans le fichier coté serveur
            cf.writeFile(newClient);
            return true;
        }
        catch(SignUpException signUpException){
            signUpException.printStackTrace();
            return false;
        }
    }

    public IVODService login(String mail, String pwd) throws RemoteException, InvalidCredentialsException {
        try {
            //on vérifie que l'adresse mail saisie correspond bien à un client existant
            //et que le mot de passe saisi correspond à cette adresse mail
            InvalidCredentialsException.controle(mail, pwd, clientList);
            //on retourne la liste des films
            return this.movies;
        }
        catch(InvalidCredentialsException invalidCredentialsException){
            invalidCredentialsException.printStackTrace();
            return null;
        }
    }

    public ArrayList<Client> getClientList() {
        return clientList;
    }

    public IVODService getMovies() {
        return movies;
    }
}
