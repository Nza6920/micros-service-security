package com.niu.security.orderapi.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SentinelConfig
 * <p>
 * ApplicationListener<ContextRefreshedEvent> bean 装配完毕会触发此事件
 *
 * @author [nza]
 * @createTime [2022/01/03 22:18]
 */
@Component
@Slf4j
public class SentinelConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("开始配置 sentinel........");
        // 设置规则
        FlowRule rule = new FlowRule();
        rule.setResource("createOrder");
        // 设置策略
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);
        List<FlowRule> rules = Lists.newArrayList(rule);
        FlowRuleManager.loadRules(rules);
    }
}
