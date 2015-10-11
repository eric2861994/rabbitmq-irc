package if4031.server;

/**
 * Created by nim_13512065 on 10/11/15.
 */
public class IRCMessage {
    private String sender;
    private String channel;
    private String body;
    private long sendTime;

    public IRCMessage(IRCMessage m) {
        this.setSender(m.getSender());
        this.setChannel(m.getChannel());
        this.setBody(m.getBody());
        this.setSendTime(m.getSendTime());
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
