package extendsThread;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class ProxyThread extends Thread {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private String port;
    private String ip;

    public ProxyThread(String port, String ip) {
        super();
        this.port = port;
        this.ip = ip;
    }

    @Override
    public void run() {

        try {
            checkProxy(ip, Integer.parseInt(port));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String checkProxy(String ip, int port) throws FileNotFoundException {

        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            URLConnection urlConnection = new URL("https://vozhzhaev.ru/test.php").openConnection(proxy);
            InputStream is = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);

            int rc;
            StringBuilder sb = new StringBuilder();
            while ((rc = reader.read()) != -1) {
                sb.append((char) rc);
            }

            FileOutputStream fout = new FileOutputStream("C:\\java\\good.txt", true);
            String good_txt = ip + ":" + port + "\n";
            byte[] bytes = good_txt.getBytes(StandardCharsets.UTF_8);
            fout.write(bytes);
            fout.close();
            return String.valueOf(System.out.printf("%-9s%-37s%s%n", ANSI_YELLOW + "IP:", ANSI_BLUE + ip + ANSI_YELLOW + ":" + ANSI_PURPLE + port, ANSI_GREEN + "-работает"));

        } catch (Exception e) {

            return String.valueOf(System.out.printf("%-9s%-37s%s%n", ANSI_YELLOW + "IP:", ANSI_BLUE + ip + ANSI_YELLOW + ":" + ANSI_PURPLE + port, ANSI_RED + "-не работает"));
        }
    }

}
