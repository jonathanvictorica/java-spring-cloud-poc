package com.jmg.primemusic.playlist.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-music")
public interface MusicFeign {

    @GetMapping("/api/v1/musics/{id}")
    Music getById(@PathVariable Long id);

    @Getter
    @Setter
    class Music {
        private Long musicId;
        private String name;
    }
}
