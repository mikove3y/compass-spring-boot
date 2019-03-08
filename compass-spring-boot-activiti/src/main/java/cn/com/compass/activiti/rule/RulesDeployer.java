package cn.com.compass.activiti.rule;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.apache.commons.lang3.StringUtils;
import org.kie.api.KieBase;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.util.Map;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 流程规则引擎部署器
 * @date 2019/3/7 14:36
 */
@Slf4j
public class RulesDeployer implements Deployer {
    @Override
    public void deploy(DeploymentEntity deployment, Map<String, Object> deploymentSettings) {
        log.debug("Processing deployment {}", deployment.getName());

        KnowledgeBuilder knowledgeBuilder = null;

        DeploymentManager deploymentManager = Context
                .getProcessEngineConfiguration()
                .getDeploymentManager();

        Map<String, ResourceEntity> resources = deployment.getResources();
        for (String resourceName : resources.keySet()) {
            log.info("Processing resource {}", resourceName);
            if (StringUtils.isNotEmpty(resourceName)&&ResourceType.determineResourceType(resourceName)!=null) {
                if (knowledgeBuilder==null) {
                    knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
                }
                ResourceEntity resourceEntity = resources.get(resourceName);
                byte[] resourceBytes = resourceEntity.getBytes();
                Resource droolsResource = ResourceFactory.newByteArrayResource(resourceBytes);
                knowledgeBuilder.add(droolsResource, ResourceType.determineResourceType(resourceName));
            }
        }

        if (knowledgeBuilder!=null) {
            KieBase knowledgeBase = knowledgeBuilder.newKieBase();
            // 流程部署时塞入deployCache中
            deploymentManager.getKnowledgeBaseCache().add(deployment.getId(), knowledgeBase);
        }
    }
}
