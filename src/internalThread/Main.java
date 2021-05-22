package internalThread;

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


                    Thread internalThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                checkProxy(ip,Integer.parseInt(port));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    internalThread.start();

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

    public static String checkProxy(String ip, int port) throws FileNotFoundException {

        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(ip, port));
            URLConnection urlConnection = new URL("https://vozhzhaev.ru/test.php").openConnection(proxy);
            InputStream is = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);

            int rc;
            StringBuilder sb = new StringBuilder();
            while ((rc = reader.read()) != -1){
                sb.append((char)rc);
            }

            FileOutputStream fout = new FileOutputStream("C:\\java\\good.txt", true);
            String good_txt = ip + ":" + port + "\n";
            byte[] bytes = good_txt.getBytes(StandardCharsets.UTF_8);
            fout.write(bytes);
            fout.close();
            return String.valueOf(System.out.printf("%-9s%-37s%s%n",ANSI_YELLOW+"IP:",ANSI_BLUE+ip+ANSI_YELLOW+":"+ANSI_PURPLE+port,ANSI_GREEN+"-работает"));

        } catch (Exception e) {

            return String.valueOf(System.out.printf("%-9s%-37s%s%n",ANSI_YELLOW+"IP:",ANSI_BLUE+ip+ANSI_YELLOW+":"+ANSI_PURPLE+port,ANSI_RED+"-не работает"));
        }
    }
}

