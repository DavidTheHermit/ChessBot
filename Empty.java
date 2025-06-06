public class Empty extends Pieces{
    public Empty(int row, int column){
        super("-", row, column); 
    }
    //used for pringint board,us this and then get color to make a piece
    public String getPiece(){
        return "-";
    }
    public static boolean canMove(){
        return false;
    }
    @Override
    public Pieces getCopy() {
        return new Empty(this.row, this.column);
    }
}