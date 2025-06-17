import java.util.ArrayList;

public abstract class Pieces {
    //instant variables of superclass
    public char color = 'O';
    public int row;//y-top is 0
    public int column;//x-left is 0
    public Pieces(char color, int row, int column){
        this.color = color;
        this.row = row;
        this.column = column;
    }
    public Pieces(Pieces other) {
        this.color = other.color;
        this.row = other.row;
        this.column = other.column;
    }

    public int getDirection(){
        throw new UnsupportedOperationException("This should not have been called, please specify the subclass you want");
    }

    public abstract Pieces getCopy();
    //lots of static methods
    //used for pringint board,us this and then get color to make a piece
    public char getColor(){
        return color;
    }
    
    //used for 7,9 and helps determin last move
    public void setColor(char color){
        this.color = color;
    }
    //for the rook when trying to see if it can castle
    public boolean getCanCastle(){
        throw new UnsupportedOperationException("This should not have been called, please specify the subclass you want");
    }
    
    public char getPiece(){
        return 'O';
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return column;
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setCol(int col){
        column = col;
    }
    public int[] checkMove1(Pieces[][] board){
        //need to check all places between when torpedo
        throw new UnsupportedOperationException("This should not have been called, please specify the subclass you want");
    }
    
    public int[] checkMove2(Pieces[][] board){
        //need to check all places between when torpedo
        throw new UnsupportedOperationException("This should not have been called, please specify the subclass you want");
    }
    
    //for bishop diogonals as well as rooks when implimented
    public int[] checkMove1(Pieces[][] ogboard, int direction){
        //need to check all places between when torpedo
        throw new UnsupportedOperationException("This should not have been called, please specify the subclass you want");
    }
    public void move1(Pieces[][] board, ArrayList<int[]>  moveHistory, int howFar){
        //need to check all places between when torpedo
        throw new UnsupportedOperationException("This should not have been called, please specify the subclass you want");
    }
    public void move2(Pieces[][] board, ArrayList<int[]>  moveHistory){
        //need to check all places between when torpedo
        throw new UnsupportedOperationException("This should not have been called, please specify the subclass you want");
    }
    public void move1(Pieces[][] board, ArrayList<int[]>  moveHistory, int direction, int howFar){
        //need to check all places between when torpedo
        throw new UnsupportedOperationException("This should not have been called, please specify the subclass you want");
    }
}