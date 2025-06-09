public class Bishop extends Pieces{
    //instant variables of subclass
    public Bishop (String color, int row, int column){
        super(color,row,column);
        //seting instant variables
    }
    //used for pringint board,us this and then get color to make a piece
    public String getPiece(){
        return "B";
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
            case 5:
                colD *= -1;
            case 7:
                colD *= -1;
                rowD *= -1;
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
    public void move1(Pieces[][] board, int direction, int howFar){
        int rowD = howFar;
        int colD = howFar;
        switch(direction){
            case 1:
                rowD *= -1;
            case 5:
                colD *= -1;
            case 7:
                colD *= -1;
                rowD *= -1;
        }
        board[super.row+rowD][super.column+colD] = new Bishop(super.color, super.row+rowD, super.column+colD);
        board[super.row][super.column] = new Empty(super.row, super.column);
        board[1][8] = new Bishop(super.color, super.row+rowD, super.column+colD);
    }
}