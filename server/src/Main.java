import Service.IConnection;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args){
        try{
            IConnection connection = new Connection();
            Registry reg = LocateRegistry.createRegistry(2001);
            reg.rebind("Connection", connection);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
