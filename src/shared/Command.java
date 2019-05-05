package shared;

import java.io.Serializable;

public class Command implements Serializable {
    private static final long serialVersionUID = 1L;

    private String command;
    private Object data;
    private Object credentials;

    public Object getCredentials() {
        return credentials;
    }

    public Command(String command) {
        this.command = command;
        this.data = "";
    }

    public Command(String command, String data) {
        this.command = command;
        this.data = data;
    }

    public Command(String command, String data, String credentials) {
        this.command = command;
        this.data = data;
        this.credentials = credentials;
    }

    public String getCommand() {
        return command;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Command{" +
                "command='" + command + '\'' +
                ", data=" + data + '\'' +
                ", credentials=" + credentials + '\'' +
                '}';
    }
}