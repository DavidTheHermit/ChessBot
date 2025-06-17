import java.util.ArrayList;

public class Bishop extends Pieces{
    //instant variables of subclass
    public Bishop (char color, int row, int column){
        super(color,row,column);
        //seting instant variables
    }
    //used for pringint board,us this and then get color to make a piece
    public char getPiece(){
        return 'B';
    }
    @Override
    public Pieces getCopy() {
        return new Bishop(this.color, this.row, this.column);
    }
    
    public int[] checkMove1(Pieces[][] board, int direction){
        //need to check all places between when torpedo
        //System.out.println();
                //need to check all places between when torpedo
        //Pieces[][] board = Moves.deepCopy(ogboard);
        int maxD = Moves.checkClearDiagonal(board, super.row, super.column, direction, super.color);
        int[] arr = new int[maxD];
        if(maxD == 0){
            return new int[0];
        }
        int rowD = 1;
        int colD = 1;
        switch(direction){
            case 1:
                rowD *= -1;
                break;
            case 5:
                colD *= -1;
                break;
            case 7:
                colD *= -1;
                rowD *= -1;
                break;
        }
        for (int currentDistance = 1; currentDistance<=maxD && super.row+rowD*currentDistance < 8 && super.row+rowD*currentDistance > -1 && super.column+colD*currentDistance > -1 && super.column+colD*currentDistance < 8; currentDistance++){
            //if (super.row == 7 && super.column == 5){
                //System.out.println("test row is " + super.row+rowD*currentDistance +"and column is "+super.column+colD*currentDistance);
            //}
            board[super.row+rowD*currentDistance][super.column+colD*currentDistance] = new Bishop(super.color, super.row+rowD*currentDistance, super.column+colD*currentDistance);
            board[super.row][super.column] = new Empty(super.row, super.column);
            if(Moves.isKingSafe(board, super.color)){
                    arr[currentDistance-1] = currentDistance;
            }
            //resets the board for next check
            board[super.row+rowD*currentDistance][super.column+colD*currentDistance] = new Empty( super.row+rowD*currentDistance, super.column+colD*currentDistance);
        }
        board[super.row][super.column] = new Bishop(super.color, super.row, super.column);
        return Moves.cleanZeros(arr);
    }
    public void move1(Pieces[][] board, ArrayList<int[]>  moveHistory, int direction, int howFar){
        int rowD = howFar;
        int colD = howFar;
        switch(direction){
            case 1:
                rowD *= -1;
                break;
            case 5:
                colD *= -1;
                break;
            case 7:
                colD *= -1;
                rowD *= -1;
                break;
        }
        char capturedPiece = board[super.row+rowD][super.column+colD].getPiece();
        board[super.row+rowD][super.column+colD] = new Bishop(super.color, super.row+rowD, super.column+colD);
        board[super.row][super.column] = new Empty(super.row, super.column);
        int intColor = super.color == 'B' ? -1 : 1;
        int capturedPieceInt = switch (capturedPiece) {
            case 'O' -> 0; // Empty
            case 'P' -> 1;
            case 'R' -> 2;
            case 'N' -> 3;
            case 'B' -> 4;
            case 'K' -> 5;
            case 'Q' -> 6; // Queen or unknown defaults to 6
            default -> throw new UnsupportedOperationException("Captured piece is not a valid chess piece");
        };
        moveHistory.add(new int[]{4,intColor, super.row, super.column,direction, howFar, capturedPieceInt});
    }
}