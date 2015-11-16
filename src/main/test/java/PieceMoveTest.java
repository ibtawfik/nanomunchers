import Game.Direction;
import Game.Node;
import Game.Piece;
import Game.PlayerId;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by islam on 11/15/15.
 */
public class PieceMoveTest {

    @Test
    public void moveTest(){
        Node node = Mockito.mock(Node.class);
        when(node.canMove(any(Direction.class))).thenReturn(true);

        Piece piece = new Piece(PlayerId.ONE);
        piece.placeOnNode(node);
        List<Direction> program = new LinkedList<Direction>();
        program.add(Direction.UP);
        program.add(Direction.DOWN);
        program.add(Direction.LEFT);
        program.add(Direction.RIGHT);
        piece.programPiece(program);

        Assert.assertTrue(piece.advance().equals(Direction.UP));
        Assert.assertTrue(piece.advance().equals(Direction.DOWN));
        Assert.assertTrue(piece.advance().equals(Direction.LEFT));
        Assert.assertTrue(piece.advance().equals(Direction.RIGHT));
        Assert.assertTrue(piece.advance().equals(Direction.UP));
    }

    @Test
    public void secondNodeTest(){
        Node node = Mockito.mock(Node.class);
        when(node.canMove(Direction.UP)).thenReturn(true);
        when(node.canMove(Direction.DOWN)).thenReturn(false);
        when(node.canMove(Direction.LEFT)).thenReturn(false);
        when(node.canMove(Direction.RIGHT)).thenReturn(true);

        Piece piece = new Piece(PlayerId.ONE);
        piece.placeOnNode(node);
        List<Direction> program = new LinkedList<Direction>();
        program.add(Direction.UP);
        program.add(Direction.DOWN);
        program.add(Direction.LEFT);
        program.add(Direction.RIGHT);
        piece.programPiece(program);

        Assert.assertTrue(piece.advance().equals(Direction.UP));
        Assert.assertTrue(piece.advance().equals(Direction.RIGHT));
        Assert.assertTrue(piece.advance().equals(Direction.UP));
        Assert.assertTrue(piece.advance().equals(Direction.RIGHT));



    }
}
