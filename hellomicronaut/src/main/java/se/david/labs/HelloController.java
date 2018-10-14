package se.david.labs;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;

import javax.inject.Inject;
import java.util.List;

@Controller("/hello")
public class HelloController {
    private final DbRepository dbRepository;

    @Inject
    public HelloController(DbRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DbEntity> index() {
        return dbRepository.findAll();
    }

    @Post("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public DbEntity createOne(@QueryValue("name") String name) {
        return dbRepository.save(new DbEntity(name));
    }

}
