import java.util.ArrayList;

public class Knight extends Pieces{
    //instant variables of subclass
    public Knight (char color, int row, int column){
        super(color,row,column);
        //seting instant variables
    }
    //used for pringint board,us this and then get color to make a piece
    public char getPiece(){
        return 'N';
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
        if (moveToRow < 8 && moveToRow > -1 && moveToCol > -1 && moveToCol < 8 && (board[moveToRow][moveToCol].getPiece() == 'O' || board[moveToRow][moveToCol].getColor() != super.color)){
            board[moveToRow][moveToCol] = new Knight(super.color,moveToRow, moveToCol);
            if(Moves.isKingSafe(board, super.color)){
                board[super.row][super.column] = new Knight(super.color,super.row, super.column);
                board[moveToRow][moveToCol] = new Empty(moveToRow, moveToCol);
                return new int[1];
            }
        }
        return new int[0];
    }
    
    public void move1(Pieces[][] board, ArrayList<int[]>  moveHistory, int direction){
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
        char capturedPiece = board[super.row+longRow][super.column+longCol].getPiece();
        board[super.row + longRow][super.column + longCol] = new Knight(super.color, super.row + longRow,super.column + longCol);
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
        moveHistory.add(new int[]{3,intColor, super.row, super.column,direction, 0, capturedPieceInt});
    }
    //NSEW and then {-1, 1}
    
}