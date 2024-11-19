package test;

import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TaskTest {

    @Test
    void tasksEqualByID() {
        Task task1 = new Task("Task 1", "Description", 1);
        Task task2 = new Task("Task 1", "Description", 1);
        Task task3 = new Task("Task 2", "Description", 2);

        assertEquals(task1, task2, "Tasks with  ID should be equal");
        assertNotEquals(task1, task3, "Tasks with different ID should not be equal");
    }
}