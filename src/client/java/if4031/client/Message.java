package if4031.client;

public class Message {
    private String channel;
    private String body;

    Message(String _channel, String _body) {
        setChannel(_channel);
        setBody(_body);
    }

    public Message(Message m) {
        setChannel(m.getChannel());
        setBody(m.getBody());
    }

    public String getChannel() {
        return channel;
    }

    public String getBody() {
        return body;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
