package netty.message;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString(callSuper = true)
public class GroupMembersResponseMessage extends Message {

    private Set<Channel> members;

    public GroupMembersResponseMessage(Set<Channel> members) {
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GroupMembersResponseMessage;
    }
}
