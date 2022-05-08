package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.GameEvent;

import java.util.List;

public interface GameEventRepository extends JpaRepository<GameEvent,Long>
{
    List<GameEvent> findAllByGameId(long gameId);
}