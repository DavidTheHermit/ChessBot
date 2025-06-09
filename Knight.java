public class Knight extends Pieces{
    //instant variables of subclass
    public Knight (String color, int row, int column){
        super(color,row,column);
        //seting instant variables
    }
    //used for pringint board,us this and then get color to make a piece
    public String getPiece(){
        return "N";
    }
    @Override
    public Pieces getCopy() {
        return new Knight(this.color, this.row, this.column);
    }
    
    public int[] checkMove1(Pieces[][] ogboard, int direction){
        //need to check all places between when torpedo
        Pieces[][] board = Moves.deepCopy(ogboard);
        int longRow;
        int longCol;
        if (direction == 0){
            if (super.row < 2){
                return new int[0];
            }
            longRow = -2;
            longCol = 1;
        }else if (direction == 7){
            if (super.row < 2){
                return new int[0];
            }
            longRow = -2;
            longCol = -1;
        }else if (direction == 3){
            if (super.row > 5){
                return new int[0];
            }
            longRow = 2;
            longCol = 1;
        }else if (direction == 4){
            if (super.row > 5){
                return new int[0];
            }
            longRow = 2;
            longCol = -1;
        } else if (direction == 6){
            if (super.column < 2){
                return new int[0];
            }
            longCol = -2;
            longRow = -1;
        } else if (direction == 5){
            if (super.column < 2){
                return new int[0];
            }
            longCol = -2;
            longRow = 1;
        }else if (direction == 1){
            if (super.column > 5){
                return new int[0];
            }
            longCol = 2;
            longRow = -1;
        }else if (direction == 2){
            if (super.column > 5){
                return new int[0];
            }
            longCol = 2;
            longRow = 1;
        }else{
            throw new UnsupportedOperationException("Your direction given has not met the format requirments of being N, S, E, or W");
        }
        //check if spot is valid
        board[super.row][super.column] = new Empty(super.row, super.column);
        int moveToRow;
        int moveToCol;
        Pieces newKnight;
        moveToRow = super.row + longRow;
        moveToCol = super.column + longCol;
        if (moveToRow < 8 && moveToRow > -1 && moveToCol > -1 && moveToCol < 8 && (board[moveToRow][moveToCol].getPiece().equals("-") || !board[moveToRow][moveToCol].getColor().equals(super.color))){
            board[moveToRow][moveToCol] = new Knight(super.color,moveToRow, moveToCol);
            if(Moves.isKingSafe(board, super.color)){
                board[super.row][super.column] = new Knight(super.color,super.row, super.column);
                board[moveToRow][moveToCol] = new Empty(moveToRow, moveToCol);
                return new int[1];
            }
        }
        return new int[0];
    }
    
    public void move1(Pieces[][] board, int direction){
        //need to check all places between when torpedo
        int longRow;
        int longCol;
        switch (direction) {
            case 0:
                longRow = -2;
                longCol = 1;
                break;
            case 1:
                longCol = 2;
                longRow = -1;
                break;
            case 2:
                longCol = 2;
                longRow = 1;
                break;
            case 3:
                longRow = 2;
                longCol = 1;
                break;
            case 4:
                longRow = 2;
                longCol = -1;
                break;
            case 5:
                longCol = -2;
                longRow = 1;
                break;
            case 6:
                longCol = -2;
                longRow = -1;
                break;
            case 7:
                longRow = -2;
                longCol = -1;
                break;
            default:
                throw new UnsupportedOperationException("Your direction given has not met the format requirments of being N, S, E, or W");
        }
        //check if spot is valid
        board[super.row][super.column] = new Empty(super.row, super.column);
        board[super.row + longRow][super.column + longCol] = new Knight(super.color, super.row + longRow,super.column + longCol);
        board[1][8] = new Knight(super.color, super.row + longRow, super.column + longCol);    }
    
    //NSEW and then {-1, 1}
    
}