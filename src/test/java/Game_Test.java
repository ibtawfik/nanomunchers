import org.junit.Test;

/**
 * Created by islam on 11/1/15.
 */
public class Game_Test {
    @Test
    public void parseMovetest(){
        String command = "1,up,down,left,right\n 7,left,down,up,right";
        String[] moves = command.split("\n");

        String[] last = moves[0].split(",");
        String wait = "";
    }
}
