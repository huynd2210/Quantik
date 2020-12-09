package pojo;

import data.PieceList;
import enumeration.Shape;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Player {
    private List<Piece> hand;
    private boolean isWhite;

    public Player(List<Piece> hand, boolean isWhite){
        this.hand = hand;
        this.isWhite = isWhite;
    }

    public Player(Player player){
        this.hand = new ArrayList<>();
        for (Piece p : player.hand){
            this.hand.add(new Piece(p));
        }
        this.isWhite = player.isWhite;
    }


    public Player(boolean isWhite){
        this.isWhite = isWhite;
        initPlayer(isWhite);
    }

    public void initPlayer(boolean isWhite){
        this.hand = new ArrayList<>();
        if (isWhite){
            this.hand.add(PieceList.whiteSphere);
            this.hand.add(PieceList.whiteSphere);
            this.hand.add(PieceList.whiteSquare);
            this.hand.add(PieceList.whiteSquare);
            this.hand.add(PieceList.whiteCylinder);
            this.hand.add(PieceList.whiteCylinder);
            this.hand.add(PieceList.whitePyramid);
            this.hand.add(PieceList.whitePyramid);
        }else{
            this.hand.add(PieceList.blackSphere);
            this.hand.add(PieceList.blackSphere);
            this.hand.add(PieceList.blackSquare);
            this.hand.add(PieceList.blackSquare);
            this.hand.add(PieceList.blackCylinder);
            this.hand.add(PieceList.blackCylinder);
            this.hand.add(PieceList.blackPyramid);
            this.hand.add(PieceList.blackPyramid);
        }
    }

}
