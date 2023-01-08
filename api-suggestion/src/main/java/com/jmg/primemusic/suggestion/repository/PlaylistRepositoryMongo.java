package com.jmg.primemusic.suggestion.repository;

import com.jmg.primemusic.suggestion.model.PlaylistEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepositoryMongo extends MongoRepository<PlaylistEntity,Long> {
}
