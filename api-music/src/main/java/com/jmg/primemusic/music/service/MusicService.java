package com.jmg.primemusic.music.service;

import com.jmg.primemusic.music.event.NewMusicEvent;
import com.jmg.primemusic.music.model.Music;
import com.jmg.primemusic.music.repository.MusicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {

    private final MusicRepository musicRepository;

    private final NewMusicEvent newMusicEvent;

    public MusicService(MusicRepository musicRepository, NewMusicEvent newMusicEvent) {
        this.musicRepository = musicRepository;
        this.newMusicEvent = newMusicEvent;
    }

    public void save(Music music) {
        musicRepository.save(music);
        newMusicEvent.execute(music);
    }


    public List<Music> getAll() {
        return musicRepository.findAll();
    }

    public Music getById(Long id) {
        return musicRepository.findById(id).orElse(null);
    }


    public void deleteById(Long id) {
        musicRepository.deleteById(id);
    }

    public void update(Music music) {
        if(musicRepository.existsById(music.getMusicId())){
            musicRepository.save(music);
        }
    }
}
