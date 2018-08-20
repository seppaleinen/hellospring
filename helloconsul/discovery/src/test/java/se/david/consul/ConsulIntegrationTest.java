package se.david.consul;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pszymczyk.consul.ConsulProcess;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulResource;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
@ActiveProfiles("test")
public class ConsulIntegrationTest {
    @LocalServerPort
    private int port;

    @ClassRule
    public static final ConsulResource consul = new ConsulResource(8502);

    @Before
    public void before() {
        RestAssured.port = port;
        consul.reset();
        registerService();
    }

    private void registerService() {
        ConsulData data = new ConsulData("dc1",
                "google",
                "http://localhost:8089",
                new ConsulData.ConsulService("register", 8089));

        String url = String.format("http://localhost:%s/v1/catalog/register", consul.getHttpPort());

        given().contentType(ContentType.JSON)
                .body(data)
                .put(url)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void healthShouldBeUp() {
        given()
                .get("/health")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("status", CoreMatchers.equalTo("UP"));
    }

    @Test
    public void callRest() {
        given()
                .accept(ContentType.JSON)
                .get("/ping/hello")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("message", CoreMatchers.equalTo("hello"));
    }

    public static class ConsulData {
        private String Datacenter;
        private String Node;
        private String Address;
        private ConsulService Service;

        ConsulData(String DataCenter, String Node, String Address, ConsulService Service) {
            this.Datacenter = DataCenter;
            this.Node = Node;
            this.Address = Address;
            this.Service = Service;
        }

        public String getDatacenter() {
            return Datacenter;
        }

        public String getNode() {
            return Node;
        }

        public String getAddress() {
            return Address;
        }

        public ConsulService getService() {
            return Service;
        }

        public static class ConsulService {
            private String Service;
            private int Port;

            public ConsulService(String Service, int Port) {
                this.Service = Service;
                this.Port = Port;
            }

            public String getService() {
                return Service;
            }

            public int getPort() {
                return Port;
            }
        }
    }
}
