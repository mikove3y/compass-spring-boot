/**
 * 
 */
package cn.com.compass.activiti.listener;

import cn.com.compass.activiti.command.AutoCompleteCmd;
import cn.com.compass.web.context.AppContext;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 自动完成流程第一步
 * @date 2018年8月31日 下午2:36:28
 * 
 */
public class AutoCompleteFirstTaskEventListener implements ActivitiEventListener {

	@Override
    public void onEvent(ActivitiEvent activitiEvent) {
        if (!(activitiEvent instanceof ActivitiEntityEventImpl)) {
            return;
        }

        ActivitiEntityEventImpl activitiEntityEventImpl = (ActivitiEntityEventImpl) activitiEvent;
        Object entity = activitiEntityEventImpl.getEntity();

        if (!(entity instanceof TaskEntity)) {
            return;
        }

        TaskEntity taskEntity = (TaskEntity) entity;

        try {
            switch (activitiEvent.getType()) {
                case TASK_CREATED:
                    this.onCreate(taskEntity);
                    break;
            }
        } catch (Exception ex) {
        }
    }

    private void onCreate(DelegateTask delegateTask) throws Exception {
        //如果是流程的第一步，则自动提交
        PvmActivity targetActivity = findFirstActivity(delegateTask.getProcessDefinitionId());

        if (!targetActivity.getId().equals(delegateTask.getExecution().getCurrentActivityId())) {
            return;
        }
        new AutoCompleteCmd(delegateTask.getId(),delegateTask.getVariables(),targetActivity.getProperty("name").toString()).execute(Context.getCommandContext());
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    public RepositoryService getRepositoryService(){
        return AppContext.getInstance().getBean(RepositoryService.class);
    }

    public PvmActivity findFirstActivity(String processDefinitionId) {
        ProcessDefinitionEntity processDefinitionEntity  = (ProcessDefinitionEntity)
                ((RepositoryServiceImpl)getRepositoryService())
                        .getDeployedProcessDefinition(processDefinitionId);

        ActivityImpl startActivity = processDefinitionEntity.getInitial();

        PvmTransition pvmTransition = startActivity.getOutgoingTransitions().get(0);
        PvmActivity targetActivity = pvmTransition.getDestination();

        if (!"userTask".equals(targetActivity.getProperty("type"))) {
            return null;
        }
        return targetActivity;
    }

}
