package if4031.client;

import if4031.client.command.Command;
import if4031.client.command.IRCCommandFactory;
import if4031.client.rpc.Message;

import java.io.PrintStream;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

class CLInterface implements IRCClientListener {

    private final Scanner scanner;
    private final PrintStream out;
    private final IRCClient ircClient;

    private Queue<String> messagesQ = new LinkedBlockingQueue<>();

    CLInterface(Scanner _scanner, PrintStream _out, IRCClient _ircClient) {
        scanner = _scanner;
        out = _out;
        ircClient = _ircClient;
    }

    private void printMessages() {
        while (!messagesQ.isEmpty()) {
            out.println(messagesQ.remove());
        }
    }

    void run() {
        // display welcome message
        out.println(WELCOME_MESSAGE);

        // get nickname
        String nickname;
        do {
            out.print(NICKNAME_PROMPT);
            nickname = scanner.nextLine();
        } while (nickname.contains(" "));

        ircClient.login(nickname);

        // main loop
        String commandString;
        while (true) {
            printMessages();

            out.print(COMMAND_PROMPT);
            commandString = scanner.nextLine();

            if (commandString.equals("/exit")) {
                break;
            }

            if (commandString.equals("")) {

            } else {
                ircClient.parseExecute(commandString);
            }
        }

        ircClient.logout();
        printMessages();
    }

    void parseExecute(String line) {
        IRCCommandFactory.ParseResult parseResult = ircCommandFactory.parse(line);

        IRCCommandFactory.ParseStatus status = parseResult.getStatus();
        if (status == IRCCommandFactory.ParseStatus.OK) {
            Command command = parseResult.getCommand();
            command.execute(this);
        }
    }

    @Override
    public void notifyFailure(int failureCode, String reason) {
        messagesQ.add("Failure " + failureCode + ": " + reason);
    }

    @Override
    public void notifyLeaveChannel(String channelName) {
        messagesQ.add("Left channel: " + channelName);
    }

    @Override
    public void notifyJoinChannel(String channelName) {
        messagesQ.add("Joined channel: " + channelName);
    }

    @Override
    public void notifyLogin() {
        messagesQ.add("Logged in.");
    }

    @Override
    public void notifyLogout() {
        messagesQ.add("Logged out.");
    }

    @Override
    public void notifyMessageArrive(List<Message> messages) {
        for (Message m : messages) {
            messagesQ.add("[" + m.getChannel() + "] (" + m.getSender() + "): " + m.getBody());
        }
    }

    @Override
    public void notifyNicknameChange(String newNickname) {
        messagesQ.add("Nickname changed to " + newNickname);
    }

    private static String PROGRAM_NAME = "gRPC IRC";
    private static String WELCOME_MESSAGE = "Welcome to " + PROGRAM_NAME + "!\nEnter your nickname to login..\n";
    private static String ERROR_MESSAGE = "Error!";
    private static String NICKNAME_PROMPT = "nickname: ";
    private static String COMMAND_PROMPT = ">> ";
}
