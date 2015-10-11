package if4031.client;

import com.rabbitmq.client.*;
import if4031.client.config.ClientConfiguration;
import if4031.client.config.PropertyConfiguration;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class ClientProgram {

    private static final String PROPERTY_FILE = "/client.properties";
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {

//        ClientConfiguration configuration = new PropertyConfiguration(PROPERTY_FILE);
//        String serverAddress = "localhost";
//        int serverPort = 9090;
//
////        String serverAddress = configuration.getString("serverAddress");
////        int serverPort = configuration.getInt("serverPort");
//        IRCClient ircClient = new IRCClient(serverAddress, serverPort);
//        Scanner scanner = new Scanner(System.in);
//        CLInterface clInterface = new CLInterface(scanner, System.out, ircClient);
//
//        ircClient.start();
//        clInterface.run();
//
//        ircClient.stop();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("direct_logs", "direct");
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String queuename = channel.queueDeclare().getQueue();
        channel.queueBind(queuename, "direct_logs", "mysevere");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queuename, true, consumer);
    }
}
