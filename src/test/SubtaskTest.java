package test;

import model.Epic;
import model.Subtask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class SubtaskTest {
    @Test
    void subtasksEqualByID() {
        Epic epic = new Epic("Epic", "Description");
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 2, epic);
        Subtask subtask2 = new Subtask("Subtask 1", "Description 1", 2, epic);
        Subtask subtask3 = new Subtask("Subtask 2", "Description 2", 3, epic);

        assertEquals(subtask1, subtask2, "Subtasks with  ID should be equal");
        assertNotEquals(subtask1, subtask3, "Tasks with different ID should not be equal");
    }
}