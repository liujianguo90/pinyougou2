package pinyougou.entity;

public class Result {
    private boolean success;
    private String message;

    public Result(String message, boolean success) {
        super();
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
