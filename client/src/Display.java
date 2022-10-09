import Exceptions.InvalidCredentialsException;
import Exceptions.SignUpException;
import Service.*;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class Display {

    Scanner sc = new Scanner(System.in);
    IVODService iVODService = null;
    Bill bill = null;
    List<MovieDesc> movieDescs;
    IClientBox clientBox;

    String askEmail(){
        System.out.println("Veuillez saisir votre email.");
        String email = sc.nextLine();
        return email;
    }

    String askPwd(){
        System.out.println("Veuillez saisir votre mot de passe.");
        String pwd = sc.nextLine();
        return pwd;
    }

    IVODService login(IConnection stub) throws InvalidCredentialsException, RemoteException {
        //gestion de l'affchage dans la console
        System.out.println("Connectez-vous :");
        iVODService = stub.login(askEmail(),askPwd());
        while (iVODService==null){
            System.out.println("Le mot de passe ou l'email est incorrect, veuillez réessayer.");
            iVODService = stub.login(askEmail(),askPwd());
        }
        return iVODService;
    }


    void signIn(IConnection stub) throws RemoteException, SignUpException {
        //gestion de l'affichage dans la console
        System.out.println("Inscrivez-vous :");
        boolean validMail = stub.signUp(askEmail(),askPwd());
        while(!validMail){
            System.out.println("L'email saisi est déjà utilisé, veuillez en saisir un nouveau.");
            validMail = stub.signUp(askEmail(),askPwd());
        }
        System.out.println("Inscription reussie !");
    }

    void searchMovie() throws RemoteException {
        clientBox = new ClientBox();
        //cela permet au client de voir la liste des films du catalogue
        System.out.println("Voici les films disponibles :");
        movieDescs = iVODService.viewCatalog();
        for (MovieDesc movie : movieDescs){
            System.out.println("------------------------------------");
            System.out.println("Titre : "+movie.getMovieName());
            System.out.println("Identifiant: "+movie.getIsbn());
            System.out.println("Synopsys: "+movie.getSynopsis());
            System.out.println();
        }
        //le client saisi l'identifiant du film qu'il souhaite visionner
        System.out.println("Veuillez saisir l'identifiant du film que vous souhaitez visionner");
        String ibs = sc.nextLine();
        //on lance le film en appelant la méthode playMovie implémentée coté serveur
        bill = iVODService.playMovie(ibs, clientBox);

        //tant que le client n'a pas saisi un identifiant valide, on lui redemande
        while(bill==null){
            System.out.println("Oups... ce film n'existe pas. Voici les films disponibles :");
            for (MovieDesc movie : movieDescs){
                System.out.println("------------------------------------");
                System.out.println("Titre : "+movie.getMovieName());
                System.out.println("Identifiant: "+movie.getIsbn());
                System.out.println("Synopsys: "+movie.getSynopsis());
                System.out.println();
            }
            System.out.println("Veuillez saisir l'identifiant du film que vous souhaitez visionner.");
            ibs = sc.nextLine();
            bill = iVODService.playMovie(ibs,clientBox);
        }
        //on affiche le prix dans la console
        System.out.println("Votre total est de "+bill +" euros");
    }
}
