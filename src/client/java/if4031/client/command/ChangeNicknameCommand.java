package if4031.client.command;

import if4031.client.IRCClient;

public class ChangeNicknameCommand implements Command {
    private String newNickname;

    ChangeNicknameCommand(String _newNickname) {
        newNickname = _newNickname;
    }

    public String getNewNickname() {
        return newNickname;
    }

    @Override
    public String toString() {
        return "/nick " + newNickname;
    }
}
