package se.david.neo4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.david.neo4j.entity.Movie;
import se.david.neo4j.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    private D3Format toD3Format(Collection<Movie> movies) {
        List<Map<String, String>> nodes = new ArrayList<>();
        List<Map<String, Integer>> rels = new ArrayList<>();
        int i = 0;
        for (Movie movie : movies) {
            nodes.add(createNode(movie.getTitle(), "movie"));
            int target = i;
            i++;
            for (Role role : movie.getRoles()) {
                Map<String, String> actor = createNode(role.getPerson().getName(), "actor");

                int source = nodes.indexOf(actor);

                if (source == -1) {
                    nodes.add(actor);
                    source = i++;
                }

                rels.add(createRel(source, target));
            }
        }
        return new D3Format(nodes, rels);
    }

    private Map<String, String> createNode(String title, String movie) {
        HashMap<String, String> nodeEntry = new HashMap<>();
        nodeEntry.put("title", title);
        nodeEntry.put("label", movie);
        return nodeEntry;
    }

    private Map<String, Integer> createRel(int source, int target) {
        Map<String, Integer> relEntry = new HashMap<>();
        relEntry.put("source", source);
        relEntry.put("target", target);
        return relEntry;
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }

    @Transactional(readOnly = true)
    public Movie findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Transactional(readOnly = true)
    public Collection<Movie> findByTitleLike(String title) {
        return movieRepository.findByTitleLike(title);
    }

    @Transactional(readOnly = true)
    public D3Format graph(int limit) {
        Collection<Movie> result = movieRepository.graph(limit);
        return toD3Format(result);
    }

    public class D3Format {
        private final List<Map<String, String>> nodes;
        private final List<Map<String, Integer>> rels;

        public D3Format(List<Map<String, String>> nodes, List<Map<String, Integer>> rels) {
            this.nodes = nodes;
            this.rels = rels;
        }

        public List<Map<String, String>> getNodes() {
            return nodes;
        }

        public List<Map<String, Integer>> getRels() {
            return rels;
        }
    }
}
