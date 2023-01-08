package com.jmg.primemusic.suggestion.event;

import com.jmg.primemusic.suggestion.config.RabbitMQConfig;
import com.jmg.primemusic.suggestion.model.PlaylistEntity;
import com.jmg.primemusic.suggestion.repository.PlaylistRepositoryMongo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewPlaylistEventConsumer {

    private final PlaylistRepositoryMongo playlistRepositoryMongo;

    public NewPlaylistEventConsumer(PlaylistRepositoryMongo playlistRepositoryMongo) {
        this.playlistRepositoryMongo = playlistRepositoryMongo;
    }
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_PLAYLIST)
    public void execute(NewPlaylistEventConsumer.Data data) {
        PlaylistEntity playlistNew= new PlaylistEntity();
        BeanUtils.copyProperties(data.getPlaylist(),playlistNew);
        if (data.getPlaylist().getMusics() != null && playlistNew.getMusics() != null) {
            BeanUtils.copyProperties(data.getPlaylist().getMusics(),playlistNew.getMusics());
        }
        playlistRepositoryMongo.deleteById(data.getPlaylist().getPlayListId());
        playlistRepositoryMongo.save(playlistNew);
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;
        private Data.PlaylistDto playlist= new PlaylistDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PlaylistDto implements Serializable {

            @Serial
            private static final long serialVersionUID = 1L;
            private Long playListId;
            private String name;
            private Integer mgCount;
            private List<Data.PlaylistDto.PlaylistMusicDto> musics = new ArrayList<>();

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
