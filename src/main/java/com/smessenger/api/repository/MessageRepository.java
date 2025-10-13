package com.smessenger.api.repository;

import com.smessenger.api.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

  // Get recent messages from a sender to a receiver, ordered descending by
  // createdAt
  @Query("SELECT m FROM Message m WHERE m.senderId = :senderId AND m.receiverId = :receiverId ORDER BY m.createdAt DESC")
  List<Message> findRecentBetween(@Param("senderId") Long senderId,
      @Param("receiverId") Long receiverId,
      Pageable pageable);

  // Get messages from sender->receiver with id greater than given id (assumes
  // monotonic ids)
  @Query("SELECT m FROM Message m WHERE m.senderId = :senderId AND m.receiverId = :receiverId AND m.id > :sinceId ORDER BY m.id ASC")
  List<Message> findSinceId(@Param("senderId") Long senderId,
      @Param("receiverId") Long receiverId,
      @Param("sinceId") Long sinceId);
}
