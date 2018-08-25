import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class GatlingLoadSimulation extends Simulation {
  private final val BASE_URL: String = s"http://localhost:8080"
  private final val PARALLEL_USERS: Integer = 5

  private final val httpProtocol: HttpProtocolBuilder = http.baseURL(BASE_URL).disableWarmUp

  private final val ENDPOINT_NAME: String = "Endpoint Name"

  private val loadTestScenario: ScenarioBuilder = scenario("Gatling Performance Test")
    .repeat(200) {
      exec(
        http(ENDPOINT_NAME)
          .get("/endpoint")
          .check(status.is(200))
      )
    }

  setUp(loadTestScenario.inject(atOnceUsers(PARALLEL_USERS)))
    .protocols(httpProtocol)
    .assertions(
      details(ENDPOINT_NAME).responseTime.mean.lt(20),
      details(ENDPOINT_NAME).successfulRequests.percent.is(100),
      //      global.requestsPerSec.gt(300)
    )
}
