package Tetris;
//
public class BoardToTextConverter
{
//    static StringBuilder builder = new StringBuilder();
//
//    private BoardToTextConverter() {}
//
//    public static String convertToText(Board board) {
//
//	for (int x=0; x<board.getWidth()-1; x++) {
//	    for (int y=0; y<board.getHeight()-1; y++) {
//
//		if (board.getFallingPoly() == null) {
//		    typeToLetter(x, y, board);
//		    continue;
//		}
//
//		//all polys are 2x2, 3x3 or 4x4
//		int fallingPolyLength = board.getFallingPoly().block[0].length;
//		int fallingPolyHeight = board.getFallingPoly().block.length;
//
//		if ((board.getFallingX()<=x && x<=board.getFallingX()+fallingPolyLength) &&
//		    (board.getFallingY()<=y && y<=board.getFallingY()+fallingPolyHeight)) {
//		    if (board.getFallingPoly().block[y][x]==SquareType.EMPTY) {
//			typeToLetter(x, y, board);
//		    }
//		    else builder.append(board.getFallingPoly().block[y][x]);
//
//		}
//
//		else typeToLetter(x, y, board);
//	    }
//	    builder.append("\n");
//	}
//    	String result = builder.toString();
//	builder = new StringBuilder(); //clears the builder
//	return result;
//    }
//    //kan byta ut den här mot en enkel builder.append(board.getFallingPoly().block[y][x]);
//    //behöver då inte ha builder som fält.
//    private static void typeToLetter(int x, int y, Board board) {
//	switch (board.getSquareType(x, y)) {
//	    case I:
//		builder.append("I");
//		break;
//	    case O:
//		builder.append("O");
//		break;
//	    case T:
//		builder.append("T");
//		break;
//	    case S:
//		builder.append("S");
//		break;
//	    case Z:
//		builder.append("Z");
//		break;
//	    case J:
//		builder.append("J");
//		break;
//	    case L:
//		builder.append("L");
//		break;
//	    case EMPTY:
//		builder.append("-");
//		break;
//	}
//    }
}
