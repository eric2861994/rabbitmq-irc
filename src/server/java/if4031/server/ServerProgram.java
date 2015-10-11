package if4031.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by nim_13512065 on 9/27/15.
 */
public class ServerProgram {
    private final static String QUEUE_NAME = "hello";
    private static final String PROPERTY_FILE = "/server.properties";

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        System.out.println("ASDFADS");
        String serverAddress = "localhost";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(serverAddress);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("direct_logs","direct");
        String severity = "mysevere";
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello Worldb!";
        channel.basicPublish("direct_logs", severity, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }

}
