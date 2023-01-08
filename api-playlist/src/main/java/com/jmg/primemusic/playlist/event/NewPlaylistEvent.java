package com.jmg.primemusic.playlist.event;

import com.jmg.primemusic.playlist.config.RabbitMQConfig;
import com.jmg.primemusic.playlist.model.Playlist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewPlaylistEvent {

    private final RabbitTemplate rabbitTemplate;

    public NewPlaylistEvent(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void execute(Playlist playlist) {
        NewPlaylistEvent.Data data = new NewPlaylistEvent.Data();
        BeanUtils.copyProperties(playlist, data.getPlaylist());
        if (data.getPlaylist().getMusics() != null && playlist.getMusics() != null) {
            BeanUtils.copyProperties(playlist.getMusics(), data.getPlaylist().getMusics());
        }
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.TOPIC_NEW_PLAYLIST, data);
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;
        private PlaylistDto playlist = new PlaylistDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PlaylistDto implements Serializable {

            @Serial
            private static final long serialVersionUID = 1L;
            private Long playListId;
            private String name;
            private Integer mgCount = 0;
            private List<PlaylistMusicDto> musics = new ArrayList<>();

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class PlaylistMusicDto implements Serializable {

                @Serial
                private static final long serialVersionUID = 1L;
                private Long musicId;
                private String musicName;
            }
        }

    }
}
