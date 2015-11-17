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
public class Client {

    private static StringBuffer command = new StringBuffer();

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 1377);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String state;
            out.println("REGISTER:" + args[0]);
            while ((state = in.readLine()) != null) {
                if(state.equals("START")){
                    command = new StringBuffer();
                }
                else if(state.equals("END")){
                    out.println(process(command.toString()));
                }else{
                    command.append(state + "\n");
                }
            }
        } catch (IOException eIO) {
            System.out.println("ERROR");
            System.out.println(eIO.getMessage());
        }
    }

    private static String process(String command){
        System.out.println(command);
        //return "103,DOWN,UP,LEFT,RIGHT|103,DOWN,LEFT,UP,RIGHT";

        //Starting point for your program
        Random random = new Random();
        return random.nextInt(125) + ",UP,DOWN,LEFT,RIGHT";
            //return 103 +",DOWN,RIGHT,UP,LEFT";
        //return "PASS";
    }
}
