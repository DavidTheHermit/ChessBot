import java.util.ArrayList;

public  class Pawn extends Pieces{
    //instant variables of subclass
    boolean canTorpedo;
    int direction;
    
    public Pawn(char color, int row, int column, boolean torp){
        super(color,row,column);
        direction = -1;
        if(super.color == 'B'){
            direction = 1;
        }
        canTorpedo = torp;
    }
    
    public int getDirection(){
        return direction;
    }
    //hii
        //seting instant variables
        //used for pringint board,us this and then get color to make a piece
    public char getPiece(){
        return 'P';
    }
    
    //used for deep copy func
    @Override
    public Pieces getCopy() {
        return new Pawn(this.color, this.row, this.column, this.canTorpedo);
    }
    
    //should have 4 mvoes, enpasant, 1 reg, torpedo, and reg capture
    //for enpasant, creat a get last move function to help determin if possible
    //enpasant only if just enpasanted and pawn is in same row
    //takes rows and col that it need to go to
    //you do not need to check if check vertacally or pawns
    
    //could hold position of king in a spare part of the board
    
    //retune a int, showing the number of paces a piece can move
    public int[] checkMove1(Pieces[][] board){
        //pawn that move forward always gives a response
        //Pieces[][] board = Moves.deepCopy(ogboard);
        int maxD = Moves.checkPawn(board, super.row, super.column, super.color);
        int[] arr = new int[maxD];
        
        if (maxD>0){
            board[super.row+direction][super.column] = new Pawn(super.color, super.row+direction, super.column, false);
            board[super.row][super.column] = new Empty(super.row, super.column);
            if(Moves.isKingSafe(board, super.color)){
                arr[0] = 1;
            }
            board[super.row+direction][super.column] = new Empty(super.row+direction, super.column);
            //if (super.row+direction == 4 && super.column == 6){
            //    SetUp.printBoard(board);
            //}
            if (canTorpedo&&maxD == 2){
                board[super.row+direction*2][super.column] = new Pawn(super.color, super.row+2*direction, super.column, false);
                if(Moves.isKingSafe(board, super.color)){
                    arr[1] = 2;
                }
                board[super.row+direction*2][super.column] = new Empty(super.row+2*direction, super.column);
            }
            board[super.row][super.column] = new Pawn(super.color, super.row, super.column, canTorpedo);
        }
        return Moves.cleanZeros(arr);
    }
    
    //attacking 
    public int[] checkMove2(Pieces[][] ogboard, ArrayList<int[]>  moveHistory) {
        Pieces[][] board = Moves.deepCopy(ogboard);
        int[] arr = new int[2];
    
        // En passant
        if (!moveHistory.isEmpty() && moveHistory.get(moveHistory.size() - 1)[0] == 1) { // safety check
            int[] lastMove = moveHistory.get(moveHistory.size() - 1);
            boolean isInRow = (super.color == 'W' && super.row == 3) || (super.color == 'B' && super.row == 4);
            if (
                isInRow &&
                (lastMove[3] == super.column - 1 || lastMove[3] == super.column + 1) &&
                lastMove[5] == 2
            ) {
                int colDirection = lastMove[3] == super.column + 1 ? 1 : -1;
                int newRow = super.row + direction;
                int newCol = super.column + colDirection;
                if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                    board[newRow][newCol] = new Pawn(super.color, newRow, newCol, false);
                    board[super.row][super.column] = new Empty(super.row, super.column);
                    
                    //need to make a copy of this piece
                    Pieces temphold = board[super.row][newCol].getCopy();
                    
                    board[super.row][newCol] = new Empty(super.row, newCol);
                    if (Moves.isKingSafe(board, super.color)) {
                        arr[0] = colDirection;
                    }
                    board[newRow][newCol] = new Empty(newRow, newCol);
                    board[super.row][super.column] = new Pawn(super.color, super.row, super.column, canTorpedo);
                    board[super.row][newCol] = temphold;
                }
            }
        }
    
        // Diagonal captures
        for (int colDirection = -1; colDirection <= 1; colDirection += 2) {
            int newRow = super.row + direction;
            int newCol = super.column + colDirection;
    
            if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) continue;
    
            Pieces target = board[newRow][newCol];
            if (target.getColor() != super.color && target.getColor() != 'O') {
                //Pieces[][] testBoard = Moves.deepCopy(ogboard);
                Pieces temphold = board[newRow][newCol].getCopy();
                board[newRow][newCol] = new Pawn(super.color, newRow, newCol, false);
                board[super.row][super.column] = new Empty(super.row, super.column);
                if (Moves.isKingSafe(board, super.color)) {
                    if (arr[0] == 0){
                        arr[0] = colDirection;
                    }
                    else arr[1] = colDirection;
                }
                board[newRow][newCol] = temphold;
                board[super.row][super.column] = new Pawn(super.color, super.row, super.column, canTorpedo);
            }
        }
    
        return Moves.cleanZeros(arr);
    }
    
    public void move1(Pieces[][] board, ArrayList<int[]>  moveHistory, int howFar){
        if ((super.row+(howFar*direction) == 0 && super.color == 'W')|| (super.row+(howFar*direction) == 7 && super.color == 'B')){
            board[super.row+(howFar*direction)][super.column] = new Queen(super.color, super.row+(howFar*direction), super.column);
        }else{
            board[super.row+(howFar*direction)][super.column] = new Pawn(super.color, super.row+(howFar*direction), super.column, false);
        }
        board[super.row][super.column] = new Empty(super.row, super.column);

        int intColor = super.color == 'B' ? -1 : 1;
        moveHistory.add(new int[]{1,intColor, super.row, super.column,howFar, 0, 0});
    }
    
    public void move2(Pieces[][] board, ArrayList<int[]>  moveHistory, int colDirection){
        char capturedPiece = 'x';
        if ((super.row + direction == 0 && super.color == 'W')|| (super.row  + direction == 7 && super.color == 'B')){
            //this is a promotion of a pawn
            capturedPiece = board[super.row+direction][super.column + colDirection].getPiece();
            board[super.row+direction][super.column + colDirection] = new Queen(super.color, super.row+direction, super.column + colDirection);
        }else{
            if (!(board[super.row+direction][super.column + colDirection] instanceof Empty)){//reg capture
                capturedPiece = board[super.row+direction][super.column + colDirection].getPiece();
            }
            board[super.row+direction][super.column + colDirection] = new Pawn(super.color, super.row+direction, super.column + colDirection, false);
        }
        board[super.row][super.column] = new Empty(super.row, super.column);
        //determines if it was enpassant
        if(capturedPiece == 'x'){
            capturedPiece = board[super.row+direction][super.column + colDirection].getPiece();
            board[super.row][super.column + colDirection] = new Empty(super.row, super.column + colDirection);
        }
        //this to to transform color into into int
        int intColor = super.color == 'B' ? -1 : 1;
        int capturedPieceInt = switch (capturedPiece) {
            //case 'O' -> 0; // Empty
            case 'P' -> 1;
            case 'R' -> 2;
            case 'N' -> 3;
            case 'B' -> 4;
            case 'K' -> 5;
            case 'Q' -> 6; // Queen or unknown defaults to 6
            default -> throw new UnsupportedOperationException("Captured piece is not a valid chess piece");
        };
        moveHistory.add(new int[]{1,intColor, super.row, super.column,1, colDirection, capturedPieceInt});
    }
}