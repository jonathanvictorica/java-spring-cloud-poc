package com.jmg.primemusic.suggestion.event;

import com.jmg.primemusic.suggestion.config.RabbitMQConfig;
import com.jmg.primemusic.suggestion.repository.MusicRepositoryMongo;
import com.jmg.primemusic.suggestion.model.MusicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class NewMusicEventConsumer {

    private final MusicRepositoryMongo musicRepositoryMongo;

    public NewMusicEventConsumer(MusicRepositoryMongo musicRepositoryMongo) {
        this.musicRepositoryMongo = musicRepositoryMongo;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MUSIC)
    public void execute(NewMusicEventConsumer.Data data) {
        MusicEntity musicNew= new MusicEntity();
        BeanUtils.copyProperties(data.getMusic(),musicNew);
        musicRepositoryMongo.deleteById(data.getMusic().getMusicId());
        musicRepositoryMongo.save(musicNew);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data implements Serializable {
        private MusicDto music=new MusicDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MusicDto{
            private Long musicId;
            private String name;
            private String singerName;
            private Integer mgCount;
        }

    }
}
