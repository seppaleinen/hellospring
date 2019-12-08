package se.david.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.david.neo4j.entity.Movie;
import se.david.neo4j.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    private D3Format toD3Format(Collection<Movie> movies) {
        List<Node> nodes = new ArrayList<>();
        List<Rel> rels = new ArrayList<>();
        for (Movie movie : movies) {
            nodes.add(createNode(movie.getTitle(), "movie"));
            for (Role role : movie.getRoles()) {
                Node actor = createNode(role.getPerson().getName(), "actor");
                int source = nodes.indexOf(actor);
                if (source == -1) {
                    nodes.add(actor);
                }
                rels.add(createRel(role.getPerson().getId(), movie.getId()));
            }
        }
        return new D3Format(nodes, rels);
    }

    private Rel createRel(Long source, Long target) {
        return new Rel(source, target);
    }

    private Node createNode(String title, String label) {
        return new Node(title, label);
    }

    @Transactional(readOnly = true)
    public Optional<Movie> findByTitle(String title) {
        return Optional.ofNullable(movieRepository.findByTitle(title));
    }

    @Transactional(readOnly = true)
    public List<Movie> findByTitleLike(String title) {
        return movieRepository.findByTitleLike(title);
    }

    @Transactional(readOnly = true)
    public D3Format graph(int limit) {
        Collection<Movie> result = movieRepository.graph(limit);
        return toD3Format(result);
    }
}
