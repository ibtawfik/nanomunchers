package ui;

import Game.Direction;
import Game.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {
    private Map<Integer, Node> nodes;
    private Map<Integer, Map<Direction, Integer>> edges = new HashMap<Integer, Map<Direction, Integer>>();

    public GameState(Map<Integer, Node> nodes, Map<Integer, Map<Direction, Integer>> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Integer, Node> nodes) {
        this.nodes = nodes;
    }

    public Map<Integer, Map<Direction, Integer>> getEdges() {
        return edges;
    }

    public void setEdges(Map<Integer, Map<Direction, Integer>> edges) {
        this.edges = edges;
    }
}
