import Exceptions.InvalidCredentialsException;
import Exceptions.SignUpException;
import Service.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            Display display = new Display();
            Registry reg = LocateRegistry.getRegistry(2001);
            IConnection stub = (IConnection)reg.lookup("Connection");
            System.out.println("Bienvenue ! Avez-vous déjà un compte ? (O/N)");
            Scanner s = new Scanner(System.in);
            String input = s.nextLine();
            if (input.equals("O")) display.login(stub);
            else if (input.equals("N")){
                display.signIn(stub);
                display.login(stub);
            }
            else return;
            display.searchMovie();
        }
        catch (RemoteException | NotBoundException | SignUpException | InvalidCredentialsException e){
            e.printStackTrace();
        }
    }
}
