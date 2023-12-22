package arni.task.services;

import arni.task.models.Task;
import arni.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService{

    @Autowired
    TaskRepository taskRepository;
    @Override
    public List<Task> listTasks() {
        return this.taskRepository.findAll();
    }

    @Override
    public Task listTaskById(Integer id) {
        Task task = this.taskRepository.findById(id).orElse(null);
        return task;
    }

    @Override
    public void saveTask(Task task) {
        this.taskRepository.save(task);
    }

    @Override
    public void deleteTask(Integer id) {
        this.taskRepository.deleteById(id);
    }

}
