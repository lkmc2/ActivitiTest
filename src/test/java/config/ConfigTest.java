package config;

import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lkmc2
 * @date 2018/8/24
 * @description 配置测试类
 */

public class ConfigTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigTest.class);

    @Test
    public void testConfig1() {
        // 获取默认的流程引擎对象（读取activiti.cfg.xml配置文件）
        ProcessEngineConfiguration configuration =
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();

        LOGGER.info("configuration = {}", configuration);
        // 运行结果：configuration = org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration@7651218e
    }

}
