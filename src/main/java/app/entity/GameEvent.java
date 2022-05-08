package app.entity;

import javax.persistence.*;

@Entity
public class GameEvent
{
    @Id
    @SequenceGenerator(name="GameEvent_SEQUENCE_GENERATOR",sequenceName="GameEvent_SEQUENCE",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="GameEvent_SEQUENCE_GENERATOR")
    private Long id;
    private char symbol;
    @ManyToOne
    private Game game;
    private int x,y;
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id=id;
    }
    public char getSymbol()
    {
        return symbol;
    }
    public void setSymbol(char symbol)
    {
        this.symbol=symbol;
    }
    public Game getGame()
    {
        return game;
    }
    public void setGame(Game game)
    {
        this.game=game;
    }
    public int getX()
    {
        return x;
    }
    public void setX(int x)
    {
        this.x=x;
    }
    public int getY()
    {
        return y;
    }
    public void setY(int y)
    {
        this.y=y;
    }
}