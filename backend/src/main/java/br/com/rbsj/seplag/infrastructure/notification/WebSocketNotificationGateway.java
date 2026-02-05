package br.com.rbsj.seplag.infrastructure.notification;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.notification.NotificationGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSocketNotificationGateway implements NotificationGateway {

    private final SimpMessagingTemplate template;

    public WebSocketNotificationGateway(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void notifyNewAlbum(Album album) {

        log.debug("Notificando novo álbum via WebSocket: id={} titulo={}", album.getId().getValue(), album.getTitulo());
        template.convertAndSend("/topic/albuns", album);
    }
}
