package com.niu.security.orderapi.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @PostMapping
    @ApiOperation("create order")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public OrderInfo create(@RequestBody OrderInfo info) {
        PriceInfo priceInfo = oAuth2RestTemplate.getForObject("http://localhost:7016/price/" + info.getProductId(), PriceInfo.class);
        log.debug("price is {}", priceInfo.getPrice());
        return info;
    }

    @GetMapping("/{id}")
    @ApiOperation("get order info")
    public OrderInfo getOrderInfo(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal String username) {
        log.info("order id: {}", id);
        log.info("user is: {}", username);

        return new OrderInfo(id, id * 2);
    }
}
