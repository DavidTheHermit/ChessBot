import java.util.ArrayList;

public class Rook extends Pieces{
    //instant variables of subclass
    public boolean canCastle;
    public Rook (char color, int row, int column, boolean can){
        super(color,row,column);
        canCastle = can;
        //seting instant variables
    }
    //used for pringint board,us this and then get color to make a piece
    public char getPiece(){
        return 'R';
    }
    @Override
    public Pieces getCopy() {
        return new Rook(this.color, this.row, this.column, this.canCastle);
    }
    public int[] checkMove1(Pieces[][] board, int direction){
        //need to check all places between when torpedo
        int maxD = Moves.checkClearPathCardinal(board, super.row, super.column, direction, super.color);
        int[] arr = new int[maxD];
        if(maxD == 0){
            return new int[0];
        }
        int rowD = switch (direction) {
            case 0 -> -1;
            case 4 -> 1;
            default -> 0;
        };
        int colD = switch (direction) {
            case 2 -> 1;
            case 6 -> -1;
            default -> 0;
        };
        board[super.row][super.column] = new Empty(super.row, super.column);
        for (int currentDistance = 1; currentDistance<maxD; currentDistance++){
            board[super.row+rowD*currentDistance][super.column+colD*currentDistance] = new Rook(super.color, super.row+rowD*currentDistance, super.column+colD*currentDistance,canCastle);
            if(Moves.isKingSafe(board, super.color)){
                arr[currentDistance-1] = currentDistance;
            }
            //resets the board for next check
            board[super.row+rowD*currentDistance][super.column+colD*currentDistance] = new Empty( super.row+rowD*currentDistance, super.column+colD*currentDistance);
        }
        //this is for the last distance and is so that a pices that would be normally erased isnt
        Pieces copy = board[super.row+rowD*maxD][super.column+colD*maxD].getCopy();
        board[super.row+rowD*maxD][super.column+colD*maxD] = new Rook(super.color, super.row+rowD*maxD, super.column+colD*maxD,canCastle);
        if(Moves.isKingSafe(board, super.color)){
            arr[maxD-1] = maxD;
        }
        board[super.row+rowD*maxD][super.column+colD*maxD] = copy;
        //resets the board for next check
        board[super.row][super.column] = new Rook(super.color, super.row, super.column,canCastle);
        return Moves.cleanZeros(arr);
    }
    public boolean getCanCastle(){
        return canCastle;
    }
    public void move1(Pieces[][] board, ArrayList<int[]>  moveHistory, int direction, int howFar){
        int rowD = switch (direction) {
            case 0 -> -howFar;
            case 4 -> howFar;
            default -> 0;
        };
        int colD = switch (direction) {
            case 2 -> howFar;
            case 6 -> -howFar;
            default -> 0;
        };
        char capturedPiece = board[super.row+rowD][super.column+colD].getPiece();
        board[super.row+rowD][super.column+colD]  = new Rook(super.color, super.row+rowD, super.column+colD, false);
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
        moveHistory.add(new int[]{2,intColor, super.row, super.column,direction, howFar, capturedPieceInt});
    }
}