
import service.IClientBox;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;


public class ClientBox extends UnicastRemoteObject implements IClientBox {

    protected ClientBox() throws RemoteException {
        super();
    }

    @Override
    public void stream(byte[] chunck) throws RemoteException {
        System.out.println(Arrays.toString(chunck));
    }
}
