package config;


import org.activiti.engine.event.EventLogEntry;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author lkmc2
 * @date 2018/8/24
 * @description 事件监听器配置测试
 */
public class ConfigEventListenerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigEventListenerTest.class);

    // 系统将自动生成流程实例
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_eventlistener.cfg.xml");

    // 自动加载xml文件
    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void test() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        activitiRule.getTaskService().complete(task.getId());

    }

}
