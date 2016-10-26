package cz.atria.lsd.util.entity;

/**
 * Created by Alexander on 10.03.14.
 */
public enum  TaskState
{
    SCHEDULED(false),
    STARTED(false),
    SUCCEEDED(true),
    FAILED(true);

    private boolean finished;

    private TaskState(boolean finished)
    {
        this.finished = finished;
    }

    public boolean isFinished()
    {
        return finished;
    }
}
