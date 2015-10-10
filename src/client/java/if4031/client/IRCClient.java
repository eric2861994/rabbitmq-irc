package if4031.client;

import com.rabbitmq.client.*;
import if4031.client.command.Command;
import if4031.client.command.IRCCommandFactory;
import if4031.client.executor.DelayableRepeatingExecutor;
import if4031.client.rpc.Message;
import if4031.client.rpc.RPCException;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class IRCClient {

    private final Connection connection;
    private final Channel channel;
    private final Consumer consumer;

    // TODO generate random nickname
    private String nickname;

    // obsolete instance variable below

    private final IRCCommandFactory ircCommandFactory = new IRCCommandFactory();
    private IRCClientListener ircClientListener;
    private int userID;
    private int channelCount;
    private Set<String> joinedChannel = new HashSet<String>();

    public IRCClient(String server, int port) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(server);
        factory.setPort(port);
        connection = factory.newConnection();
        channel = connection.createChannel();

        String clientQueueName = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(clientQueueName, true, consumer);
    }

    void start() {
    }

    void stop() throws IOException {
        connection.close();
    }

    public void setIrcClientListener(IRCClientListener _ircClientListener) {
        ircClientListener = _ircClientListener;
    }

    /**
     * Change nickname.
     * Nickname is stored in client because rabbitmq server cannot store it.
     *
     * @param newNickname new nickname.
     */
    public void changeNickname(String newNickname) {
        nickname = newNickname;
    }

    /**
     * Get messages from our queue in rabbitMQ.
     * Notify listener for the new messages.
     * TODO implement
     */
    public void getMessages() {
    }

    /**
     * Join a channel.
     * Equivalent to binding to our queue to an exchange in rabbitgMQ.
     * TODO implement
     * @param channelName    channel to join
     */
    public void joinChannel(String channelName) {
    }

    /**
     * Leave a channel.
     * Equivalent to unbinding our queue from an exchange in rabbitMQ.
     * TODO implement
     * @param channelName    channel to leave.
     */
    public void leaveChannel(String channelName) {

    }

    /**
     * Send Message to all joined channels.
     * Equivalent to sending messages to many exchanges in rabbitMQ.
     * joined channel is maintained in client.
     * TODO implement
     * @param message    message
     */
    public void sendMessageAll(String message) {

    }

    /**
     * Send Message to a specific joined channel.
     * Equivalent to sending message to an exchange in rabbitMQ.
     * must check first whether we have joined the channel or not.
     * joined channel is maintained in client.
     * TODO implement
     * @param channelName    channel name
     * @param message        message
     */
    public void sendMessageChannel(String channelName, String message) {

    }

    void parseExecute(String line) {
        IRCCommandFactory.ParseResult parseResult = ircCommandFactory.parse(line);

        IRCCommandFactory.ParseStatus status = parseResult.getStatus();
        if (status == IRCCommandFactory.ParseStatus.OK) {
            Command command = parseResult.getCommand();
            command.execute(this);
        }
    }
}
