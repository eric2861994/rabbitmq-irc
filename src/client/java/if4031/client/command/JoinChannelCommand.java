package if4031.client.command;

import if4031.client.IRCClient;

public class JoinChannelCommand implements Command {
    private final String channelName;

    JoinChannelCommand(String _channelName) {
        channelName = _channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public String toString() {
        return "/join " + channelName;
    }
}
