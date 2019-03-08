package cn.com.compass.activiti.behavior;

import cn.com.compass.activiti.rule.RulesAgendaFilter;
import cn.com.compass.activiti.rule.RulesHelper;
import cn.com.compass.web.context.AppContext;
import org.activiti.engine.delegate.BusinessRuleTaskDelegate;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.TaskActivityBehavior;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.repository.ProcessDefinition;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


import java.util.*;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 自定义规则任务行为实现
 * @date 2019/3/7 11:10
 */
public class CustomBusinessRuleTaskActivityBehavior extends TaskActivityBehavior implements BusinessRuleTaskDelegate {

    private static final long serialVersionUID = 1L;
    protected Set<Expression> variablesInputExpressions = new HashSet<Expression>();
    protected Set<Expression> rulesExpressions = new HashSet<Expression>();
    protected boolean exclude = false;
    protected String resultVariable;

    public CustomBusinessRuleTaskActivityBehavior(){

    }

    @Override
    public void execute(ActivityExecution execution) throws Exception {
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl) execution.getEngineServices().getProcessEngineConfiguration();
        ProcessDefinition processDefinition = processEngineConfiguration.getDeploymentManager().findDeployedProcessDefinitionById(
                execution.getProcessDefinitionId());
        String deploymentId = processDefinition.getDeploymentId();

        KieBase knowledgeBase = RulesHelper.findKnowledgeBaseByDeploymentId(deploymentId);
        KieSession ksession = knowledgeBase.newKieSession();

        if (variablesInputExpressions != null) {
            Iterator<Expression> itVariable = variablesInputExpressions.iterator();
            while (itVariable.hasNext()) {
                Expression variable = itVariable.next();
                ksession.insert(variable.getValue(execution));
            }
        }
        /**
         * 匹配rule规则名字或者分组的方法：
         * 1.自定义过滤器:RulesAgendaFilter
         * 2.使用drools提供的各种过滤器:RuleNameEndsWithAgendaFilter/RuleNameEqualsAgendaFilter/RuleNameMatchesAgendaFilter/
         * RuleNameSerializationAgendaFilter/RuleNameStartsWithAgendaFilter
         * 3.通过agenda-group进行匹配，drl中rule设置agenda-group分组，同时
         * ksession.getAgenda().getAgendaGroup("xx").setFocus();即可，需要额外的在drl文件中增加agenda-group分组
         */
        if (!rulesExpressions.isEmpty()) {
            RulesAgendaFilter filter = new RulesAgendaFilter();
            Iterator<Expression> itRuleNames = rulesExpressions.iterator();
            while (itRuleNames.hasNext()) {
                Expression ruleName = itRuleNames.next();
                filter.addSuffic(ruleName.getValue(execution).toString());
            }
            filter.setAccept(!exclude);
            ksession.fireAllRules(filter);
        } else {
            ksession.fireAllRules();
        }

        Collection<? extends Object> ruleOutputObjects = ksession.getObjects();
        if (ruleOutputObjects != null && !ruleOutputObjects.isEmpty()) {
            Collection<Object> outputVariables = new ArrayList<Object>();
            for (Object object : ruleOutputObjects) {
                outputVariables.add(object);
            }
            execution.setVariable(resultVariable, outputVariables);
        }
        ksession.dispose();
        leave(execution);
    }

    @Override
    public void addRuleVariableInputIdExpression(Expression inputId) {
        this.variablesInputExpressions.add(inputId);
    }

    @Override
    public void addRuleIdExpression(Expression inputId) {
        this.rulesExpressions.add(inputId);
    }

    @Override
    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    @Override
    public void setResultVariable(String resultVariableName) {
        this.resultVariable = resultVariableName;
    }
}
