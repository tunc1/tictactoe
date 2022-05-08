package app.service;

import app.entity.Game;
import app.entity.User;
import app.repository.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest
{
    GameService gameService;
    @Mock
    GameRepository gameRepository;
    @BeforeEach
    void setUp()
    {
        gameService=new GameService(gameRepository);
    }
    @Test
    void save()
    {
        Game game=new Game();
        Mockito.when(gameRepository.save(Mockito.any())).thenReturn(game);
        Game saved=gameService.save(game);
        Assertions.assertEquals(game,saved);
    }
    @Test
    void findById()
    {
        long id=1L;
        Game game=new Game();
        Mockito.when(gameRepository.findById(Mockito.any())).thenReturn(Optional.of(game));
        Game found=gameService.findById(id);
        Assertions.assertEquals(game,found);
    }
    @Test
    void findById_throwsEntityNotFoundException()
    {
        Mockito.when(gameRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        long id=1L;
        Assertions.assertThrows(EntityNotFoundException.class,()->gameService.findById(id));
    }
    @Test
    void join()
    {
        long id=1L;
        User user=new User();
        Game game=new Game();
        Mockito.when(gameRepository.findById(Mockito.any())).thenReturn(Optional.of(game));
        gameService.join(id,user);
        Assertions.assertEquals(game.getUser2(),user);
        Mockito.verify(gameRepository).save(Mockito.any());
    }
}