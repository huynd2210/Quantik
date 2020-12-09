package data;

import enumeration.Shape;
import pojo.Piece;

public class PieceList {
    public static final Piece whiteCylinder = new Piece(Shape.CYLINDER, true, 'C');
    public static final Piece blackCylinder = new Piece(Shape.CYLINDER, false, 'c');
    public static final Piece whiteSphere = new Piece(Shape.SPHERE, true, 'S');
    public static final Piece blackSphere = new Piece(Shape.SPHERE, false, 's');
    public static final Piece whitePyramid = new Piece(Shape.PYRAMID, true, 'P');
    public static final Piece blackPyramid = new Piece(Shape.PYRAMID, false, 'p');
    public static final Piece whiteSquare = new Piece(Shape.SQUARE, true, 'B');
    public static final Piece blackSquare = new Piece(Shape.SQUARE, true, 'b');
}
