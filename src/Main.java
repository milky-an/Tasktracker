import enums.Stage;
import model.Epic;
import model.Subtask;
import model.Task;
import service.Managers;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.createTask(epic1);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic1);

        taskManager.createTask(subtask1);
        taskManager.createTask(subtask2);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        taskManager.createTask(epic2);

        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", epic2);
        taskManager.createTask(subtask3);

        System.out.println("Список задач: " + taskManager.getAllTasks());
        System.out.println("Список эпиков: " + taskManager.getAllEpics());
        System.out.println("Список подзадач: " + taskManager.getAllSubtasks());

        task1.setStage(Stage.DONE);
        taskManager.updateTask(task1);

        subtask1.setStage(Stage.DONE);
        taskManager.updateTask(subtask1);

        subtask2.setStage(Stage.IN_PROGRESS);
        taskManager.updateTask(subtask2);

        subtask3.setStage(Stage.DONE);
        taskManager.updateTask(subtask3);

        System.out.println("После изменения статусов:");
        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());

        System.out.println("Проверка статусов эпиков:");
        System.out.println("Эпик 1: " + taskManager.getTaskById(epic1.getId()));
        System.out.println("Эпик 2: " + taskManager.getTaskById(epic2.getId()));

        taskManager.deleteAllTasks();
        taskManager.deleteAllEpics();

        System.out.println("После удаления:");
        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());
    }
}
