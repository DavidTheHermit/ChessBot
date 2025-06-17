import java.util.ArrayList;

public class SetUp {
    public SetUp() {
        
    }
    public static void regBoard(Pieces[][] board){
        //black pieces
        board[0][0] = new Rook('B', 0,0,true);
        board[0][1] = new Knight('B',0, 1);
        board[0][2] = new Bishop('B',0, 2);
        board[0][3] = new Queen('B',0, 3);
        board[0][4] = new King('B',0, 4,true);
        board[0][5] = new Bishop('B',0, 5);
        board[0][6] = new Knight('B',0, 6);
        board[0][7] = new Rook('B',0, 7,true);
        board[0][8] = new King('B',0, 4,true);
        

        //pawn row
        for (int col = 0; col < 8; col++){
            board[1][col] = new Pawn('B',1, col, true);
        }
        //empty rows(2-5)
        for (int row = 2; row < 6; row++){
            for (int col = 0; col < 8; col++){
                board[row][col] = new Empty(2, col);
            }
        }
        for (int col = 0; col < 8; col++){
            board[6][col] = new Pawn('W',6, col, true);
        }        
        //white pieces
        board[7][0] = new Rook('W', 7,0,true);
        board[7][1] = new Knight('W',7, 1);
        board[7][2] = new Bishop('W',7, 2);
        board[7][3] = new Queen('W',7, 3);
        board[7][4] = new King('W',7, 4,true);
        board[7][5] = new Bishop('W',7, 5);
        board[7][6] = new Knight('W',7, 6);
        board[7][7] = new Rook('W',7, 7,true);
        //phantom peice to find king location
        board[7][8] = new King('W',7, 4,true);
        
    
    }

    public static void printMoveHistory( ArrayList<int[]>  moveHistory){
        System.out.println("Pc  Clr Row Col Dir Dis");
        for (int i = 0; i < moveHistory.size(); i++){
            int[] move = moveHistory.get(i);
            char piece = switch (move[0]){
                case 1 ->'P';
                case 2 ->'R';
                case 3 -> 'N';
                case 4 -> 'B';
                case 5 -> 'K';
                default -> 'Q';
            };
            char capturedPiece = switch (move[6]){
                case 0 ->'O';
                case 1 ->'P';
                case 2 ->'R';
                case 3 -> 'N';
                case 4 -> 'B';
                case 5 -> 'K';
                default -> 'Q';
            };
            char color = move[1] == 1 ? 'W' : 'B';
            System.out.println(piece + " " + color + " " + move[2] + " " + move[3] + " " + move[4] + " " + move[5] + " " + capturedPiece);
        }
    }

        public static void printBoard(Pieces[][] board) {
        System.out.println("*  0  1  2  3  4  5  6  7");
        for (int row = 0; row < 8; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < 8; col++) {
                Pieces piece = board[row][col];
                if (piece.getColor() == 'O') {
                    System.out.print("-- ");
                } else {
                    String pieceType = piece.getClass().getSimpleName().substring(0, 1); // R for Rook, Q for Queen, etc.
                    System.out.print("" + piece.getColor() + pieceType + " ");
                }
            }
            // Print debug info from helper column 8
            System.out.println("  (" + board[row][8].getRow() + "," + board[row][8].getCol() + ")");
        }
    }
}