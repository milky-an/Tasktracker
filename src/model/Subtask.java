package model;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(String name, String description, Epic epic) {
        super(name, description);
        setEpic(epic);
    }

    public Subtask(String name, String description, int id, Epic epic) {
        super(name, description, id);
        setEpic(epic);
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Epic cannot be null.");
        }
        if (this.epic != epic) {
            this.epic = epic;
            epic.addSubtask(this);
        }
    }
}
