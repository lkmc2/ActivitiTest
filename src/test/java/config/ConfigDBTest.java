package config;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lkmc2
 * @date 2018/8/24
 * @description 数据库配置测试
 */
public class ConfigDBTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigDBTest.class);

    @Test
    public void testConfig1() {
        // 获取默认的流程引擎对象（读取activiti.cfg.xml配置文件，基于Spring）
        ProcessEngineConfiguration configuration =
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();

        LOGGER.info("configuration = {}", configuration);
        // 运行结果：configuration = org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration@7651218e

        ProcessEngine processEngine = configuration.buildProcessEngine();

        LOGGER.info("获取流程引擎 {}", processEngine.getName());
        // 运行结果：获取流程引擎 default
        processEngine.close();

    }

}
