package Game;

import sun.awt.image.ImageWatched;

import java.io.*;
import java.util.*;

/**
 * Created by islam on 11/1/15.
 */
public class Game {
    private static Game game= null;
    public static Map<Integer, Node> nodes = new HashMap<Integer, Node>();
    //private List<Game.Node> nodes;
    private Player player1;
    private Player player2;
    private int timeStep;

    //We have to decide who lives in each round if two people choose the same space.
    //That is decided by this advantage number.
    private PlayerId advantage = PlayerId.ONE;

    private String p1_nextMove;
    private String p2_nextMove;

    private LinkedList<String> player1Messages = new LinkedList<String>();
    private LinkedList<String> player2Messages = new LinkedList<String>();


    public static Game getInstance(){
        if(game == null){
            game = new Game();
        }
        return game;
    }

    private Game (){

    }


    /****************************************************************************************
     * Commands from the players
     ****************************************************************************************/
    public void registerPlayer(int playerId, String playerName, int numPieces){
        if(playerId == 1){
            player1 = new Player(PlayerId.ONE, numPieces,playerName);
            System.out.println("Registered: Game.Player 1, " + numPieces + ", " + playerName );
        }else{
            player2 = new Player(PlayerId.TWO, numPieces, playerName);
            System.out.println("Registered: Game.Player 2, " + numPieces + ", " + playerName );
        }
    }

    public void receiveMoves(int player, String move){
        //Move is expected to be either PASS or nodeId,up,down,left,right \n nodeId,left,up,right,down
        if(player == 1){
            p1_nextMove = move;
            player1.setNextMove(p1_nextMove);
        }else{
            p2_nextMove = move;
            player2.setNextMove(p2_nextMove);
        }

        if(player1 != null && player2 != null){
            if(player1.getNextMove() != null && player2.getNextMove() != null){
                makeMove();
                p1_nextMove = null;
                p2_nextMove = null;
            }
        }

    }


    private void makeMove(){
        if(advantage.equals(PlayerId.ONE)){
            move(player1);
            move(player2);
            advanceTime();
            this.player1Messages.push(this.toString());
            this.player2Messages.push(this.toString());
            advantage = PlayerId.TWO;
        }else{
            move(player2);
            move(player1);
            advanceTime();
            this.player1Messages.push(this.toString());
            this.player2Messages.push(this.toString());
            advantage = PlayerId.ONE;
        }
    }

    private void advanceTime(){
        for(Node node: nodes.values()){
            node.moveTime(advantage);
        }
        player1.advanceTime();
        player2.advanceTime();
        timeStep++;
    }

    private void move(Player player){
        if(player.getNextMove() != null){
            String nextMove = player.getNextMove();
            //If pass then do nothing
            if(nextMove.toLowerCase().trim().equals("pass")){
                player.setNextMove(null);
            }

            //Otherwise make all the moves
            String [] moves = nextMove.split("\\|");
            for(String singleMove: moves){
                String[] move = singleMove.split(",");
                Integer nodeId = Integer.valueOf(move[0]);

                List<Direction> program = new LinkedList<Direction>();

                for(int i = 1; i < 5; i++){
                    String value = move[i].toLowerCase().trim();

                    if(value.equals("up")){
                        program.add(Direction.UP);
                    }else if(value.equals("down")){
                        program.add(Direction.DOWN);
                    }else if(value.equals("left")){
                        program.add(Direction.LEFT);
                    }else if(value.equals("right")){
                        program.add(Direction.RIGHT);
                    }
                }

                Node node = nodes.get(nodeId);

                player.place(node, program);

            }
        }

        if(player == player1){
            player1.setNextMove(null);
        }else if (player == player2){
            player2.setNextMove(null);
        }

    }

    /****************************************************************************************
     * Functions at application startup
     ****************************************************************************************/


    public void createBoard(File board) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(board));
        String nextLine;
        List<String[]> nodes = new LinkedList<String[]>();
        List<String[]> edges = new LinkedList<String[]>();

        while((nextLine = reader.readLine()) != null){
            String [] splitString = nextLine.split(",");
            if(splitString.length == 3 && splitString[0].equals("nodeid") == false){
                nodes.add(splitString);
            }

            if(splitString.length  == 2 && splitString[0].equals("nodeid1") == false){
                edges.add(splitString);
            }
        }

        createNodes(nodes);
        connectNodes(edges);
    }


    private void createNodes(List<String[]> nodes){
        for(String[] rep: nodes){
            Node node = new Node(Integer.valueOf(rep[0]), Integer.valueOf(rep[1]),Integer.valueOf(rep[2]));
            this.nodes.put(node.getId(), node);
        }
    }

    private void connectNodes(List<String[]> edges){
        for(String[] rep: edges){
            Integer nodeId1 = Integer.valueOf(rep[0]);
            Integer nodeId2 = Integer.valueOf(rep[1]);
            Node node1 = nodes.get(nodeId1);
            Node node2 = nodes.get(nodeId2);
            node1.connect(node2);
            node2.connect(node1);
        }
    }

    public String toString(){
        int player_1_score=0;
        int player_2_score = 0;

        StringBuilder builder = new StringBuilder();
        builder.append("nodeid,xLoc,yLoc,status,up,down,left,right\n");
        List<Node> sortedNodes = new LinkedList<Node>(this.nodes.values());

        Collections.sort(sortedNodes, new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                return o1.getId() - o2.getId();
            }
        });

        for(int i = 0; i < sortedNodes.size(); i++){
            builder.append(sortedNodes.get(i).toString() + "\n");
            Status status = sortedNodes.get(i).getStatus();

            if(status.equals(Status.EATEN_P1) || status.equals(Status.OCCUPIED_P1)){
                player_1_score++;
            }else if(status.equals(Status.EATEN_P2) || status.equals(Status.OCCUPIED_P2)){
                player_2_score++;
            }
        }
        //builder.append("PlayerID,PlayerName,unused,dead,inPlay,score\n");


        return builder.toString();
    }


    public LinkedList<String> getMessages(int playerId){
        if(playerId == 1){
            LinkedList<String> returnMessages = new LinkedList<String>(this.player1Messages);
            clearMessages(playerId);
            return returnMessages;
        }else{
            LinkedList<String> returnMessages = new LinkedList<String>(this.player2Messages);
            clearMessages(playerId);
            return returnMessages;
        }
    }

    public void clearMessages(int playerId){
        if(playerId == 1){
            this.player1Messages.clear();
        }else{
            this.player2Messages.clear();
        }
    }

    public boolean readyForMove(int playerId){
        if(playerId == 1){
            return p1_nextMove == null;
        }else{
            return p2_nextMove == null;
        }
    }



}