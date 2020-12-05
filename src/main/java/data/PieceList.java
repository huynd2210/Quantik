package data;

import enumeration.Shape;
import pojo.Piece;

public class PieceList {
    public static final Piece whiteCylinder = new Piece(Shape.CYLINDER, true);
    public static final Piece blackCylinder = new Piece(Shape.CYLINDER, false);
    public static final Piece whiteSphere = new Piece(Shape.SPHERE, true);
    public static final Piece blackSphere = new Piece(Shape.SPHERE, false);
    public static final Piece whitePyramid = new Piece(Shape.PYRAMID, true);
    public static final Piece blackPyramid = new Piece(Shape.PYRAMID, false);
    public static final Piece whiteSquare = new Piece(Shape.CYLINDER, true);
    public static final Piece blackSquare = new Piece(Shape.CYLINDER, true);
}
