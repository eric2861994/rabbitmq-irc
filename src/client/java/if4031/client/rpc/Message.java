package if4031.client.rpc;

public class Message {

    private final String sender;
    private final String channel;
    private final String body;
    private final long sendTime;

    Message(String _sender, String _channel, String _body, long _sendTime) {
        sender = _sender;
        channel = _channel;
        body = _body;
        sendTime = _sendTime;
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
}
