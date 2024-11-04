package view.commands;

public abstract class Command {
    private String key;
    private String description;

    public Command (String k, String desc) {
        this.key = k;
        this.description = desc;
    }

    public abstract void execute();

    public String getDescription() {
        return this.description;
    }

    public String getKey() {
        return this.key;
    }
}
