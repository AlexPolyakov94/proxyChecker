package runnableThread;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;


public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) throws FileNotFoundException {

        System.out.printf("%32s%n",ANSI_GREEN+"НАЧАЛО ПРОВЕРКИ:");

        FileOutputStream fout = new FileOutputStream("C:\\java\\good.txt");
        try {
            String good_txt = "Список рабочих прокси-серверов:\n";
            byte[] bytes = good_txt.getBytes(StandardCharsets.UTF_8);
            fout.write(bytes);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream("C:\\java\\ip.txt");
            int i;
            String proxy = "";

            while ((i=fin.read()) != -1){

                if(i==13) continue;
                else if(i==10){
                    String ip = proxy.split(":")[0];
                    String port=proxy.split(":")[1];

                    Thread proxyThread = new Thread(new ProxyRunnableThread(port, ip));
                    proxyThread.start();

                    proxy ="";

                }else if(i != 9){
                    proxy += (char)i;
                }else{
                    proxy += ":";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


