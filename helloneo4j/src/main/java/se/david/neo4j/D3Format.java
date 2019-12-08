package se.david.neo4j;

import java.util.List;
import java.util.Map;

public class D3Format {
    private List<Map<String, String>> nodes;
    private List<Map<String, Long>> rels;

    public D3Format() {
    }

    public D3Format(List<Map<String, String>> nodes, List<Map<String, Long>> rels) {
        this.nodes = nodes;
        this.rels = rels;
    }

    public List<Map<String, String>> getNodes() {
        return nodes;
    }

    public List<Map<String, Long>> getRels() {
        return rels;
    }

    public class Node {
        private String title;
        private String label;

        public Node() {
        }

        public Node(String title, String label) {
            this.title = title;
            this.label = label;
        }
    }

    public class Rel {
        private Long source;
        private Long target;

        public Rel() {
        }

        public Rel(Long source, Long target) {
            this.source = source;
            this.target = target;
        }
    }
}
