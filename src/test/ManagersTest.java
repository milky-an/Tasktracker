package test;

import org.junit.jupiter.api.Test;
import service.HistoryManager;
import service.Managers;
import service.TaskManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {
    @Test
    void getDefaultReturnTaskManager() {
        TaskManager taskManager = Managers.getDefault();

        assertNotNull(taskManager, "getDefault should return a non-null TaskManager.");
    }

    @Test
    void getDefaultHistoryReturnHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(historyManager, "getDefaultHistory should return a non-null HistoryManager.");
    }
}