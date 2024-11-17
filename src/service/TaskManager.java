package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int taskID = 1;
    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, Epic> epicMap = new HashMap<>();
    HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    private int generateTaskID() {
        return taskID++;
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicMap.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtaskMap.values());
    }

    public void deleteAllTasks() {
        taskMap.clear();
    }

    public void deleteAllEpics() {
        epicMap.values().forEach(epic -> {
            epic.getSubtasks().forEach(subtask -> subtaskMap.remove(subtask.getId()));
        });
        epicMap.clear();
    }

    public void deleteAllSubtasks() {
        subtaskMap.values().forEach(subtask -> subtask.getEpic().removeSubtask(subtask));
        subtaskMap.clear();
    }

    public Task getTaskById(int id) {
        if (subtaskMap.containsKey(id)) {
            return subtaskMap.get(id);
        } else if (epicMap.containsKey(id)) {
            return epicMap.get(id);
        } else if (taskMap.containsKey(id)) {
            return taskMap.get(id);
        }
        return null;
    }

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

    public <T extends Task> void removeByTaskID(HashMap<Integer, T> map, int id) {
        map.remove(id);
    }

    public ArrayList<Subtask> getSubtaskByEpicID(int epicId) {
        Epic epic = epicMap.get(epicId);
        if (epic != null) {
            return epic.getSubtasks();
        } else {
            return new ArrayList<>();
        }
    }
}
