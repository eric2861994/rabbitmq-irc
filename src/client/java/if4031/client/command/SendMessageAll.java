package if4031.client.command;

import if4031.client.IRCClient;

class SendMessageAll implements Command {
    private final String message;

    SendMessageAll(String _message) {
        message = _message;
    }

    @Override
    public String toString() {
        return '\\' + message;
    }

    @Override
    public void execute(IRCClient ircClient) {
        ircClient.sendMessageAll(message);
    }
}
