package com.niu.security.priceapi.price;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 价格 api
 *
 * @author [nza]
 * @version 1.0 [2021/08/01 13:11]
 * @createTime [2021/08/01 13:11]
 */
@RestController
@RequestMapping("/price")
@Slf4j
@Api("price controller")
public class PriceController {

    @GetMapping("/{id}")
    @ApiOperation("get price")
    public PriceInfo getPrice(@PathVariable Long id, @AuthenticationPrincipal String user) {

        log.info("user: {}", user);

        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setId(id)
                .setPrice(new BigDecimal("100"));

        log.debug("product id is {}", priceInfo.getId());
        return priceInfo;
    }
}
