package kz.ctrlbee.repository;

import kz.ctrlbee.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    List<Room> findAllByNameContainingIgnoreCase(String pattern);
}
