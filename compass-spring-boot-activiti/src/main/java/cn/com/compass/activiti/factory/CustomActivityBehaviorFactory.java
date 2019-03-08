package cn.com.compass.activiti.factory;

import cn.com.compass.activiti.behavior.CustomBusinessRuleTaskActivityBehavior;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.BusinessRuleTaskDelegate;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 重写drools规则引擎
 * @date 2019/3/7 11:05
 */
public class CustomActivityBehaviorFactory extends DefaultActivityBehaviorFactory {

    @Override
    public ActivityBehavior createBusinessRuleTaskActivityBehavior(BusinessRuleTask businessRuleTask) {
        BusinessRuleTaskDelegate ruleActivity = null;
        if (StringUtils.isNotEmpty(businessRuleTask.getClassName())){
            try {
                Class<?> clazz = Class.forName(businessRuleTask.getClassName());
                ruleActivity = (BusinessRuleTaskDelegate) clazz.newInstance();
            } catch (Exception e) {
                throw new ActivitiException("Could not instantiate businessRuleTask (id:" + businessRuleTask.getId()  + ") class: " +
                        businessRuleTask.getClassName(), e);
            }
        } else {
            ruleActivity = new CustomBusinessRuleTaskActivityBehavior();
        }

        for (String ruleVariableInputObject : businessRuleTask.getInputVariables()) {
            ruleActivity.addRuleVariableInputIdExpression(expressionManager.createExpression(ruleVariableInputObject.trim()));
        }

        for (String rule : businessRuleTask.getRuleNames()) {
            ruleActivity.addRuleIdExpression(expressionManager.createExpression(rule.trim()));
        }

        ruleActivity.setExclude(businessRuleTask.isExclude());

        if (businessRuleTask.getResultVariableName() != null && businessRuleTask.getResultVariableName().length() > 0) {
            ruleActivity.setResultVariable(businessRuleTask.getResultVariableName());
        } else {
            ruleActivity.setResultVariable("org.activiti.engine.rules.OUTPUT");
        }

        return ruleActivity;
    }
}
