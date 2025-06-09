public class SetUp {
    public SetUp() {
        
    }
    public static void regBoard(Pieces[][] board){
        //black pieces
        board[0][0] = new Rook("B", 0,0,true);
        board[0][1] = new Knight("B",0, 1);
        board[0][2] = new Bishop("B",0, 2);
        board[0][3] = new Queen("B",0, 3);
        board[0][4] = new King("B",0, 4,true);
        board[0][5] = new Bishop("B",0, 5);
        board[0][6] = new Knight("B",0, 6);
        board[0][7] = new Rook("B",0, 7,true);
        board[0][8] = new King("B",0, 4,true);
        

        //pawn row
        for (int col = 0; col < 8; col++){
            board[1][col] = new Pawn("B",1, col, true);
        }
        //empty rows(2-5)
        for (int row = 2; row < 6; row++){
            for (int col = 0; col < 8; col++){
                board[row][col] = new Empty(2, col);
            }
        }
        for (int col = 0; col < 8; col++){
            board[6][col] = new Pawn("W",6, col, true);
        }        
        //white pieces
        board[7][0] = new Rook("W", 7,0,true);
        board[7][1] = new Knight("W",7, 1);
        board[7][2] = new Bishop("W",7, 2);
        board[7][3] = new Queen("W",7, 3);
        board[7][4] = new King("W",7, 4,true);
        board[7][5] = new Bishop("W",7, 5);
        board[7][6] = new Knight("W",7, 6);
        board[7][7] = new Rook("W",7, 7,true);
        //phantom peice to find king location
        board[7][8] = new King("W",7, 4,true);
        
        //cords for last move
        board[1][8] = new King("B",0, 0,true);
        board[2][8] = new King("B",0, 0,true);
        board[3][8] = new King("B",0, 0,true);
        for (int i = 2; i<7; i++){
            board[i][8]= new King("B",i, i,true);
        }
    }
    public static void printBoard(Pieces[][] board){
        //System.out.print();
        System.out.println("* 0  1  2  3  4  5  6  7");
        for (int row = 0; row < 8; row++){
            System.out.print(row);
            for (int col = 0; col < 9; col++){
                System.out.print(" "+board[row][col].getColor()+board[row][col].getPiece());
            }
            System.out.println(" "+board[row][8].getRow()+" "+board[row][8].getCol());
        }
    }
}