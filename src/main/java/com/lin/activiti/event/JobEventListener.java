package com.lin.activiti.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lkmc2
 * @date 2018/8/27
 * @description 定时任务事件监听器
 */

public class JobEventListener implements ActivitiEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobEventListener.class);

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        ActivitiEventType eventType = activitiEvent.getType();
        String eventName = eventType.name();

        // 筛选出定时任务的事件
        if (eventName.startsWith("TIMER") || eventName.startsWith("JOB")) {
            // 自定义事件的getProcessInstanceId为null
            LOGGER.info("监听到Job定时任务事件 {} \t {}", eventType, activitiEvent.getProcessInstanceId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
