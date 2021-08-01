package com.niu.security.orderapi.order;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 价格详情
 *
 * @version 1.0 [2021/08/01 13:11]
 * @author [nza]
 * @createTime [2021/08/01 13:11]
 */
@Data
@Accessors(chain = true)
public class PriceInfo {

    private Long id;

    private BigDecimal price;
}
