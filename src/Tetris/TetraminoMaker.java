package Tetris;

public class TetraminoMaker
{
    SquareType[][] squares;

    public TetraminoMaker(final SquareType[][] squares) {
	this.squares = squares;
    }

    public int getNumberOfTypes() {
	return SquareType.values().length;
    }

    public Poly getPoly(int n) {
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

    private Poly createIPoly() {
	squares = new SquareType[4][4];
	squares[1][0] = SquareType.I;
	squares[1][1] = SquareType.I;
	squares[1][2] = SquareType.I;
	squares[1][3] = SquareType.I;
	Poly poly = new Poly(squares);
	return poly;
    }

    private Poly createOPoly() {
	squares = new SquareType[2][2];
	squares[0][0] = SquareType.O;
	squares[0][1] = SquareType.O;
	squares[1][0] = SquareType.O;
	squares[1][1] = SquareType.O;
	Poly poly = new Poly(squares);
	return poly;
    }

    private Poly createTPoly() {
	squares = new SquareType[3][3];
	squares[0][1] = SquareType.T;
	squares[1][0] = SquareType.T;
	squares[1][1] = SquareType.T;
	squares[1][2] = SquareType.T;
	Poly poly = new Poly(squares);
	return poly;
    }

    private Poly createSPoly() {
	squares = new SquareType[3][3];
	squares[0][1] = SquareType.S;
	squares[0][2] = SquareType.S;
	squares[1][0] = SquareType.S;
	squares[1][1] = SquareType.S;
	Poly poly = new Poly(squares);
	return poly;
    }

    private Poly createZPoly() {
	squares = new SquareType[3][3];
	squares[0][0] = SquareType.Z;
	squares[0][1] = SquareType.Z;
	squares[1][1] = SquareType.Z;
	squares[1][2] = SquareType.Z;
	Poly poly = new Poly(squares);
    	return poly;
    }

    private Poly createJPoly() {
	squares = new SquareType[3][3];
	squares[0][0] = SquareType.J;
	squares[1][0] = SquareType.J;
	squares[1][1] = SquareType.J;
	squares[1][2] = SquareType.J;
	Poly poly = new Poly(squares);
        return poly;
    }

    private Poly createLPoly() {
	squares = new SquareType[3][3];
	squares[0][2] = SquareType.L;
	squares[1][0] = SquareType.L;
	squares[1][1] = SquareType.L;
	squares[1][2] = SquareType.L;
	Poly poly = new Poly(squares);
	return poly;
    }
}
