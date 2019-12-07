package se.david.neo4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.david.neo4j.entity.Movie;
import se.david.neo4j.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    private Map<String, Object> toD3Format(Collection<Movie> movies) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<Movie> result = movies.iterator();
        while (result.hasNext()) {
            Movie movie = result.next();
            nodes.add(map("title", movie.getTitle(), "label", "movie"));
            int target = i;
            i++;
            for (Role role : movie.getRoles()) {
                Map<String, Object> actor = map("title", role.getPerson().getName(), "label", "actor");
                int source = nodes.indexOf(actor);
                if (source == -1) {
                    nodes.add(actor);
                    source = i++;
                }
                rels.add(map("source", source, "target", target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
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
    public Map<String, Object> graph(int limit) {
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
