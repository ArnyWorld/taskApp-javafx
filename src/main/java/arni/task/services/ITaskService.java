package arni.task.services;

import arni.task.models.Task;

import java.util.List;

public interface ITaskService {
    public List<Task> listTasks();
    public Task listTaskById(Integer id);
    public void saveTask(Task task);
    public void deleteTask(Integer id);
}
