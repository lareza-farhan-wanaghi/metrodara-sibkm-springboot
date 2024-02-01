package com.metrodata.orderservice.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metrodata.orderservice.exception.CustomException;
import com.metrodata.orderservice.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            ObjectMapper objectMapper = new ObjectMapper();
            ErrorMessage errorMessage =
                    objectMapper.readValue(httpResponse.getBody(), ErrorMessage.class);
            throw new CustomException(
                    errorMessage.getMessage(),
                    errorMessage.getError(),
                    httpResponse.getRawStatusCode()); //400
        }
    }
}
