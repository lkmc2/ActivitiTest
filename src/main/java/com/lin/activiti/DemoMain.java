package com.lin.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author lkmc2
 * @date 2018/8/22
 * @description 启动类
 */

public class DemoMain {
    public static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) {
        LOGGER.info("启动程序");

        // 1.创建流程引擎（创建基于默认数据库的流程引擎）
        ProcessEngineConfiguration config =
                ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        ProcessEngine processEngine = config.buildProcessEngine();
        String name = processEngine.getName();
        String version = ProcessEngine.VERSION;

        LOGGER.info("流程引擎名称{},版本{}", name, version);

        // 2.部署流程定义文件

        // 3.启动流程引擎

        // 4.处理流程任务

        LOGGER.info("结束程序");
    }

}
