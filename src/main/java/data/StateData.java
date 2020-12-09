package data;

import lombok.Getter;
import lombok.Setter;
import pojo.Board;
import pojo.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class StateData {
    private int hash;
    private Board board;
    private boolean isWhiteTurn;
    private boolean isEnd;
    private String winner;
    private double stateValue;
    private List<Integer> parentHash;
    private List<Integer> childrenHash;
    private Player whitePlayer;
    private Player blackPlayer;

    public StateData(){
        this.board = new Board();
        this.isWhiteTurn = true;
        this.isEnd = false;
        this.winner = null;
        this.stateValue = 0;
        this.parentHash = new ArrayList<>();
        this.childrenHash = new ArrayList<>();
        this.whitePlayer = new Player(true);
        this.blackPlayer = new Player(false);
        this.hash = hashCode();
    }

    public StateData(Board board, boolean isWhiteTurn, boolean isEnd){
        this.board = board;
        this.isWhiteTurn = isWhiteTurn;
        this.isEnd = isEnd;
        this.winner = null;
        this.stateValue = 0;
        this.parentHash = new ArrayList<>();
        this.childrenHash = new ArrayList<>();
        this.whitePlayer = new Player(true);
        this.blackPlayer = new Player(false);
        this.hash = this.hashCode();
    }

    public StateData(StateData copy){
        this.hash = copy.hash;
        this.board = new Board(copy.board);
        this.isWhiteTurn = copy.isWhiteTurn;
        this.isEnd = copy.isEnd;
        this.stateValue = copy.stateValue;
        this.parentHash = new ArrayList<>();
        this.parentHash.addAll(copy.parentHash);
        this.childrenHash = new ArrayList<>();
        this.childrenHash.addAll(copy.childrenHash);
        this.winner = null;
        this.whitePlayer = new Player(copy.whitePlayer);
        this.blackPlayer = new Player(copy.blackPlayer);
        this.hash = copy.hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateData)) return false;

        StateData stateData = (StateData) o;

        if (isWhiteTurn != stateData.isWhiteTurn) return false;
        if (isEnd != stateData.isEnd) return false;
        if (Double.compare(stateData.stateValue, stateValue) != 0) return false;
        if (!Objects.equals(board, stateData.board)) return false;
        if (!Objects.equals(winner, stateData.winner)) return false;
        if (!Objects.equals(parentHash, stateData.parentHash)) return false;
        if (!Objects.equals(childrenHash, stateData.childrenHash))
            return false;
        if (!Objects.equals(whitePlayer, stateData.whitePlayer))
            return false;
        return Objects.equals(blackPlayer, stateData.blackPlayer);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = board != null ? board.hashCode() : 0;
        result = 31 * result + (isWhiteTurn ? 1 : 0);
        result = 31 * result + (isEnd ? 1 : 0);
        result = 31 * result + (winner != null ? winner.hashCode() : 0);
        temp = Double.doubleToLongBits(stateValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (parentHash != null ? parentHash.hashCode() : 0);
        result = 31 * result + (childrenHash != null ? childrenHash.hashCode() : 0);
        result = 31 * result + (whitePlayer != null ? whitePlayer.hashCode() : 0);
        result = 31 * result + (blackPlayer != null ? blackPlayer.hashCode() : 0);
        return result;
    }

    public void print(){
        this.getBoard().print();
        System.out.println(this.isWhiteTurn);
        System.out.println(this.whitePlayer);
        System.out.println(this.blackPlayer);
    }
}
