package com.app.util.message.validator.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.app.util.message.validator.domain.MessagePattern;

public interface MessagePatternRepository extends JpaRepository<MessagePattern, Integer> {

    @RestResource(path = "findByIdentifier", rel = "findByIdentifier")
    @Query("SELECT m FROM MessagePattern m WHERE lower(m.name) = lower(:name)")
    public List<MessagePattern> findByIdentifier(@Param("name") String name);

}
