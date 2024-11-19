package test;

import model.Epic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class EpicTest {

    @Test
    void epicsEqualByID() {
        Epic epic1 = new Epic("Epic 1", "Description 1", 1);
        Epic epic2 = new Epic("Epic 1", "Description 1", 1);
        Epic epic3 = new Epic("Epic 2", "Description 2", 2);

        assertEquals(epic1, epic2, "Tasks with  ID should be equal");
        assertNotEquals(epic2, epic3, "Epics with different ID should not be equal");
    }
}