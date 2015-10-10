package if4031.client.command;

import if4031.client.IRCClient;

/**
 * Abstract representation of IRC Command.
 */
public interface Command {

    /**
     * Execute the command.
     */
    void execute(IRCClient ircClient);
}
