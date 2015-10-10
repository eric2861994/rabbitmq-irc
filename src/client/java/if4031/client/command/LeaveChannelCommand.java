package if4031.client.command;

import if4031.client.IRCClient;

class LeaveChannelCommand implements Command {
    private final String channelName;

    LeaveChannelCommand(String _channelName) {
        channelName = _channelName;
    }

    @Override
    public String toString() {
        return "/leave " + channelName;
    }

    @Override
    public void execute(IRCClient ircClient) {
        ircClient.leaveChannel(channelName);
    }
}
