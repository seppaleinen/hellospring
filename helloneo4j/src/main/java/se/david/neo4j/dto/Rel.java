package se.david.neo4j.dto;

public class Rel {
    private Long source;
    private Long target;

    public Rel() {
    }

    public Rel(Long source, Long target) {
        this.source = source;
        this.target = target;
    }

    public Long getSource() {
        return source;
    }

    public Long getTarget() {
        return target;
    }
}
