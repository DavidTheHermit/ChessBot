public class Queen extends Pieces{
    //instant variables of subclass
    public Queen (String color, int row, int column){
        super(color,row,column);
        //seting instant variables
    }
    //used for pringint board,us this and then get color to make a piece
    public String getPiece(){
        return "Q";
    }
    @Override
    public Pieces getCopy() {
        return new Queen(this.color, this.row, this.column);
    }
    
    @SuppressWarnings("override")
    public int[] checkMove1(Pieces[][] ogboard, int direction){
        Pieces[][] board = Moves.deepCopy(ogboard);
        int maxD;
        int rowD;
        int colD;
        if(direction % 2 == 1){
            maxD = Moves.checkClearDiagonal(board, super.row, super.column, direction, super.color);
            rowD = 1;
            colD = 1;
            
            if(direction == 7 || direction == 1){
                rowD = -1;
            }
            if(direction == 7 || direction == 5){
                colD = -1;
            }
        }else{
            //rook move
            maxD = Moves.checkClearPathCardinal(board, super.row, super.column, direction, super.color);
            rowD = 0;
            colD = 0;

            switch (direction) {
                case 0:
                    rowD = -1;
                    break;
                case 4:
                    rowD = 1;
                    break;
                case 6:
                    colD = -1;
                    break;
                default:
                    colD = 1;
            }
        }
        if (maxD == 0){
            return new int[0];
        }
        int[] arr = new int[maxD];
        //System.out.println(maxD+" direction is "+direction);
        for (int currentDistance = 1; currentDistance<maxD+1; currentDistance++){
            board[super.row+rowD*currentDistance][super.column+colD*currentDistance] = new Queen(super.color, super.row+rowD*currentDistance, super.column+colD*currentDistance);
            board[super.row][super.column] = new Empty(super.row, super.column);
            if(Moves.isKingSafe(board, super.color)){
                    arr[currentDistance-1] = currentDistance;
            }
            //resets the board for next check
            board[super.row+rowD*currentDistance][super.column+colD*currentDistance] = new Empty( super.row+rowD*currentDistance, super.column+colD*currentDistance);
        }
        board[super.row][super.column] = new Queen(super.color, super.row, super.column);
        return Moves.cleanZeros(arr);
    }
    public void move1(Pieces[][] board, int direction, int howFar){
        int rowD = howFar;
        int colD = howFar;
        if (direction % 2 == 1){
            if(direction == 0 || direction == 7 || direction == 1){
                rowD *=-1;
            }
            if(direction < 8 && direction > 4){
                colD *=-1;
            }
        }else{
            switch (direction) {
                case 0:
                    rowD *= -1;
                    colD = 0;
                    break;
                case 2:
                    rowD = 0;
                    break;
                case 4:
                    colD = 0;
                    break;
                default:
                    colD *= -1;
                    rowD = 0;
            }
        }
        //reg code
        board[super.row+rowD][super.column+colD] = new Queen(super.color, super.row+rowD, super.column+colD);
        board[super.row][super.column] = new Empty(super.row, super.column);
        board[1][8].set(board, super.color, super.row+rowD, super.column+colD);
    }
    
}