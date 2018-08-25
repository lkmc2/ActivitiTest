package config;

import org.activiti.engine.logging.LogMDC;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author lkmc2
 * @date 2018/8/24
 * @description 数据库配置测试
 */
public class ConfigMDCTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigMDCTest.class);

    // 系统将自动生成流程实例
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_mdc.cfg.xml");

    // 自动加载xml文件
    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void test() {
        // 开启MDC打印（只有在运行报错时，才会打印MDC日志）
        LogMDC.setMDCEnabled(true);

        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        assertNotNull(processInstance);

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        assertEquals("Activiti is awesome!", task.getName());

        activitiRule.getTaskService().complete(task.getId());
    }

}
