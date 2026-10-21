package com.mohammed.order;

import com.mohammed.order.dto.CreateOrderDto;
import com.mohammed.order.dto.OrderResponseDto;
import com.mohammed.order.dto.PageResponseDto;
import com.mohammed.order.dto.UpdateOrderDto;
import com.mohammed.order.service.OrderService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/orders")
public class OrderResource {

    private final OrderService orderService;
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PageResponseDto<OrderResponseDto> getOrders(@QueryParam("status") String status,
                                               @QueryParam("page") @DefaultValue("0") int page,
                                               @QueryParam("size") @DefaultValue("10") int size
    ) {
        return orderService.getOrders(status,page,size);
    }

    @Path("/status")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderStatus() {
        return "ORDERS WORK FINE";
    }

    @Path("/{id}")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("id") Long id) {
        OrderResponseDto order= orderService.getOrderById(id);
        return Response.ok(order).build();

    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OrderResponseDto createOrder( @Valid CreateOrderDto dto) {
        return orderService.create(dto);

    }

    @Path("/{id}")
    @Transactional
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OrderResponseDto updateOrder( @Valid UpdateOrderDto dto,@PathParam("id") Long id) {
        return orderService.update(dto,id);

    }
    @GET
    @Path("/slow")
    public Response slow() throws InterruptedException {
        Thread.sleep(5000);
        return Response.ok("finished").build();
    }
}
