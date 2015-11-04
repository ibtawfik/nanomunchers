package Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * Created by islam on 11/1/15.
 */
public class Client2 {
    public static void main(String[] args) {
        Random random = new Random();
        try {
            Socket socket = new Socket("localhost", 1377);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String state;
            out.println("REGISTER:LEON");
            //out.println("76,UP,DOWN,LEFT,RIGHT");
            while ((state = in.readLine()) != null) {
                System.out.println("Received:" + state);
                Thread.sleep(1000);
                String command = random.nextInt(100) + ",UP,DOWN,LEFT,RIGHT";
                System.out.println(command);
                out.println(command);

                //        game.receiveMoves(1,"76,UP,DOWN,LEFT,RIGHT");
//        game.receiveMoves(2,"32,UP,DOWN,LEFT,RIGHT");
            }
        } catch (IOException eIO) {
            System.out.println(eIO.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
