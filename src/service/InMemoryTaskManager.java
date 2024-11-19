package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private static int taskID = 1;
    private HashMap<Integer, Task> taskMap = new HashMap<>();
    private HashMap<Integer, Epic> epicMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskMap = new HashMap<>();
    private HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    private int generateTaskID() {
        return taskID++;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicMap.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtaskMap.values());
    }

    @Override
    public void deleteAllTasks() {
        deleteTasksFromHistory(taskMap.values());
        taskMap.clear();
    }

    @Override
    public void deleteAllEpics() {
        deleteTasksFromHistory(epicMap.values());
        epicMap.values().forEach(epic -> deleteTasksFromHistory(epic.getSubtasks()));
        epicMap.values().forEach(epic -> {
            epic.getSubtasks().forEach(subtask -> subtaskMap.remove(subtask.getId()));
        });
        epicMap.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        deleteTasksFromHistory(subtaskMap.values());
        subtaskMap.values().forEach(subtask -> subtask.getEpic().removeSubtask(subtask));
        subtaskMap.clear();
    }

    @Override
    public Task getTaskById(int id) {
        if (subtaskMap.containsKey(id)) {
            Task task = subtaskMap.get(id);
            historyManager.add(task);
            return task;
        } else if (epicMap.containsKey(id)) {
            Task task = epicMap.get(id);
            historyManager.add(task);
            return task;
        } else if (taskMap.containsKey(id)) {
            Task task = taskMap.get(id);
            historyManager.add(task);
            return task;
        }
        return null;
    }

    @Override
    public <T extends Task> void createTask(T task) {
        task.setId(generateTaskID());
        if (task instanceof Epic) {
            epicMap.put(task.getId(), (Epic) task);
        } else if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            subtaskMap.put(task.getId(), subtask);
            Epic epic = subtask.getEpic();
            if (epic != null) {
                epic.addSubtask(subtask);
                epic.updateEpicStage();
            }
        } else {
            taskMap.put(task.getId(), task);
        }
    }


    @Override
    public void updateTask(Task task) {
        if (task instanceof Epic) {
            Epic epic = (Epic) task;
            epicMap.put(task.getId(), epic);
            epic.updateEpicStage();
        } else if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            subtaskMap.put(task.getId(), subtask);
            subtask.getEpic().updateEpicStage();
        } else {
            taskMap.put(task.getId(), task);
        }
    }

    @Override
    public void deleteTaskById(int id) {
        deleteTaskFromHistory(taskMap.get(id));
        taskMap.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epicMap.remove(id);
        deleteTasksFromHistory(epic.getSubtasks());
        if (epic != null) {
            epic.getSubtasks().forEach(subtask -> subtaskMap.remove(subtask.getId()));
            deleteTaskFromHistory(epic);
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtaskMap.remove(id);
        if (subtask != null) {
            Epic epic = subtask.getEpic();
            if (epic != null) {
                epic.removeSubtask(subtask);
                epic.updateEpicStage();
                deleteTaskFromHistory(epic);
            }
            deleteTaskFromHistory(subtask);
        }
    }

    @Override
    public ArrayList<Subtask> getSubtaskByEpicID(int epicId) {
        Epic epic = epicMap.get(epicId);
        if (epic != null) {
            return epic.getSubtasks();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private <T extends Task> void deleteTasksFromHistory(Iterable<T> tasks) {
        for (T task : tasks) {
            deleteTaskFromHistory(task);
        }
    }

    private <T extends Task> void deleteTaskFromHistory(T task) {
        if (historyManager.getHistory().contains(task)) {
            historyManager.add(null);
        }
    }
}
