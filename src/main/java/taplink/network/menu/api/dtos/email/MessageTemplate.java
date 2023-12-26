package taplink.network.menu.api.dtos.email;

import taplink.network.menu.api.commons.enums.MessageType;

public interface MessageTemplate {
    MessageType getMessageType();
    String getTemplateName();
}
