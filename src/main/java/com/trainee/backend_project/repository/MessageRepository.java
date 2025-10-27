package com.trainee.backend_project.repository;

import com.trainee.backend_project.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Query helpers

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	boolean existsByMessageContent(String messageContent);

	List<Message> findByPropertyId(Long propertyId);
}
