package se.david.neo4j.dto;

import java.util.Set;

public class D3Format {
    private Set<Node> nodes;
    private Set<Rel> rels;

    public D3Format() {
    }

    public D3Format(Set<Node> nodes, Set<Rel> rels) {
        this.nodes = nodes;
        this.rels = rels;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Rel> getRels() {
        return rels;
    }
}
