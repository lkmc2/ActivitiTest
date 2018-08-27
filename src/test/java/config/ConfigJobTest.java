package config;

import org.activiti.engine.logging.LogMDC;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author lkmc2
 * @date 2018/8/27
 * @description 定时任务配置测试
 */
public class ConfigJobTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigJobTest.class);

    // 系统将自动生成流程实例
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_job.cfg.xml");

    // 自动加载xml文件
    @Test
    @Deployment(resources = {"my-process_job.bpmn20.xml"})
    public void test() throws InterruptedException {
        LOGGER.info("start");

        // 查询定时任务
        List<Job> jobList = activitiRule.getManagementService()
                .createTimerJobQuery()
                .listPage(0, 100);

        for (Job job : jobList) {
            LOGGER.info("定时任务 {}，默认重试次数 = {}", job, job.getRetries());
        }
        LOGGER.info("jobList.size = {}", jobList.size());

        Thread.sleep(1000); // 休眠10秒

        LOGGER.info("end");
    }

}
