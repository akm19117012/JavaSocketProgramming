import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.util.Scanner;

public class SSLClient {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String host="localhost";
        int port=12345;
        String trustStorePath="/home/geeta/Documents/CODE/JavaSocketProgramming/src/keystore/clienttruststore1.jks";
        String trustStorePassword="password1";
        String keyManagerAlgo="SunX509";
//        String keyEncryptionAlgo="RSA";
        try{
            FileInputStream fis=new FileInputStream(trustStorePath);
            KeyStore trustStore=KeyStore.getInstance("JKS");
            trustStore.load(fis,trustStorePassword.toCharArray());
            TrustManagerFactory tmf=TrustManagerFactory.getInstance(keyManagerAlgo);
            tmf.init(trustStore);
            SSLContext sslContext=SSLContext.getInstance("TLS");
            sslContext.init(null,tmf.getTrustManagers(),null);
            SSLSocketFactory ssf=sslContext.getSocketFactory();
            SSLSocket sslSocket=(SSLSocket) ssf.createSocket(host,port);
            BufferedReader br =new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(sslSocket.getOutputStream()));

            String msg_from_client=br.readLine();
            System.out.println(msg_from_client);
            String msg_to_send=sc.nextLine();
            bw.write(msg_to_send);
//            bw.newLine();
            bw.flush();


            br.close();
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
