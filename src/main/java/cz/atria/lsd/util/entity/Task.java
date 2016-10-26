package cz.atria.lsd.util.entity;

/**
 * Created by Alexander on 10.03.14.
 */
public class Task
{
    private Integer id;

    private Integer totalAmount;

    private Integer handledAmount;

    private String statusText;

    private TaskState state;

    private String error;

    public Task()
    {
        state = TaskState.SCHEDULED;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getHandledAmount() {
        return handledAmount;
    }

    public void setHandledAmount(Integer handledAmount) {
        this.handledAmount = handledAmount;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
