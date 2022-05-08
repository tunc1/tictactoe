package app.service;

import app.entity.User;
import org.springframework.stereotype.Service;
import app.entity.Game;
import app.repository.GameRepository;
import javax.persistence.EntityNotFoundException;

@Service
public class GameService
{
	private GameRepository gameRepository;
	public GameService(GameRepository gameRepository)
	{
		this.gameRepository=gameRepository;
	}
	public Game save(Game game)
	{
		return gameRepository.save(game);
	}
	public Game findById(Long id)
	{
		return gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
    public void join(long id,User user)
    {
    	Game game=gameRepository.findById(id).get();
    	game.setUser2(user);
		gameRepository.save(game);
    }
}