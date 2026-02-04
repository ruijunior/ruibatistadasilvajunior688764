package br.com.rbsj.seplag.infrastructure.notification;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.notification.NotificationGateway;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketNotificationGateway implements NotificationGateway {

    private final SimpMessagingTemplate template;

    public WebSocketNotificationGateway(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void notifyNewAlbum(Album album) {
        // Envia o objeto álbum completo para o tópico /topic/albuns
        // O Frontend (Angular) vai assinar esse tópico para exibir o alerta
        template.convertAndSend("/topic/albuns", album);
    }
}
