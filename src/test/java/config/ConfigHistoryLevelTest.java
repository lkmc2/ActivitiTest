package config;

import com.google.common.collect.Maps;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author lkmc2
 * @date 2018/8/24
 * @description 数据库配置测试
 */
public class ConfigHistoryLevelTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigHistoryLevelTest.class);

    // 系统将自动生成流程实例
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_history.cfg.xml");

    // 自动加载xml文件
    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void test() {
        // 1.启动流程
        startProcessInstance();

        // 2.修改变量
        changeVariable();

        // 3.提交表单task
        submitTaskFormData();

        // 输出历史内容
        // 4.输出历史活动
        showHistoryActivity();
        // 5.输出历史变量
        showHistoryVariable();

        // 6.输出历史用户任务
        showHistroyTask();
        // 7.输出历史表单
        showHistoryForm();

        // 8.输出历史详情
        showHistroyDetail();
    }

    private void showHistroyDetail() {
        List<HistoricDetail> historicDetails = activitiRule.getHistoryService()
                .createHistoricDetailQuery()
                .listPage(0, 100);

        for (HistoricDetail historicDetail : historicDetails) {
            LOGGER.info("historicDetail = {}", toString(historicDetail));
        }
        LOGGER.info("historicDetails.size = {}", historicDetails.size());
    }

    private void showHistoryForm() {
        List<HistoricDetail> historicDetailsForm = activitiRule.getHistoryService()
                .createHistoricDetailQuery()
                .formProperties()
                .listPage(0, 100);

        for (HistoricDetail historicDetail : historicDetailsForm) {
            LOGGER.info("historicDetail = {}", toString(historicDetail));
        }
        LOGGER.info("historicDetailsForm.size = {}", historicDetailsForm.size());
    }

    private void showHistroyTask() {
        List<HistoricTaskInstance> historicTaskInstances = activitiRule.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .listPage(0, 100);
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            LOGGER.info("historicTaskInstance = {}", historicTaskInstance);

        }
        LOGGER.info("historicTaskInstances.size = {}", historicTaskInstances.size());
    }

    private void showHistoryVariable() {
        List<HistoricVariableInstance> historicVariableInstances = activitiRule.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .listPage(0, 100);

        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            LOGGER.info("historicActivityInstance = {}", historicVariableInstance);
        }
        LOGGER.info("historicVariableInstances.size = {}", historicVariableInstances.size());
    }

    private void showHistoryActivity() {
        List<HistoricActivityInstance> historicActivityInstances = activitiRule.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .listPage(0, 100);

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("historicActivityInstance = {}", historicActivityInstance);
        }
        LOGGER.info("historicActivityInstances.size = {}", historicActivityInstances.size());
    }

    private void submitTaskFormData() {
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        Map<String, String> properties = Maps.newHashMap();
        properties.put("formKey1", "value1");
        properties.put("formKey2", "value2");
        activitiRule.getFormService().submitTaskFormData(task.getId(), properties);
    }

    private void changeVariable() {
        List<Execution> executions = activitiRule.getRuntimeService().createExecutionQuery()
                .listPage(0, 100);

        for (Execution execution : executions) {
            LOGGER.info("execution = {}", execution);
        }
        LOGGER.info("execution.size = {}", executions.size());

        // 获取第一条记录的id
        String id = executions.iterator().next().getId();
        // 根据id修改keyStart1变量的值
        activitiRule.getRuntimeService().setVariable(id, "keyStart1", "value1_change");
    }

    private void startProcessInstance() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("keyStart1", "value1");
        params.put("keyStart2", "value2");
        ProcessInstance processInstance = activitiRule.getRuntimeService()
                .startProcessInstanceByKey("my-process", params);
    }

    private static String toString(HistoricDetail historicDetail) {
        return ToStringBuilder.reflectionToString(historicDetail, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
