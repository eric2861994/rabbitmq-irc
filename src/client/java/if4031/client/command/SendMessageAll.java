package if4031.client.command;

import if4031.client.IRCClient;

public class SendMessageAll implements Command {
    private final String message;

    SendMessageAll(String _message) {
        message = _message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return '\\' + message;
    }
}
