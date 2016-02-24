package Tetris;

public final class TetraminoMaker
{
    public static int getNumberOfTypes() {
	return SquareType.values().length;
    }

    private TetraminoMaker() {
    }

    public static Poly getPoly(int n) {
	SquareType squaretype = SquareType.values()[n];

	switch (squaretype) {
	    case I: return createIPoly();
	    case O: return createOPoly();
	    case T: return createTPoly();
	    case S: return createSPoly();
	    case Z: return createZPoly();
	    case J: return createJPoly();
	    case L: return createLPoly();
	    default: throw new IllegalArgumentException("Not a correct block");
	}
    }

    private static Poly createIPoly() {
	SquareType[][] squares = new SquareType[4][4];
	squares[1][0] = SquareType.I;
	squares[1][1] = SquareType.I;
	squares[1][2] = SquareType.I;
	squares[1][3] = SquareType.I;
	Poly poly = new Poly(squares);
	return poly;
    }

    private static Poly createOPoly() {
	SquareType[][] squares = new SquareType[2][2];
	squares[0][0] = SquareType.O;
	squares[0][1] = SquareType.O;
	squares[1][0] = SquareType.O;
	squares[1][1] = SquareType.O;
	Poly poly = new Poly(squares);
	return poly;
    }

    private static Poly createTPoly() {
	SquareType[][] squares = new SquareType[3][3];
	squares[0][1] = SquareType.T;
	squares[1][0] = SquareType.T;
	squares[1][1] = SquareType.T;
	squares[1][2] = SquareType.T;
	Poly poly = new Poly(squares);
	return poly;
    }

    private static Poly createSPoly() {
	SquareType[][] squares = new SquareType[3][3];
	squares[0][1] = SquareType.S;
	squares[0][2] = SquareType.S;
	squares[1][0] = SquareType.S;
	squares[1][1] = SquareType.S;
	Poly poly = new Poly(squares);
	return poly;
    }

    private static Poly createZPoly() {
	SquareType[][] squares = new SquareType[3][3];
	squares[0][0] = SquareType.Z;
	squares[0][1] = SquareType.Z;
	squares[1][1] = SquareType.Z;
	squares[1][2] = SquareType.Z;
	Poly poly = new Poly(squares);
    	return poly;
    }

    private static Poly createJPoly() {
	SquareType[][] squares = new SquareType[3][3];
	squares[0][0] = SquareType.J;
	squares[1][0] = SquareType.J;
	squares[1][1] = SquareType.J;
	squares[1][2] = SquareType.J;
	Poly poly = new Poly(squares);
        return poly;
    }

    private static Poly createLPoly() {
	SquareType[][] squares = new SquareType[3][3];
	squares[0][2] = SquareType.L;
	squares[1][0] = SquareType.L;
	squares[1][1] = SquareType.L;
	squares[1][2] = SquareType.L;
	Poly poly = new Poly(squares);
	return poly;
    }
}
