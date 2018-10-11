package se.david.labs.swagger;

import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.david.labs.RequestResponse;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
public class SwaggerifiedController {
    @ApiOperation(value = "Calling swagger endpoint, that only responds back with the value in request")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "RequestObject",
                    value = "Request Object",
                    required = true,
                    examples = @Example(
                            value = @ExampleProperty(
                                    mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                    value = "{\"message\": \"hello\"}")),
                    dataTypeClass = RequestResponse.class,
                    paramType = "body")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Query was successful, response in body", response = RequestResponse.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(path = "/swagger",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<RequestResponse> swagger(@RequestBody RequestResponse request) {
        return ResponseEntity.ok(new RequestResponse(request.getMessage()));
    }
}
