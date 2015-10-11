package if4031.client;

import if4031.client.command.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class CLInterface {

    private final Scanner scanner;
    private final PrintStream out;
    private final IRCClient ircClient;
    private final CommandParser commandParser = new CommandParser();

    CLInterface(Scanner _scanner, PrintStream _out, IRCClient _ircClient) {
        scanner = _scanner;
        out = _out;
        ircClient = _ircClient;
    }

    private void printMessages() {
        List<Message> messageList = ircClient.getMessages();
        if (messageList != null) {
            for (Message message : messageList) {
                out.println("[" + message.getChannel() + "] " + message.getBody());
            }
        }
    }

    void run() {
        // display welcome message
        out.println(WELCOME_MESSAGE);

        // main loop
        String commandString;
        while (true) {
            printMessages();
            out.print(COMMAND_PROMPT);
            commandString = scanner.nextLine();
            CommandParser.ParseResult parseResult = commandParser.parse(commandString);

            CommandParser.ParseStatus status = parseResult.getStatus();
            if (status == CommandParser.ParseStatus.OK) {
                Command cmd = parseResult.getCommand();
                process(cmd);

            } else if (status == CommandParser.ParseStatus.EXIT) {
                break;
            }
        }
    }

    void process(Command command) {
        if (command instanceof ChangeNicknameCommand) {
            ChangeNicknameCommand cmd = (ChangeNicknameCommand) command;
            String newNickname = cmd.getNewNickname();
            ircClient.changeNickname(newNickname);
            out.println("Nickname changed to " + newNickname);

        } else if (command instanceof JoinChannelCommand) {
            JoinChannelCommand cmd = (JoinChannelCommand) command;
            String channelName = cmd.getChannelName();
            try {
                ircClient.joinChannel(channelName);
                out.println("Joined channel: " + channelName);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (command instanceof LeaveChannelCommand) {
            LeaveChannelCommand cmd = (LeaveChannelCommand) command;
            String channelName = cmd.getChannelName();
            try {
                ircClient.leaveChannel(channelName);
                out.println("Left channel: " + channelName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (command instanceof SendMessageAll) {
            SendMessageAll cmd = (SendMessageAll) command;
            try {
                ircClient.sendMessageAll(cmd.getMessage());
                out.println("Message sent");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (command instanceof SendMessageChannel) {
            SendMessageChannel cmd = (SendMessageChannel) command;
            try {
                ircClient.sendMessageChannel(cmd.getChannelName(), cmd.getMessage());
                out.println("Message sent");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // never happen
        }
    }

    private static String PROGRAM_NAME = "Rabbit MQ - IRC";
    private static String WELCOME_MESSAGE = "Welcome to " + PROGRAM_NAME + "!\nChange your nickname with /nick command\n";
    private static String COMMAND_PROMPT = ">> ";
}
