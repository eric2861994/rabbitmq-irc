package if4031.client;

import com.rabbitmq.client.*;
import if4031.client.util.RandomString;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class IRCClient {

    private final Connection connection;
    private final Channel channel;
    private final Consumer consumer;

    private String nickname;
    private Set<String> joinedChannel = new HashSet<String>();

    public IRCClient(String server, int port, String exchangeName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(server);
//        factory.setPort(port);
        connection = factory.newConnection();
        channel = connection.createChannel();

        String clientQueueName = channel.queueDeclare().getQueue();
//        consumer = new QueueingConsumer(channel);
        consumer = new QueueingConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(clientQueueName, true, consumer);
        nickname = new RandomString().randomString(16);
    }

    void start() {
    }

    void stop() throws IOException {
        connection.close();
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
    public List<Message> getMessages() {
        return null;
    }

    /**
     * Join a channel.
     * Equivalent to binding to our queue to an exchange in rabbitgMQ.
     *
     * @param channelName channel to join
     */
    public void joinChannel(String channelName) {
        this.joinedChannel.add(channelName);
        channel.queueBind(queueName, EXCHANGE_NAME, severity);
    }

    /**
     * Leave a channel.
     * Equivalent to unbinding our queue from an exchange in rabbitMQ.
     *
     * @param channelName channel to leave.
     */
    public void leaveChannel(String channelName) {
        this.joinedChannel.remove(channelName);
    }

    /**
     * Send Message to all joined channels.
     * Equivalent to sending messages to many exchanges in rabbitMQ.
     * joined channel is maintained in client.
     * TODO implement
     *
     * @param message message
     */
    public void sendMessageAll(String message) {

    }

    /**
     * Send Message to a specific joined channel.
     * Equivalent to sending message to an exchange in rabbitMQ.
     * must check first whether we have joined the channel or not.
     * joined channel is maintained in client.
     * TODO implement
     *
     * @param channelName channel name
     * @param message     message
     */
    public void sendMessageChannel(String channelName, String message) {

    }
}
