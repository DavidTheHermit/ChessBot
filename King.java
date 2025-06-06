public class King extends Pieces{
    //instant variables of subclass
    public boolean canCastle;
    public King (String color, int row, int column, boolean canCastle){
        super(color,row,column);
        this.canCastle = canCastle;
        //seting instant variables
    }
    //used for pringint board,us this and then get color to make a piece
    public String getPiece(){
        return "K";
    }
    @Override
    public Pieces getCopy() {
        return new King(this.color, this.row, this.column, this.canCastle);
    }
    public int[] checkMove1(Pieces[][] ogboard, int direction){
        //need to check all places between when torpedo
        //System.out.println();
        //need to check all places between when torpedo
        
        Pieces[][] board = Moves.deepCopy(ogboard);
        int[] arr = new int[2];
        //holds the row of the king
        int locKing = 7;
        if(super.color.startsWith("B")){
            locKing = 0;
        }
        int rowD = 0;
        int colD = 0;
        
        if(direction == 0 || direction == 7 || direction == 1){
            rowD = -1;
        }else if(direction < 6 && direction > 2){
            rowD = 1;
        }
        //west
        if(direction < 8 && direction > 4){
                colD = -1;
        } else if(direction < 4 && direction > 0){
                colD = 1;
        }
        int moveToRow = super.row+rowD;
        int moveToCol = super.column+colD;
        if(moveToRow > 7 || moveToRow < 0 || moveToCol < 0 || moveToCol > 7||board[moveToRow][moveToCol].getColor().equals(super.color)){
            return new int[0];
        }
        board[moveToRow][moveToCol] = new King(super.color, moveToRow, moveToCol,canCastle);
        //sets new location of king
        board[locKing][8].setRow(moveToRow);
        board[locKing][8].setCol(moveToCol);
        board[super.row][super.column] = new Empty(super.row, super.column);
        if(Moves.isKingSafe(board, super.color)){
            arr[0] = 1;
        }else {
            board[moveToRow][moveToCol] = new Empty(moveToRow, moveToCol);
            board[super.row][super.column] = new King(super.color,super.row, super.column, canCastle);
            return new int[0];
        }
        //since it can already move one, it knows it wont be in check in between
        if(direction == 6  && canCastle && board[super.row][super.column - 4] instanceof Rook && ((Rook)board[super.row][super.column - 4]).getCanCastle()&& Moves.isKingSafe(ogboard, super.color)){
            board[super.row][moveToCol] = new King(super.color, super.row, moveToCol,canCastle);
            board[locKing][8].setCol(moveToCol);
            board[super.row][super.column] = new Empty(super.row, super.column);
            if(Moves.isKingSafe(board, super.color)){
                arr[1] = 2;
            }
            board[super.row][moveToCol] = new Empty(super.row, moveToCol);
            board[locKing][8].setCol(super.column);
            board[super.row][super.column] = new King(super.color, super.row, super.column,canCastle);
        }else{
            if(direction == 2  && canCastle && board[super.row][super.column + 3] instanceof Rook && ((Rook)board[super.row][super.column + 3]).getCanCastle()&& Moves.isKingSafe(ogboard, super.color)){
                board[super.row][moveToCol] = new King(super.color, super.row, moveToCol,canCastle);
                board[locKing][8].setCol( moveToCol);
                board[super.row][super.column] = new Empty(super.row, super.column);
                if(Moves.isKingSafe(board, super.color)){
                    arr[1] = 2;
                }
                board[super.row][moveToCol] = new Empty(super.row, moveToCol);
                board[locKing][8].setCol(super.column);
                board[super.row][super.column] = new King(super.color, super.row, super.column,canCastle);
            }
        }
        return Moves.cleanZeros(arr);
    }
    public void move1(Pieces[][] board, int direction, int howFar){
        int rowD = 1;
        int colD = howFar;
        //diagonal
        if (direction % 2 == 1){
            if(direction == 0 || direction == 7 || direction == 1){
                rowD *=-1;
            }
            if(direction < 8 && direction > 4){
                colD *=-1;
            }
        }else{
            if(direction == 0){
                rowD = -1;
                colD = 0;
            }else if(direction == 4){
                colD = 0;
            }else if(direction == 6){
                colD *= -1;
                rowD = 0;
            }else if(direction == 2){
                rowD = 0;
            }
        }
        //reg code
        int locKing = 7;
        if(super.color.startsWith("B")){
            locKing = 0;
        }
        board[super.row+rowD][super.column+colD] = new King(super.color, super.row+rowD, super.column+colD,false);;
        //sets new location of king
        board[locKing][8].setRow(super.row+rowD);
        board[locKing][8].setCol(super.column+colD);
        board[super.row][super.column] = new Empty(super.row, super.column);
        //east
        if (colD == 2){
            board[locKing][8] = new Empty(locKing, 7);
            board[locKing][5] = new Rook(super.color,locKing, 5, false);
        //west
        }else if(colD == -2){
            board[locKing][0] = new Empty(locKing, 0);
            board[locKing][3] = new Rook(super.color,locKing, 3, false);
        }
        canCastle = false;
        board[1][8].set(board,super.color, super.row+rowD, super.column+colD);
        
    }
}