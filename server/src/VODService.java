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

    @Override
    public List<MovieDesc> viewCatalog() throws RemoteException{
        return List.of(
                new MovieDesc("Titanic","000-000-000-00-0-0","Un bateau coule",new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
                new MovieDesc("Avengers","111-111-111-11-1","Des superheros se rassemblent",new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}),
                new MovieDesc("Shutter Island","222-222-222-22-2","Un patient fou devient policier",new byte[]{0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0})
        );
    }

    @Override
    public Bill playMovie(String isbn, IClientBox box) throws RemoteException {
        Optional<MovieDesc> movieDescOptional= viewCatalog().stream().filter(movie -> isbn.equals(movie.getIsbn())).findAny();
        if(movieDescOptional.isPresent()){
            byte[] bytes = movieDescOptional.get().getBytes();
            new Thread(() -> {
                for(int i=0;i<bytes.length;i+=6){
                    try{
                        box.stream(Arrays.copyOfRange(bytes,i,Math.min((i+6),bytes.length)));
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                }
            }).start();
            return new Bill(movieDescOptional.get().getMovieName(),(BigInteger.TEN));
        }
        return null;
    }
}
