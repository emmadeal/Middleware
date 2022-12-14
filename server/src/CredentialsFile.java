
import Service.Client;

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
        String mail;
        String pwd;

        try{
            while(true){
                mail = br.readLine();
                pwd = br.readLine();
                if(mail==null) break;
                System.out.println("mail: " + "-" + mail + "-");
                System.out.println("pwd: " + "-" + pwd + "-");
                client = new Client(mail, pwd);
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
            bw.write(client.getMail()+"\n");
            bw.write(client.getPassword()+"\n");
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
