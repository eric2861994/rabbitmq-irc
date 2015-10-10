package if4031.client.rpc;

import java.util.List;

public interface RPCClient {

    /**
     * Get userID, this userID will be used for future requests.
     *
     * @param nickname nickname
     */
    int login(String nickname) throws RPCException;

    /**
     * Change nickname.
     *
     * @param user    user id
     * @param newNick new nickname
     */
    boolean changeNickname(int user, String newNick) throws RPCException;

    void logout(int user) throws RPCException;

    /**
     * Join a channel.
     *
     * @param user    user id
     * @param channel channel name
     */
    void joinChannel(int user, String channel) throws RPCException;

    /**
     * Get Messages.
     *
     * @param user user id
     */
    List<Message> getMessages(int user) throws RPCException;

    /**
     * Send message to a certain channel. Returns Messages for user as well.
     *
     * @param user    user id
     * @param channel channel name
     * @param message message
     */
    List<Message> sendMessageToChannel(int user, String channel, String message) throws RPCException;

    /**
     * Send message to all subscribed channels. Returns messages as well.
     *
     * @param user    user id
     * @param message message
     */
    List<Message> sendMessage(int user, String message) throws RPCException;

    /**
     * Leave a channel.
     *
     * @param user    user id
     * @param channel channel name
     */
    void leaveChannel(int user, String channel) throws RPCException;
}
