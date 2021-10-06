package com.niu.security.orderapi.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订单 api
 *
 * @author [nza]
 * @version 1.0 [2021/08/01 13:01]
 * @createTime [2021/08/01 13:01]
 */
@RestController
@RequestMapping("/orders")
@Slf4j
@Api("order api")
public class OrderController {

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    @ApiOperation("create order")
    public OrderInfo create(@RequestBody OrderInfo info) {

//        PriceInfo priceInfo = restTemplate.getForObject("http://localhost:7016/price/" + info.getProductId(), PriceInfo.class);
//        log.debug("price is {}", priceInfo.getPrice());

        return info;
    }

    @GetMapping("/{id}")
    @ApiOperation("get order info")
    public OrderInfo getOrderInfo(@PathVariable Long id, @ApiIgnore @RequestHeader("username") String username) {
        log.info("order id: {}", id);
        log.info("user is: {}", username);

        return new OrderInfo(id, id * 2);
    }
}
