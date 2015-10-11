package if4031.client;
import if4031.client.exception.ChannelException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nim_13512065 on 9/17/15.
 */
public class IRCUser {
    private List<String> ircChannelNames;
    private List<Message> messages;
    private int userId;
    private String nickname;

    public IRCUser(int userId, String nickname) {
        this.setIrcChannelNames(new ArrayList<String>());
        this.setMessages(new ArrayList<Message>());
        this.setUserId(userId);
        this.setNickname(nickname);
    }

    public List<String> getIrcChannelNames() {
        return ircChannelNames;
    }

    public void setIrcChannelNames(List<String> ircChannelNames) {
        this.ircChannelNames = ircChannelNames;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void deleteMessageAtChannel(String channel) {
        List<Message> newMessage = new ArrayList<Message>();
        for (Message m : this.getMessages()) {
            if (!m.getChannel().equals(channel)) {
                newMessage.add(new Message(m));
            }
        }
        this.setMessages(newMessage);
    }

    public void addIRCChannels(String newircChannel) {
        this.getIrcChannelNames().add(newircChannel);
    }

    public IRCChannel removeIRCChannel(String channel) throws ChannelException {
        IRCChannel ret = null;
        for (String ircChannel : this.getIrcChannelNames()) {
            if (ircChannel.equals(channel)) {
                ret = new IRCChannel(ircChannel);
                this.getIrcChannelNames().remove(ircChannel);
                return ret;
            }
        }
        throw new ChannelException("channel not found");
    }

    public void addMessage(Message message) {
        this.getMessages().add(message);
    }

    public void deleteMessageAtAllChannel() {
        this.setMessages(new ArrayList<Message>());
    }
}
