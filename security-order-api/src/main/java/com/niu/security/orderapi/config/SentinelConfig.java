package com.niu.security.orderapi.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
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
        // 设置流控规则
        FlowRule rule = new FlowRule();
        // 设置资源名称
        rule.setResource("createOrder");
        // 设置策略
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(10);
        List<FlowRule> rules = Lists.newArrayList(rule);
        FlowRuleManager.loadRules(rules);

        // 设置降级规则
        DegradeRule degradeRule = new DegradeRule();
        // 设置资源名称
        degradeRule.setResource("createOrder");
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // 超过十秒的服务
        degradeRule.setCount(10);
        // 设置熔断持续时间
        degradeRule.setTimeWindow(10);
        List<DegradeRule> degradeRules = Lists.newArrayList();
        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);

        // 设置参数限流规则
        //        ParamFlowRule paramFlowRule = new ParamFlowRule();
        //        paramFlowRule.setResource("getOrderInfo");
    }
}
