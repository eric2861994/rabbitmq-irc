package if4031.client.command;

import if4031.client.IRCClient;

/**
 * Command to send a message to a specific channel.
 */
public class SendMessageChannel implements Command {
    private final String channelName;
    private final String message;

    SendMessageChannel(String _channelName, String _message) {
        channelName = _channelName;
        message = _message;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return '@' + channelName + ' ' + message;
    }
}
