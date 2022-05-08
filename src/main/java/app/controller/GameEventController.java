package app.controller;

import app.entity.GameEvent;
import app.entity.User;
import app.service.GameEventService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/gameEvent")
public class GameEventController
{
	private GameEventService gameEventService;
	public GameEventController(GameEventService gameEventService)
	{
		this.gameEventService=gameEventService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public GameEvent save(@RequestBody GameEvent gameEvent,Authentication authentication)
	{
		User user=(User)authentication.getPrincipal();
		return gameEventService.save(gameEvent,user);
	}
	@GetMapping(params="gameId")
	public List<GameEvent> findAllByGameId(@RequestParam long gameId)
	{
		return gameEventService.findAllByGameId(gameId);
	}
}