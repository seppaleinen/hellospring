package se.david.task;

import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.repository.TaskExecution;

import java.util.logging.Logger;

public class TaskListener implements TaskExecutionListener {
    private final static Logger LOGGER = Logger.getLogger(TaskListener.class.getName());

    @Override
    public void onTaskEnd(TaskExecution arg0) {
        LOGGER.info("Task Ended");
    }

    @Override
    public void onTaskFailed(TaskExecution arg0, Throwable arg1) {
        LOGGER.severe("Task Failed");
    }

    @Override
    public void onTaskStartup(TaskExecution arg0) {
        LOGGER.info("Task Startup");
    }
}
