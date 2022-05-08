package app.service;

import app.entity.Game;
import app.entity.GameEvent;
import app.entity.User;
import app.repository.GameEventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameEventServiceTest
{
    GameEventService gameEventService;
    @Mock
    GameEventRepository gameEventRepository;
    @Mock
    GameService gameService;
    @BeforeEach
    void setUp()
    {
        gameEventService=new GameEventService(gameEventRepository,gameService);
    }
    @Test
    void save_O()
    {
        User user=new User();
        user.setId(1L);
        User user2=new User();
        user2.setId(2L);
        Game game=new Game();
        game.setUser(user);
        game.setId(1L);
        GameEvent gameEvent=new GameEvent();
        gameEvent.setGame(game);
        Mockito.when(gameService.findById(Mockito.anyLong())).thenReturn(game);
        Mockito.when(gameEventRepository.save(Mockito.any())).thenReturn(gameEvent);
        GameEvent saved=gameEventService.save(gameEvent,user2);
        Assertions.assertEquals('O',saved.getSymbol());
    }
    @Test
    void save_X()
    {
        User user=new User();
        user.setId(1L);
        User user2=new User();
        user2.setId(1L);
        Game game=new Game();
        game.setUser(user);
        game.setId(1L);
        GameEvent gameEvent=new GameEvent();
        gameEvent.setGame(game);
        Mockito.when(gameService.findById(Mockito.anyLong())).thenReturn(game);
        Mockito.when(gameEventRepository.save(Mockito.any())).thenReturn(gameEvent);
        GameEvent saved=gameEventService.save(gameEvent,user2);
        Assertions.assertEquals('X',saved.getSymbol());
    }
    @Test
    void findAllByGameId()
    {
        List<GameEvent> gameEvents=List.of(new GameEvent());
        Mockito.when(gameEventRepository.findAllByGameId(Mockito.anyLong())).thenReturn(gameEvents);
        long gameId=1L;
        List<GameEvent> found=gameEventService.findAllByGameId(gameId);
        Assertions.assertEquals(gameEvents,found);
    }
}