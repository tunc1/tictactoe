package app.controller;

import app.entity.Game;
import app.entity.User;
import app.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest
{
    GameController gameController;
    @Mock
    GameService gameService;
    Authentication authentication;
    @BeforeEach
    void setUp()
    {
        gameController=new GameController(gameService);
        authentication=new UsernamePasswordAuthenticationToken(new User(),null);
    }
    @Test
    void join()
    {
        long id=1L;
        gameController.join(id,authentication);
        Mockito.verify(gameService).join(id,(User)authentication.getPrincipal());
    }
    @Test
    void save()
    {
        Game game=new Game();
        Mockito.when(gameService.save(Mockito.any())).thenReturn(game);
        gameController.save(game,authentication);
        Game saved=gameController.save(game,authentication);
        Assertions.assertEquals(game,saved);
        Assertions.assertEquals(game.getUser(),(User)authentication.getPrincipal());
    }
    @Test
    void findById()
    {
        Game game=new Game();
        long id=1L;
        Mockito.when(gameService.findById(Mockito.any())).thenReturn(game);
        Game found=gameController.findById(id);
        Assertions.assertEquals(game,found);
    }
}