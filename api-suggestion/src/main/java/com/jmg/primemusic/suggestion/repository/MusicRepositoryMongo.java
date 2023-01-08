package com.jmg.primemusic.suggestion.repository;

import com.jmg.primemusic.suggestion.model.MusicEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepositoryMongo extends MongoRepository<MusicEntity,Long> {
}
