import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SSLServer {
    public static void main(String[] args) {
//        String host="localhost";
        int port=12345;
//        String keyStorePath="./keystore/serverkeystore1.jks";
//        String keyStorePath="Main.java";
        String keyStorePath="/home/geeta/Documents/CODE/JavaSocketProgramming/src/keystore/serverkeystore1.jks";
        String keyStorePassword="password1";
        String keyManagerAlgo="SunX509";
//        String keyEncryptionAlgo="RSA";

        try{

            KeyStore ks=KeyStore.getInstance("JKS");
            System.out.println(ks.getType());
            FileInputStream fis=new FileInputStream(keyStorePath);
            System.out.println("fileLoaded");
            ks.load(fis,keyStorePassword.toCharArray());
            KeyManagerFactory kmf=KeyManagerFactory.getInstance(keyManagerAlgo);
            kmf.init(ks,keyStorePassword.toCharArray());
            SSLContext sslContext=SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(),null,null);
            SSLServerSocketFactory ssf=sslContext.getServerSocketFactory();
            try{

                SSLServerSocket sslServerSocket=(SSLServerSocket) ssf.createServerSocket(port);
                System.out.printf("Server started at port %s\n",port);
                SSLSocket clientSocket=(SSLSocket) sslServerSocket.accept();
                System.out.println("Client is connected");

                InputStream inputStream =clientSocket.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                OutputStream outputStream=clientSocket.getOutputStream();
                OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
                BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);

                String msg_from_client;
                bufferedWriter.write("You are connected to server");
                bufferedWriter.newLine();
                bufferedWriter.write("Enter your name to continue");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                msg_from_client=bufferedReader.readLine();
                System.out.printf("Hi,%s.\nWelcome to our server\n",msg_from_client);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}