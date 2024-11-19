package test;

import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Managers;
import service.TaskManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void taskManagerAddsAndFindsTasksById() {
        Task task1 = new Task("Task 1", "Description 1", 1);
        Epic epic = new Epic("Epic 1", "Epic Description");
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description", epic);

        taskManager.createTask(task1);
        taskManager.createTask(epic);
        taskManager.createTask(subtask);

        assertEquals(task1, taskManager.getTaskById(1), "Task should be retrieved by ID");
        assertEquals(epic, taskManager.getTaskById(epic.getId()), "Epic should be retrieved by ID");
        assertEquals(subtask, taskManager.getTaskById(subtask.getId()), "Subtask should be retrieved by ID");
    }
}
