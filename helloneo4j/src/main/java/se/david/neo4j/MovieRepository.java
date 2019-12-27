package se.david.neo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.david.neo4j.entity.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends Neo4jRepository<Movie, Long> {

    Movie findByTitle(@Param("title") String title);

    List<Movie> findByTitleLike(@Param("title") String title);

    @Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) RETURN m,r,a LIMIT {limit}")
    List<Movie> graph(@Param("limit") int limit);
}
