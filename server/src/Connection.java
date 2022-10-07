import Exceptions.InvalidCredentialsException;
import Exceptions.SignUpException;
import service.*;

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
        cf = new CredentialsFile("./src/credentials.txt");
        clientList = cf.readFile();
    }

    public boolean signUp(String mail, String pwd) throws RemoteException, SignUpException {
        try {
            SignUpException.controle(mail, clientList);
            Client newClient = new Client(mail, pwd);
            clientList.add(newClient);
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
            InvalidCredentialsException.controle(mail, pwd, clientList);
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
