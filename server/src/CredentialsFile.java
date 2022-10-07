
import service.Client;

import java.io.*;
import java.util.ArrayList;

public class CredentialsFile {

    FileReader fr;
    BufferedReader br;
    FileWriter fw;
    BufferedWriter bw;

    public CredentialsFile(String file) throws FileNotFoundException, IOException {
        this.fr = new FileReader(file);
        this.fw = new FileWriter(file, true);
        this.br = new BufferedReader(fr);
        this.bw = new BufferedWriter(fw);
    }

    public ArrayList<Client> readFile(){
        Client client;
        ArrayList<Client> clients = new ArrayList<>();
        String line;

        try{
            while(true){
                line = br.readLine();
                if(line==null) break;
                client = new Client(line, br.readLine());
                clients.add(client);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                br.close();
                fr.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        return clients;

    }

    public void writeFile(Client client){

        try {
            bw.write(client.getMail());
            bw.write(client.getPassword());
        } catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                bw.flush();
                bw.close();
                fw.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
