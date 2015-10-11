package if4031.client;

public class Message {

    private String sender;
    private String channel;
    private String body;
    private long sendTime;

    Message(String _sender, String _channel, String _body, long _sendTime) {
        setSender(_sender);
        setChannel(_channel);
        setBody(_body);
        setSendTime(_sendTime);
    }

    public Message(Message m) {
        setSender(m.getSender());
        setChannel(m.getChannel());
        setBody(m.getBody());
        setSendTime(m.getSendTime());
    }

    public String getSender() {
        return sender;
    }

    public String getChannel() {
        return channel;
    }

    public String getBody() {
        return body;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
