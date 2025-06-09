public class Rook extends Pieces{
    //instant variables of subclass
    public boolean canCastle;
    public Rook (String color, int row, int column, boolean can){
        super(color,row,column);
        canCastle = can;
        //seting instant variables
    }
    //used for pringint board,us this and then get color to make a piece
    public String getPiece(){
        return "R";
    }
    @Override
    public Pieces getCopy() {
        return new Rook(this.color, this.row, this.column, this.canCastle);
    }
    public int[] checkMove1(Pieces[][] board, int direction){
        //need to check all places between when torpedo
        //System.out.println();
                //need to check all places between when torpedo
        //Pieces[][] board = Moves.deepCopy(ogboard);
        int maxD = Moves.checkClearPathCardinal(board, super.row, super.column, direction, super.color);
        int[] arr = new int[maxD];
        if(maxD == 0){
            return new int[0];
        }
        int rowD = 0;
        int colD = 0;
        switch (direction) {
            case 0:
                rowD = -1;
                break;
             case 2:
                colD = 1;
                break;
             case 4:
                rowD = 1;
                break;
             case 6:
                colD = -1;
                break;
            default:
                throw new UnsupportedOperationException("Your direction given has not met the format requirments of being N, S, E, or W");    
        }
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
    public void move1(Pieces[][] board, int direction, int howFar){
        int rowD = 0;
        int colD = 0;
        switch (direction) {
            case 0:
                rowD = -1;
                break;
            case 2:
                colD = 1;
                break;
            case 4:
                rowD = 1;
                break;
            default:
                colD = -1;
        }
        rowD*=howFar;
        colD*=howFar;
        board[super.row+rowD][super.column+colD]  = new Rook(super.color, super.row+rowD, super.column+colD, false);
        board[super.row][super.column] = new Empty(super.row, super.column);
        canCastle = false;
        board[1][8] = new Rook(super.color, super.row+rowD, super.column+colD, false);
    }
}