public class Empty extends Pieces{
    public Empty(int row, int column){
        super('O', row, column); 
    }
    //used for pringint board,us this and then get color to make a piece
    public char getPiece(){
        return 'O';
    }
    public static boolean canMove(){
        return false;
    }
    @Override
    public Pieces getCopy() {
        return new Empty(this.row, this.column);
    }
}