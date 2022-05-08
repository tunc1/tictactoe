package app.controller;

import app.entity.GameEvent;
import app.entity.User;
import app.service.GameEventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameEventControllerTest
{
    GameEventController gameEventController;
    @Mock
    GameEventService gameEventService;
    Authentication authentication;
    @BeforeEach
    void setUp()
    {
        gameEventController=new GameEventController(gameEventService);
        authentication=new UsernamePasswordAuthenticationToken(new User(),null);
    }
    @Test
    void save()
    {
        GameEvent gameEvent=new GameEvent();
        Mockito.when(gameEventService.save(Mockito.any(),Mockito.any())).thenReturn(gameEvent);
        GameEvent saved=gameEventController.save(gameEvent,authentication);
        Assertions.assertEquals(gameEvent,saved);
    }
    @Test
    void findAllByGameId()
    {
        long id=1L;
        List<GameEvent> gameEvents=List.of(new GameEvent());
        Mockito.when(gameEventService.findAllByGameId(Mockito.anyLong())).thenReturn(gameEvents);
        List<GameEvent> found=gameEventController.findAllByGameId(id);
        Assertions.assertEquals(gameEvents,found);
    }
}