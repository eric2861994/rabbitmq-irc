package if4031.client;

import if4031.client.rpc.Message;

import java.util.List;

public interface IRCClientListener {
    void notifyFailure(int failureCode, String reason);

    void notifyLeaveChannel(String channelName);

    void notifyJoinChannel(String channelName);

    void notifyLogin();

    void notifyLogout();

    void notifyMessageArrive(List<Message> messages);

    void notifyNicknameChange(String newNickname);
}
