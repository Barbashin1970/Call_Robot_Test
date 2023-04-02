package userdata;

public class DataUser {

    private UserInfo data;
    private String error;
    private String message;

    public DataUser(UserInfo data, String error, String message) {
        this.data = data;
        this.error = error;
        this.message = message;
    }

    public DataUser() {
    }

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}