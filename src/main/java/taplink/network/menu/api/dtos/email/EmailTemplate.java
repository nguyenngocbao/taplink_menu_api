package taplink.network.menu.api.dtos.email;

import taplink.network.menu.api.commons.enums.MessageType;

public abstract class EmailTemplate implements MessageTemplate {
    @Override
    public MessageType getMessageType() {
        return MessageType.EMAIL;
    }
}


