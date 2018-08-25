package com.lin.activiti.interceptor;

import org.activiti.engine.debug.ExecutionTreeUtil;
import org.activiti.engine.impl.agenda.AbstractOperation;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.logging.LogMDC;

/**
 * @author lkmc2
 * @date 2018/8/25
 * @description MDC命令执行器
 */

public class MDCCommandInvoker extends DebugCommandInvoker {

    public void executeOperation(Runnable runnable) {
        // 获取原本MDC记录器是否生效
        boolean mdcEnabled = LogMDC.isMDCEnabled();

        // 启动MDC异常记录器
        LogMDC.setMDCEnabled(true);
        if (runnable instanceof AbstractOperation) {
            AbstractOperation operation = (AbstractOperation)runnable;
            if (operation.getExecution() != null) {
                // 将可执行的操作添加到MDC异常记录器中
                LogMDC.putMDCExecution(operation.getExecution());
            }
        }
        super.executeOperation(runnable);
        // 清除MDC记录
        LogMDC.clear();

        // 如果原本MDC记录器不生效，取消MDC记录器的启动
        if (!mdcEnabled) {
            LogMDC.setMDCEnabled(false);
        }
    }

}
