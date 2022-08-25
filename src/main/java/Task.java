public class Task {
    protected String description;
    protected boolean isDone = false;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String toDataString(String separator) {
        return String.format("%s%d%s", separator, (this.isDone) ? 1 : 0, this.description);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s",getStatusIcon(), this.description);
    }
}
