package app.service;

import java.util.List;
import java.util.Objects;

import app.entity.Game;
import app.entity.User;
import org.springframework.stereotype.Service;
import app.entity.GameEvent;
import app.repository.GameEventRepository;

@Service
public class GameEventService
{
	private GameEventRepository gameEventRepository;
	private GameService gameService;
	public GameEventService(GameEventRepository gameEventRepository,GameService gameService)
	{
		this.gameEventRepository=gameEventRepository;
		this.gameService=gameService;
	}
	public GameEvent save(GameEvent gameEvent,User user)
	{
		Game game=gameService.findById(gameEvent.getGame().getId());
		if(Objects.equals(game.getUser().getId(),user.getId()))
			gameEvent.setSymbol('X');
		else
			gameEvent.setSymbol('O');
		return gameEventRepository.save(gameEvent);
	}
    public List<GameEvent> findAllByGameId(long gameId)
    {
		return gameEventRepository.findAllByGameId(gameId);
    }
}