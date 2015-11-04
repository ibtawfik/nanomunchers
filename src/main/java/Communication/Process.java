package Communication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import Game.Game;

/**
 * Created by islam on 11/1/15.
 */
public class Process {
    /**
     * arg0 : NumberOfPieces
     * arg1 : input file
     * arg2 : socket
     * @param args
     */
    public static void main(String[] args) throws IOException {
        args[0] = "10";
        args[1] = "\\Users\\islamt\\Desktop\\input";
//        args[2] = "1377";

        //Create sockets for players to register on
        try {
            ServerSocket serverSocket = new ServerSocket(1377);

            Socket player1Socket = serverSocket.accept();
            Runnable connectionHandler = new Connector(player1Socket, 1);
            new Thread(connectionHandler).start();

            Socket player2Socket = serverSocket.accept();
            Runnable connectionHandler2 = new Connector(player2Socket, 2);
            new Thread(connectionHandler2).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get the game object and create the board
        Game game = Game.getInstance();
        game.createBoard(new File(args[1]));

        //Start the web server

    }



}
