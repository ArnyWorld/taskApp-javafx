package arni.task.controllers;

import arni.task.models.Task;
import arni.task.services.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.*;

@Component
public class IndexController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private TaskService taskService;
    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, Integer> idColumnTask;
    @FXML
    private TableColumn<Task, String> nameColumnTask;
    @FXML
    private TableColumn<Task, Integer> managerColumnTask;
    @FXML
    private TableColumn<Task, Integer> statusColumnTask;

    private final ObservableList<Task> taskList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurationColumns();
        listTasks();
    }

    private void configurationColumns(){
        idColumnTask.setCellValueFactory(new PropertyValueFactory<>("idTask"));
        nameColumnTask.setCellValueFactory(new PropertyValueFactory<>("nameTask"));
        managerColumnTask.setCellValueFactory(new PropertyValueFactory<>("manager"));
        statusColumnTask.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void listTasks(){
        logger.info("Task List...");
        taskList.clear();
        taskList.addAll(taskService.listTasks());
        taskTable.setItems(taskList);
    }
}
