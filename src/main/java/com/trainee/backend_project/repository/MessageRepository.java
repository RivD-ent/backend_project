package com.trainee.backend_project.repository;

import com.trainee.backend_project.model.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	boolean existsByMessageContent(String messageContent);

	List<Message> findByPropertyId(Long propertyId);

	List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

	@Query("""
		select m from Message m
		where ((m.sender.id = :userId and m.receiver.id = :otherUserId)
			or (m.sender.id = :otherUserId and m.receiver.id = :userId))
			and ((:propertyId is null and m.property is null) or m.property.id = :propertyId)
		order by m.sentAt asc, m.id asc
		""")
	List<Message> findConversationMessages(
		@Param("propertyId") Long propertyId,
		@Param("userId") Long userId,
		@Param("otherUserId") Long otherUserId
	);
}
