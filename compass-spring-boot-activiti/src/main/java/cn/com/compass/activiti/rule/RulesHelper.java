package cn.com.compass.activiti.rule;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.kie.api.KieBase;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/3/7 11:29
 */
public class RulesHelper {

    /**
     * 通过部署Id获取规则文件，可能存在多服务节点问题 待验证 FIXME
     * @param deploymentId
     * @return
     */
    public static KieBase findKnowledgeBaseByDeploymentId(String deploymentId) {
        DeploymentCache<Object> knowledgeBaseCache = Context
                .getProcessEngineConfiguration()
                .getDeploymentManager()
                .getKnowledgeBaseCache();

        KieBase knowledgeBase = (KieBase) knowledgeBaseCache.get(deploymentId);
        if (knowledgeBase==null) {
            DeploymentEntity deployment = Context
                    .getCommandContext()
                    .getDeploymentEntityManager()
                    .findDeploymentById(deploymentId);
            if (deployment==null) {
                throw new ActivitiObjectNotFoundException("no deployment with id "+deploymentId, Deployment.class);
            }
            Context
                    .getProcessEngineConfiguration()
                    .getDeploymentManager()
                    .deploy(deployment);
            knowledgeBase = (KieBase) knowledgeBaseCache.get(deploymentId);
            if (knowledgeBase==null) {
                throw new ActivitiException("deployment "+deploymentId+" doesn't contain any rules");
            }
        }
        return knowledgeBase;
    }
}
