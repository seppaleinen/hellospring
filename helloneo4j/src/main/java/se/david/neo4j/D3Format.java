package se.david.neo4j;

import java.util.List;

public class D3Format {
    private List<Node> nodes;
    private List<Rel> rels;

    public D3Format() {
    }

    public D3Format(List<Node> nodes, List<Rel> rels) {
        this.nodes = nodes;
        this.rels = rels;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Rel> getRels() {
        return rels;
    }
}
