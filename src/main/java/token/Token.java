package token;

public class Token {
    private DataToken data;
    private String error;
    private String message;

    public Token(DataToken data, String error, String message) {
        this.data = data;
        this.error = error;
        this.message = message;
    }

    public DataToken getData() {
        return data;
    }

    public void setData(DataToken data) {
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
