import Exceptions.InvalidCredentialsException;
import Exceptions.SignUpException;
import service.*;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Display {

    Scanner sc = new Scanner(System.in);
    IVODService iVODService = null;
    Bill bill = null;
    List<MovieDesc> movieDescs;
    IClientBox clientBox;

    String askEmail(){
        System.out.println("Veuillez saisir votre email");
        String email = sc.nextLine();
        return email;
    }

    String askPwd(){
        System.out.println("Veuillez saisir votre mot de passe");
        String pwd = sc.nextLine();
        return pwd;
    }

    IVODService login(IConnection stub) throws InvalidCredentialsException, RemoteException {
        iVODService = stub.login(askEmail(),askPwd());
        while (iVODService==null){
            System.out.println("Le mot de passe ou l'email est incorrect, veuillez réessayer");
            iVODService = stub.login(askEmail(),askPwd());
        }
        return iVODService;
    }


    void signIn(IConnection stub) throws RemoteException, SignUpException {
        boolean validMail = stub.signUp(askEmail(),askPwd());
        while(!validMail){
            System.out.println("L'email existe déjà, veuillez en saisir un nouveau");
            validMail = stub.signUp(askEmail(),askPwd());
        }
        System.out.println("Inscription reussie !");
    }

    void searchMovie() throws RemoteException {
        clientBox = new ClientBox();
        System.out.println("Voici le catalogue des films disponibles");
        movieDescs = iVODService.viewCatalog();
        for (MovieDesc movie : movieDescs){
            System.out.println("------------------------------------");
            System.out.println("Titre : "+movie.getMovieName());
            System.out.println("Identifiant: "+movie.getIsbn());
            System.out.println("Synopsys: "+movie.getSynopsis());
            System.out.println();
        }
        System.out.println("Veuillez saisir l'identifiant du film que vous souhaitez visionner");
        String ibs = sc.nextLine();
        bill = iVODService.playMovie(ibs, clientBox);

        while(bill==null){
            System.out.println("Film introuvable, voici le catalogue des films disponibles");
            for (MovieDesc movie : movieDescs){
                System.out.println("------------------------------------");
                System.out.println("Titre : "+movie.getMovieName());
                System.out.println("Identifiant: "+movie.getIsbn());
                System.out.println("Synopsys: "+movie.getSynopsis());
                System.out.println();
            }
            System.out.println("Veuillez saisir l'identifiant du film que vous souhaitez visionner");
            ibs = sc.nextLine();
            bill = iVODService.playMovie(ibs,clientBox);
        }
        System.out.println("Veuillez payer "+bill +" euros");
    }
}
