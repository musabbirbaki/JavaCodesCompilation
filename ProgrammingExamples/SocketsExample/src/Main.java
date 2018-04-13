import java.io.*;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Client");

        String ip = "localhost";
        int port = 12888;
        Socket s = new Socket(ip, port);
        // after this a request will be sent to sever side and then ur connected

        String str = "Musabbir Ready.";

        //data into stream                             from where u want to send this data (output port of the socket s)
        OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());

        //actually send data (resposible to send data)
        //sends the output stream
        PrintWriter out = new PrintWriter(os);
        out.println(str);
        out.flush();

        InputStreamReader isr = new InputStreamReader(s.getInputStream());
        BufferedReader br = new BufferedReader(isr);

        String str2 = br.readLine();

        System.out.println("Client Message: " + str2);


    }
}
