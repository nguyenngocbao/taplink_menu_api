package taplink.network.menu.api.services;

import taplink.network.menu.api.dtos.email.EmailRequestDto;

public interface NotificationService {
    void sendNotification(EmailRequestDto emailRequestDto);
}
