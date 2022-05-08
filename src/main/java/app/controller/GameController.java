package app.controller;

import app.entity.Game;
import app.entity.User;
import app.service.GameService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/game")
public class GameController
{
	private GameService gameService;
	public GameController(GameService gameService)
	{
		this.gameService=gameService;
	}
	@PostMapping("/{id}/join")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void join(@PathVariable long id,Authentication authentication)
	{
		User user=(User)authentication.getPrincipal();
		gameService.join(id,user);
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Game save(@RequestBody Game game,Authentication authentication)
	{
		User user=(User)authentication.getPrincipal();
		game.setUser(user);
		return gameService.save(game);
	}
	@GetMapping("/{id}")
	public Game findById(@PathVariable Long id)
	{
		return gameService.findById(id);
	}
}