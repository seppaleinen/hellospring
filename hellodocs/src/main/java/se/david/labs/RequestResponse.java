package se.david.labs;

import io.swagger.annotations.ApiModelProperty;

public class RequestResponse {
    @ApiModelProperty(example = "Hello")
    private String message;

    public RequestResponse() {
    }

    public RequestResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
