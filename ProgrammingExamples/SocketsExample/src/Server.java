import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String [] args) throws IOException {
        System.out.println("Server is started");
        System.out.println("Server is waiting for server request");

        ServerSocket ss = new ServerSocket(12888);
        Socket s = ss.accept();

        InputStreamReader isr = new InputStreamReader(s.getInputStream());
        BufferedReader br = new BufferedReader(isr);

        String str = br.readLine();

        System.out.println("Client Message: " + str);

        str = str.substring(3);

        //data into stream                             from where u want to send this data (output port of the socket s)
        OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());

        //actually send data (resposible to send data)
        //sends the output stream
        PrintWriter out = new PrintWriter(os);
        out.println(str);
        out.flush();

    }
}
