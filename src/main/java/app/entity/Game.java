package app.entity;

import javax.persistence.*;

@Entity
public class Game
{
    @Id
    @SequenceGenerator(name="Game_SEQUENCE_GENERATOR",sequenceName="Game_SEQUENCE",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Game_SEQUENCE_GENERATOR")
    private Long id;
    @ManyToOne
    private User user,user2;
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id=id;
    }
    public User getUser()
    {
        return user;
    }
    public void setUser(User user)
    {
        this.user=user;
    }
    public User getUser2()
    {
        return user2;
    }
    public void setUser2(User user2)
    {
        this.user2=user2;
    }
}