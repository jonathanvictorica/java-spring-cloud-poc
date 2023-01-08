package com.jmg.primemusic.playlist.service;

import com.jmg.primemusic.playlist.event.NewPlaylistEvent;
import com.jmg.primemusic.playlist.client.MusicFeign;
import com.jmg.primemusic.playlist.model.PlayListMusic;
import com.jmg.primemusic.playlist.model.Playlist;
import com.jmg.primemusic.playlist.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final NewPlaylistEvent newPlaylistEvent;
    private final MusicFeign musicFeign;

    public PlaylistService(PlaylistRepository playlistRepository, NewPlaylistEvent newPlaylistEvent, MusicFeign musicFeign) {
        this.playlistRepository = playlistRepository;
        this.newPlaylistEvent = newPlaylistEvent;
        this.musicFeign = musicFeign;
    }

    public void save(Playlist playlist) {
        playlistRepository.save(playlist);
        newPlaylistEvent.execute(playlist);
    }


    public List<Playlist> getAll() {
        return playlistRepository.findAll();
    }

    public Playlist getById(Long id) {
        return playlistRepository.findById(id).orElse(null);
    }


    public void deleteById(Long id) {
        playlistRepository.deleteById(id);
    }

    public void update(Playlist playlist) {
        if (playlistRepository.existsById(playlist.getPlayListId())) {
            playlistRepository.save(playlist);
        }
    }

    public void addMusic(Long idPlayList, Long idMusic) throws Exception {
        var playList = playlistRepository.findById(idPlayList);
        if (playList.isPresent()) {
            var result = musicFeign.getById(idMusic);
            if (result == null) {
              throw new Exception("Music not found");
            }
            playList.get().getMusics().add(new PlayListMusic(null, playList.get(),result.getMusicId(),result.getName()));
            playlistRepository.save(playList.get());

            newPlaylistEvent.execute(playList.get());
        }else{
            throw new Exception("Playlist not found");
        }
    }
}
