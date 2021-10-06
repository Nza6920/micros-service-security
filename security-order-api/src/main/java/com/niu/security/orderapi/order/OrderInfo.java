package com.niu.security.orderapi.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 订单详情
 *
 * @author [nza]
 * @version 1.0 [2021/08/01 13:00]
 * @createTime [2021/08/01 13:00]
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {

    private Long id;

    private Long productId;
}
