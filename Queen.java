import java.util.ArrayList;

public class Queen extends Pieces{
    //instant variables of subclass
    public Queen (char color, int row, int column){
        super(color,row,column);
        //seting instant variables
    }
    //used for pringint board,us this and then get color to make a piece
    public char getPiece(){
        return 'Q';
    }
    @Override
    public Pieces getCopy() {
        return new Queen(this.color, this.row, this.column);
    }
    
    @SuppressWarnings("override")
    public int[] checkMove1(Pieces[][] ogboard, int direction){
        Pieces[][] board = Moves.deepCopy(ogboard);
        int maxD;
        if(direction % 2 == 1){
            maxD = Moves.checkClearDiagonal(board, super.row, super.column, direction, super.color);
        }else{
            maxD = Moves.checkClearPathCardinal(board, super.row, super.column, direction, super.color);
        } 
        if(maxD == 0){
            return new int[0];
        }
        int rowD = switch(direction){
            case 0 -> -1;
            case 1 -> -1;
            case 2 -> 0;
            case 4 -> 1;
            case 6 -> 0;
            case 7 -> -1;
            default -> 1;
            };
        int colD = switch(direction){
            case 0 -> 0;
            case 2 -> 1;
            case 4 -> 0;
            case 5 -> -1;
            case 6 -> -1;
            case 7 -> -1;
            default -> 1;
        };
        int[] arr = new int[maxD];
        board[super.row][super.column] = new Empty(super.row, super.column);
        for (int currentDistance = 1; currentDistance<maxD; currentDistance++){
            board[super.row+rowD*currentDistance][super.column+colD*currentDistance] = new Queen(super.color, super.row+rowD*currentDistance, super.column+colD*currentDistance);
            if(Moves.isKingSafe(board, super.color)){
                arr[currentDistance-1] = currentDistance;
            }
            //resets the board for next check
            board[super.row+rowD*currentDistance][super.column+colD*currentDistance] = new Empty( super.row+rowD*currentDistance, super.column+colD*currentDistance);
        }
        //this is for the last distance and is so that a pices that would be normally erased isnt
        Pieces copy = board[super.row+rowD*maxD][super.column+colD*maxD].getCopy();
        board[super.row+rowD*maxD][super.column+colD*maxD] = new Queen(super.color, super.row+rowD*maxD, super.column+colD*maxD);
        if(Moves.isKingSafe(board, super.color)){
            arr[maxD-1] = maxD;
        }
        board[super.row+rowD*maxD][super.column+colD*maxD] = copy;

        board[super.row][super.column] = new Queen(super.color, super.row, super.column);
        return Moves.cleanZeros(arr);
    }
    public void move1(Pieces[][] board, ArrayList<int[]>  moveHistory, int direction, int howFar){
        int rowD = switch (direction) {
            case 0 -> -howFar;
            case 1 -> -howFar;
            case 4 -> howFar;
            case 5 -> howFar;
            case 7 -> -howFar;
            default -> 0;
        };
        int colD = switch (direction) {
            case 2 -> howFar;
            case 1 -> howFar;
            case 5 -> -howFar;
            case 6 -> -howFar;
            case 7 -> -howFar;
            default -> 0;
        };
        //reg code
        char capturedPiece = board[super.row+rowD][super.column+colD].getPiece();
        board[super.row+rowD][super.column+colD] = new Queen(super.color, super.row+rowD, super.column+colD);
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
        moveHistory.add(new int[]{6,intColor, super.row, super.column,direction, howFar, capturedPieceInt});
    }
    
}