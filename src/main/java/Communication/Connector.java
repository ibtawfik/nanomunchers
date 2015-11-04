package Communication;

import Game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by islam on 11/1/15.
 */
public class Connector implements Runnable{
    private Socket clientSocket;
    private int playerId;
    private Game game = Game.getInstance();

    public Connector(Socket clientSocket, int playerId){
        this.playerId = playerId;
        this.clientSocket = clientSocket;
    }

    public void run() {
        try{
            PrintWriter out =  new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            String outputLine = "";
            LinkedList<String> commands = new LinkedList<String>();
            while ((inputLine = in.readLine()) != null) {

                if(inputLine.contains("REGISTER:")){
                    registerPlayer(inputLine);
                }else{
                    commands.add(inputLine);
                }

                if(game.readyForMove(playerId)){
                    if(commands.size() > 0){
                        game.receiveMoves(playerId,commands.pop());
                        out.println(poll());
                    }else{
                        out.println("WAITING");
                    }
                }



            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void registerPlayer(String inputLine){
        String teamName = inputLine.replaceAll("REGISTER:", "");
        game.registerPlayer(playerId,teamName,10);
    }

    private String poll(){
        while(true){
            LinkedList<String> messages = game.getMessages(playerId);
            if(messages.size() > 0){
                for(String message: messages){
                    System.out.println(message);
                    return message;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}