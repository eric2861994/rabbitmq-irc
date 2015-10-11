package if4031.client;

import com.rabbitmq.client.*;
import if4031.client.util.RandomString;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class IRCClient {

    private final Connection connection;
    private final Channel channel;
    private final Consumer consumer;
    private final String exchangeName;
    private final String clientQueueName;
    private final Object messageQueueLock = new Object();

    private String nickname;
    private Set<String> joinedChannel = new HashSet<String>();

    private Queue<Message> messageQueue = new LinkedList<Message>();

    public IRCClient(String server, int port, String _exchangeName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(server);
        factory.setPort(port);
        connection = factory.newConnection();
        channel = connection.createChannel();

        clientQueueName = channel.queueDeclare().getQueue();
        consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                String messageBody = new String(body, "UTF-8");
                Message message = new Message(envelope.getRoutingKey(), messageBody);
                synchronized (messageQueueLock) {
                    messageQueue.add(message);
                }
            }
        };
        channel.basicConsume(clientQueueName, true, consumer);

        nickname = new RandomString().randomString(16);

        exchangeName = _exchangeName;
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
     */
    public List<Message> getMessages() {
        List<Message> result = new ArrayList<Message>();
        synchronized (messageQueueLock) {
            while (!messageQueue.isEmpty()) {
                result.add(messageQueue.remove());
            }
        }
        return result;
    }

    /**
     * Join a channel.
     * Equivalent to binding to our queue to an exchange in rabbitgMQ.
     *
     * @param channelName channel to join
     */
    public void joinChannel(String channelName) throws IOException {
        this.joinedChannel.add(channelName);
        channel.queueBind(clientQueueName, exchangeName, channelName);
    }

    /**
     * Leave a channel.
     * Equivalent to unbinding our queue from an exchange in rabbitMQ.
     *
     * @param channelName channel to leave.
     */
    public void leaveChannel(String channelName) throws IOException {
        this.joinedChannel.remove(channelName);
        channel.queueUnbind(clientQueueName, exchangeName, channelName);
    }

    /**
     * Send Message to all joined channels.
     * Equivalent to sending messages to many exchanges in rabbitMQ.
     * joined channel is maintained in client.
     *
     * @param message message
     */
    public void sendMessageAll(String message) throws IOException {
        String sent = "(" + nickname + "): " + message;
        for (String mychannel : joinedChannel) {
            channel.basicPublish(exchangeName, mychannel, null, sent.getBytes());
        }
    }

    /**
     * Send Message to a specific joined channel.
     * Equivalent to sending message to an exchange in rabbitMQ.
     * must check first whether we have joined the channel or not.
     * joined channel is maintained in client.
     *
     * @param channelName channel name
     * @param message     message
     */
    public void sendMessageChannel(String channelName, String message) throws IOException {
        if (joinedChannel.contains(channelName)) {
            String sent = "(" + nickname + "): " + message;
            channel.basicPublish(exchangeName, channelName, null, sent.getBytes());
        }
    }
}
