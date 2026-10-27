package com.mohammed.order.exception;
import com.mohammed.order.dto.ErrorResponseDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class OrderNotFoundResponseMapper
        implements ExceptionMapper<OrderNotFoundException> {

    @Override
    public Response toResponse(OrderNotFoundException exception) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponseDto(
                        404,
                        exception.getMessage()
                ))
                .build();
    }
}
