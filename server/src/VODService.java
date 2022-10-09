import Service.Bill;
import Service.IClientBox;
import Service.IVODService;
import Service.MovieDesc;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class VODService extends UnicastRemoteObject implements IVODService {

    protected VODService() throws RemoteException {
    }

    //on créer une liste de films pour remplir le catalogue
    @Override
    public List<MovieDesc> viewCatalog() throws RemoteException{
        return List.of(
                new MovieDesc("Titanic","000-000-000-00-0","Un bateau coule",new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
                new MovieDesc("Avengers","111-111-111-11-1","Des superheros se rassemblent",new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}),
                new MovieDesc("Shutter Island","222-222-222-22-2","Un patient fou devient policier",new byte[]{0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}),
                new MovieDescExtended("Jurassic Park", "333-333-333-33-3", "Des dinosaures en vie qui s'échappent d'un parc", new byte[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1}, new byte[]{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1})
        );
    }

    @Override
    public Bill playMovie(String isbn, IClientBox box) throws RemoteException {
        //on commence par chercher que l'identifiant rentré correpsond bien à un film de la liste
        Optional<MovieDesc> movieDescOptional= viewCatalog().stream().filter(movie -> isbn.equals(movie.getIsbn())).findAny();
        //si c'est le cas, on rentre dans la boucle suivante
        if(movieDescOptional.isPresent()){
            //on récupère le bloc de bytes du film
            byte[] bytes = movieDescOptional.get().getBytes();
            //on lance un thread pour streamer les blocs de bytes récupérés
            new Thread(() -> {
                //ici les byte sont envoyés 6 par 6
                for(int i=0;i<bytes.length;i+=6){
                    try{
                        //on affiche les blocs de bytes coté client
                        box.stream(Arrays.copyOfRange(bytes,i,Math.min((i+6),bytes.length)));
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                }
            }).start();
            //on renvoie le prix que devra payer le client
            return new Bill(movieDescOptional.get().getMovieName(),(BigInteger.TEN));
        }
        return null;
    }
}
