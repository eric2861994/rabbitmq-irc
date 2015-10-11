package if4031.client;

import if4031.client.exception.ChannelException;
import if4031.server.filter.ValidUserId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nim_13512065 on 9/17/15.
 */

@ValidUserId
public class IRCChannel {
    private String channelName;
    private List<Integer> integers;

    public IRCChannel(String channelName) {
        this.setChannelName(channelName);
        this.setIntegers(new ArrayList<Integer>());
    }

    public IRCChannel(IRCChannel ircChannel) {
        this.setChannelName(ircChannel.getChannelName());

        this.setIntegers(new ArrayList<Integer>());
        for (Integer integer : ircChannel.getIntegers()) {
            this.getIntegers().add(integer);
        }

    }

    public List<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(List<Integer> integers) {
        this.integers = integers;
    }

    public void addUser(Integer integer) throws ChannelException {
        if (integers.contains(integer)) {
            throw new ChannelException("user already joined");
        } else {
            integers.add(integer);
        }
    }

    public void removeUser(Integer integer)  {
        if (integers.contains(integer)) {
            integers.remove(integer);
        }
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
