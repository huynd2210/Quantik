package pojo;

public class Game {
    public Player white;
    public Player black;
    public Board board;


    public Game(){
        initGame();
    }

    public void initGame(){
        this.board = new Board();
        this.white = new Player(true);
        this.black = new Player(true);
    }

}
