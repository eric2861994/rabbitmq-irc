package if4031.client.command;

import if4031.client.IRCClient;

class ChangeNicknameCommand implements Command {
    private String newNickname;

    ChangeNicknameCommand(String _newNickname) {
        newNickname = _newNickname;
    }

    @Override
    public String toString() {
        return "/nick " + newNickname;
    }

    @Override
    public void execute(IRCClient ircClient) {
        ircClient.changeNickname(newNickname);
    }
}
