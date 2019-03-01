package cn.com.compass.activiti.command;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 跳过任务节点
 * @date 2019/3/1 10:27
 */
public class JumpTaskCmd implements Command<Void> {

    /**
     * 当前任务的executionId
     */
    private String executionId;
    /**
     * 跳转目标activityId
     */
    private String activityId;
    /**
     * 跳过原因
     */
    private String reason;

    public JumpTaskCmd(){

    }

    public JumpTaskCmd(String executionId,String activityId,String reason){
        this.executionId = executionId;
        this.activityId = activityId;
        this.reason = reason;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        TaskEntityManager taskEntityManager = commandContext.getTaskEntityManager();
        List<TaskEntity> tasks =  taskEntityManager.findTasksByExecutionId(executionId);
        if (CollectionUtils.isNotEmpty(tasks)) {
            for (TaskEntity taskEntity : tasks) {
                taskEntityManager.deleteTask(taskEntity, reason, false);
            }
        }
        ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findExecutionById(executionId);
        ProcessDefinitionImpl processDefinition = executionEntity.getProcessDefinition();
        ActivityImpl activity = processDefinition.findActivity(activityId);
        executionEntity.executeActivity(activity);
        return null;
    }
}
