package if4031.client.rpc;

import if4031.common.*;

import java.util.ArrayList;
import java.util.List;

public class GRPCClient implements RPCClient {

    private final IRCServiceGrpc.IRCServiceBlockingStub gRPCClient;

    public GRPCClient(IRCServiceGrpc.IRCServiceBlockingStub _gRPCClient) {
        gRPCClient = _gRPCClient;
    }

    @Override
    public int login(String nickname) throws RPCException {
        LoginRequest loginRequest = LoginRequest.newBuilder().setNickname(nickname).build();
        LoginResponse loginResponse = gRPCClient.login(loginRequest);
        return loginResponse.getUserID();
    }

    @Override
    public boolean changeNickname(int user, String newNick) throws RPCException {
        ChangeNickRequest changeNicknameRequest = ChangeNickRequest.newBuilder().setUser(user).setNewNickname(newNick).build();
        StatusResponse statusResponse = gRPCClient.changeNickname(changeNicknameRequest);
        return statusResponse.getStatus() == ResponseStatus.OK;
    }

    @Override
    public void logout(int user) throws RPCException {
        ActionRequest actionRequest = ActionRequest.newBuilder().setUser(user).build();
        StatusResponse statusResponse = gRPCClient.logout(actionRequest);
    }

    @Override
    public void joinChannel(int user, String channel) throws RPCException {
        ChannelRequest channelRequest = ChannelRequest.newBuilder().setUser(user).setChannel(channel).build();
        StatusResponse statusResponse = gRPCClient.joinChannel(channelRequest);
    }

    /**
     * Changes remote messages to local messages.
     *
     * @param remoteMessages remote messages
     * @return local messages
     */
    private List<Message> remoteToLocalMessages(List<if4031.common.Message> remoteMessages) {
        List<Message> localMessages = new ArrayList<>();
        for (if4031.common.Message oneRemoteMessage : remoteMessages) {
            Message oneLocalMessage = new Message(
                    oneRemoteMessage.getSender(),
                    oneRemoteMessage.getChannel(),
                    oneRemoteMessage.getBody(),
                    oneRemoteMessage.getSendTime()
            );
            localMessages.add(oneLocalMessage);
        }

        return localMessages;
    }

    @Override
    public List<Message> getMessages(int user) throws RPCException {
        ActionRequest actionRequest = ActionRequest.newBuilder().setUser(user).build();
        MessagesResponse messagesResponse = gRPCClient.getMessage(actionRequest);
        List<if4031.common.Message> remoteMessages = messagesResponse.getMessagesList();
        return remoteToLocalMessages(remoteMessages);
    }

    @Override
    public List<Message> sendMessageToChannel(int user, String channel, String message) throws RPCException {
        ChannelMessageRequest channelMessageRequest = ChannelMessageRequest.newBuilder().setUser(user).setChannel(channel).setMessage(message).build();
        MessagesResponse messagesResponse = gRPCClient.sendMessageToChannel(channelMessageRequest);
        List<if4031.common.Message> remoteMessages = messagesResponse.getMessagesList();
        return remoteToLocalMessages(remoteMessages);
    }

    @Override
    public List<Message> sendMessage(int user, String message) throws RPCException {
        MessageRequest messageRequest = MessageRequest.newBuilder().setUser(user).setMessage(message).build();
        MessagesResponse messagesResponse = gRPCClient.sendMessage(messageRequest);
        List<if4031.common.Message> remoteMessages = messagesResponse.getMessagesList();
        return remoteToLocalMessages(remoteMessages);
    }

    @Override
    public void leaveChannel(int user, String channel) throws RPCException {
        ChannelRequest channelRequest = ChannelRequest.newBuilder().setUser(user).setChannel(channel).build();
        StatusResponse statusResponse = gRPCClient.leaveChannel(channelRequest);
    }
}
