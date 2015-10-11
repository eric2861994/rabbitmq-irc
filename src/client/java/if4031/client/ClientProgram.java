package if4031.client;

import if4031.client.config.ClientConfiguration;
import if4031.client.config.PropertyConfiguration;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class ClientProgram {

    private static final String PROPERTY_FILE = "/client.properties";

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {

        ClientConfiguration configuration = new PropertyConfiguration(PROPERTY_FILE);
        String serverAddress = configuration.getString("serverAddress");
        int serverPort = configuration.getInt("serverPort");
        String serverExchange = configuration.getString("serverExchange");

        IRCClient ircClient = new IRCClient(serverAddress, serverPort, serverExchange);
        Scanner scanner = new Scanner(System.in);
        CLInterface clInterface = new CLInterface(scanner, System.out, ircClient);

        ircClient.start();
        clInterface.run();

        ircClient.stop();
    }
}
