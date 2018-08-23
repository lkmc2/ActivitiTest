package com.lin.activiti;

import com.google.common.collect.Maps;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.DateFormType;
import org.activiti.engine.impl.form.StringFormType;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


/**
 * @author lkmc2
 * @date 2018/8/22
 * @description 启动类
 */

public class DemoMain {
    public static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) throws ParseException {
        LOGGER.info("启动程序");

        // 1.创建流程引擎
        ProcessEngine processEngine = getProcessEngine();

        // 2.部署流程定义文件
        ProcessDefinition processDefinition = getProcessDefinition(processEngine);

        // 3.启动流程引擎
        ProcessInstance processInstance = getProcessInstance(processEngine, processDefinition);

        // 4.处理流程任务
        Scanner scanner = new Scanner(System.in);

        while (processInstance != null && !processInstance.isEnded()) {
            TaskService taskService = processEngine.getTaskService();
            List<Task> list = taskService.createTaskQuery().list();
            LOGGER.info("待处理任务数量 [{}]", list.size());

            for (Task task : list) {
                LOGGER.info("待处理任务 [{}]", task.getName());
                FormService formService = processEngine.getFormService();
                TaskFormData taskFormData = formService.getTaskFormData(task.getId());
                List<FormProperty> formProperties = taskFormData.getFormProperties();
                HashMap<String, Object> variables = Maps.newHashMap();

                for (FormProperty property : formProperties) {
                    String line = null;

                    if (StringFormType.class.isInstance(property.getType())) {
                        LOGGER.info("请输入 {}？", property.getName());
                        line = scanner.nextLine();
                        variables.put(property.getId(), line);
                    } else if (DateFormType.class.isInstance(property.getType())) {
                        LOGGER.info("请输入 {}？ 格式（yyyy-MM-dd）", property.getName());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        line = scanner.nextLine();
                        Date date = dateFormat.parse(line);
                        variables.put(property.getId(), date);
                    } else {
                        LOGGER.info("类型不支持 {}", property.getType());
                    }
                    LOGGER.info("输入的内容是 [{}]", line);
                }

                // 提交任务（任务id，参数）
                taskService.complete(task.getId(), variables);

                // 重新查询获取任务实例
                processInstance = processEngine.getRuntimeService()
                        .createProcessInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .singleResult();
            }
        }

        LOGGER.info("结束程序");
    }

    /**
     * 启动流程引擎
     * @param processEngine 流程引擎
     * @param processDefinition 流程配置
     * @return 流程实例
     */
    private static ProcessInstance getProcessInstance(ProcessEngine processEngine, ProcessDefinition processDefinition) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        LOGGER.info("启动流程 [{}]", processInstance.getProcessDefinitionKey());
        return processInstance;
    }

    /**
     * 部署流程定义文件
     * @param processEngine 流程引擎
     * @return 流程定义文件
     */
    private static ProcessDefinition getProcessDefinition(ProcessEngine processEngine) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addClasspathResource("event.bpmn20.xml");
        Deployment deploy = deploymentBuilder.deploy();

        String deploymentId = deploy.getId();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();

        LOGGER.info("流程定义文件 [{}]，流程ID [{}]", processDefinition.getName(), processDefinition.getId());
        return processDefinition;
    }

    /**
     * 创建流程引擎
     * @return 流程引擎
     */
    private static ProcessEngine getProcessEngine() {
        // 基于默认数据库的流程引擎
        ProcessEngineConfiguration config =
                ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        ProcessEngine processEngine = config.buildProcessEngine();
        String name = processEngine.getName();
        String version = ProcessEngine.VERSION;

        LOGGER.info("流程引擎名称 [{}],版本 [{}]", name, version);
        return processEngine;
    }

}
