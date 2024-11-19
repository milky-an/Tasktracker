package model;

import enums.Stage;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public void updateEpicStage() {
        if (subtasks.isEmpty() || subtasks.stream().allMatch(subtask -> subtask.getStage() == Stage.NEW)) {
            this.setStage(Stage.NEW);
        } else if (subtasks.stream().allMatch(subtask -> subtask.getStage() == Stage.DONE)) {
            this.setStage(Stage.DONE);
        } else {
            this.setStage(Stage.IN_PROGRESS);
        }
    }
}
