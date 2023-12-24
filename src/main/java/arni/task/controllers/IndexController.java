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
    @FXML
    private TextField textTask;
    @FXML
    private TextField textManager;
    @FXML
    private TextField textStatus;

    private Integer idTask;

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

    public void addTask(){
        if(textTask.getText().isEmpty()){
            showMessage("Error Validation", "task name is required");
            textTask.requestFocus();
            return;
        }else{
            var task = new Task();
            getDataForm(task);
            task.setIdTask(null);
            taskService.saveTask(task);
            showMessage("Information", "Task Saved");
            clearForm();
            listTasks();

        }
    }

    public void loadFormTask(){
        var task = taskTable.getSelectionModel().getSelectedItem();
        if(task!=null){
            idTask = task.getIdTask();
            textTask.setText(task.getNameTask());
            textManager.setText(task.getManager());
            textStatus.setText(task.getStatus());
        }
    }

    private void getDataForm(Task task){
        if(idTask!=null) {
            task.setIdTask(idTask);
            task.setNameTask(textTask.getText());
            task.setManager(textManager.getText());
            task.setStatus(textStatus.getText());
        }
    }

    private void showMessage(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle((title));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void modifyTask(){
        if(idTask==null){
            showMessage("Information", "select a task");
            return;
        }
        if(textTask.getText().isEmpty()){
            showMessage("Error Validation", "Select a task");
            textTask.requestFocus();
            return;
        }
        var task = new Task();
        getDataForm(task);
        taskService.saveTask(task);
        showMessage("Information", "Task edited");
        clearForm();
        listTasks();

    }

    public void deleteTask(){
        var task = taskTable.getSelectionModel().getSelectedItem();
        if(task!=null){
            logger.info("delete Register"+task.toString());
            taskService.deleteTask(task.getIdTask());
            showMessage("Information", "Deleted task"+task.getIdTask());
            clearForm();
            listTasks();
        }
        else{
            showMessage("Error", "Select a row");
        }
    }
    public void clearForm(){
        idTask = null;
        textTask.clear();
        textManager.clear();
        textStatus.clear();

    }

}
