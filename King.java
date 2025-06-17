import java.util.ArrayList;

public class King extends Pieces{
    //instant variables of subclass
    public boolean canCastle;
    public King (char color, int row, int column, boolean canCastle){
        super(color,row,column);
        this.canCastle = canCastle;
        //seting instant variables
    }
    //used for pringint board,us this and then get color to make a piece
    public char getPiece(){
        return 'K';
    }
    @Override
    public Pieces getCopy() {
        return new King(this.color, this.row, this.column, this.canCastle);
    }
    public int[] checkMove1(Pieces[][] board, int direction){
        //need to check all places between when torpedo
        //System.out.println();
        //need to check all places between when torpedo
        
        //Pieces[][] board = Moves.deepCopy(ogboard);
        int[] arr = new int[2];
        //check if king is in check at start
        boolean startCheck = Moves.isKingSafe(board, super.color);
        //holds the row of the king
        int locKing = 7;
        if(super.color == 'B'){
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
        if(moveToRow > 7 || moveToRow < 0 || moveToCol < 0 || moveToCol > 7||board[moveToRow][moveToCol].getColor() == super.color){
            return new int[0];
        }
        Pieces tempPiece = board[moveToRow][moveToCol].getCopy();
        board[moveToRow][moveToCol] = new King(super.color, moveToRow, moveToCol,canCastle);
        //sets new location of king
        board[locKing][8].setRow(moveToRow);
        board[locKing][8].setCol(moveToCol);
        board[super.row][super.column] = new Empty(super.row, super.column);
        if(Moves.isKingSafe(board, super.color)){
            arr[0] = 1;
            board[moveToRow][moveToCol] = tempPiece;
            board[super.row][super.column] = new King(super.color,super.row, super.column, canCastle);
            board[locKing][8].setRow(super.row);
            board[locKing][8].setCol(super.column);
        }else {
            board[moveToRow][moveToCol] = tempPiece;
            board[super.row][super.column] = new King(super.color,super.row, super.column, canCastle);
            board[locKing][8].setRow(super.row);
            board[locKing][8].setCol(super.column);
            return new int[0];
        }
        //since it can already move one, it knows it wont be in check in between
        moveToCol += colD;
        if(startCheck && direction == 6  && board[locKing][3] instanceof Empty && board[locKing][1] instanceof Empty  && board[locKing][2] instanceof Empty && canCastle && board[super.row][super.column - 4] instanceof Rook && ((Rook)board[super.row][super.column - 4]).getCanCastle()){
            board[super.row][moveToCol] = new King(super.color, super.row, moveToCol,true);
            board[locKing][8].setCol(moveToCol);
            board[super.row][super.column] = new Empty(super.row, super.column);
            if(Moves.isKingSafe(board, super.color)){
                arr[1] = 2;
            }
            board[super.row][moveToCol] = new Empty(super.row, moveToCol);
            board[super.row][super.column] = new King(super.color, super.row, super.column,canCastle);
        }else{
            //need to check if is in check before castling
            if(startCheck && direction == 2  && board[locKing][5] instanceof Empty  && board[locKing][6] instanceof Empty && canCastle && board[super.row][super.column + 3] instanceof Rook && ((Rook)board[super.row][super.column + 3]).getCanCastle()){
                board[super.row][moveToCol] = new King(super.color, super.row, moveToCol,canCastle);
                board[locKing][8].setCol( moveToCol);
                board[super.row][super.column] = new Empty(super.row, super.column);
                if(Moves.isKingSafe(board, super.color)){
                    arr[1] = 2;
                }
                board[super.row][moveToCol] = new Empty(super.row, moveToCol);
                board[super.row][super.column] = new King(super.color, super.row, super.column,canCastle);
            }
        }
        board[locKing][8].setRow(super.row);
        board[locKing][8].setCol(super.column);
        return Moves.cleanZeros(arr);
    }
    public void move1(Pieces[][] board, ArrayList<int[]>  moveHistory, int direction, int howFar){
        int rowD = 1;
        int colD = howFar;
        //diagonal
        switch (direction){
            case 0:
                rowD = -1;
                colD = 0;
                break;
            case 1:
                rowD = -1;
                break;
            case 2:
                rowD = 0;
                break;
            case 3:
                colD = -1;
                break;
            case 4:
                colD = 0;
                break;
            case 5:
                rowD = 1;
                colD *= -1;
                break;
            case 6:
                rowD = 0;
                colD *= -1;
                break;
            case 7: 
                rowD = -1;
                colD *= -1;
                break;
            default:
                throw new AssertionError();
        }
        int moveToRow = super.row+rowD;
        int moveToCol = super.column+colD;

        //reg code
        int locKing = 7;
        if(super.color == 'B'){
            locKing = 0;
        }
        if(moveToRow > 7 || moveToRow < 0 || moveToCol < 0 || moveToCol > 7||board[moveToRow][moveToCol].getColor() == super.color){
            System.out.println("helpppp " + moveToRow + " " + moveToCol);
            SetUp.printMoveHistory(moveHistory);
        }
        char capturedPiece = board[moveToRow][moveToCol].getPiece();
        board[moveToRow][moveToCol] = new King(super.color, moveToRow, moveToCol,false);;
        //sets new location of king
        board[locKing][8].setRow(moveToRow);
        board[locKing][8].setCol(moveToCol);
        board[super.row][super.column] = new Empty(super.row, super.column);
        //east
        if (direction == 2 && howFar == 2){
            board[locKing][7] = new Empty(locKing, 7);
            board[locKing][5] = new Rook(super.color,locKing, 5, false);
        //west
        }else if(direction == 6 && howFar == 2){
            board[locKing][0] = new Empty(locKing, 0);
            board[locKing][3] = new Rook(super.color,locKing, 3, false);
        }
        canCastle = false;
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
        moveHistory.add(new int[]{5,intColor, super.row, super.column,direction, howFar, capturedPieceInt});
    }
}