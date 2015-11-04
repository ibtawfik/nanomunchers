package ui;

import Communication.Connector;
import Game.Game;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UIApplication extends Application<UIConfiguration> {

    @Override
    public void initialize(Bootstrap<UIConfiguration> myConfigurationBootstrap) {
        myConfigurationBootstrap.addBundle(new AssetsBundle("/assets", "/assets", "index.html"));
    }

    @Override
    public void run(UIConfiguration uiConfiguration, Environment environment) throws Exception {
        final GameResource resource = new GameResource();
        environment.jersey().register(resource);
    }

    public static void main(String[] args) throws Exception {
//        args[0] = "10";
//        args[1] = "/Users/islamt/Desktop/input";

        Game game = Game.getInstance();
        game.createBoard(new File("/Users/islam/Desktop/input.txt"));
//        game.registerPlayer(1,"LeonSpark",5);
//        game.registerPlayer(2,"AI",5);
//        game.receiveMoves(1,"76,UP,DOWN,LEFT,RIGHT");
//        game.receiveMoves(2,"32,UP,DOWN,LEFT,RIGHT");
        new UIApplication().run(args);
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
        
        

    }
}