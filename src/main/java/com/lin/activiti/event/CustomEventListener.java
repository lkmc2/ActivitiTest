package com.lin.activiti.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lkmc2
 * @date 2018/8/26
 * @description 自定义事件监听器
 */

public class CustomEventListener implements ActivitiEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomEventListener.class);


    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        ActivitiEventType eventType = activitiEvent.getType();

        if (ActivitiEventType.PROCESS_STARTED.equals(eventType)) {
            // 自定义事件的getProcessInstanceId为null
            LOGGER.info("监听到自定义事件 {} \t {}", eventType, activitiEvent.getProcessInstanceId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
