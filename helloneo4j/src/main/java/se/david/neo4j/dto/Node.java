package se.david.neo4j.dto;

public class Node {
    private String title;
    private String label;

    public Node() {
    }

    public Node(String title, String label) {
        this.title = title;
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public String getLabel() {
        return label;
    }
}
