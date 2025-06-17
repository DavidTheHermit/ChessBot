import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
public class Game {
    public Watch watch;
    public ArrayList<int[]>  moveHistory;
    Pieces[][] board;
    Scanner input = new Scanner(System.in);
    public Game(){
        moveHistory = new ArrayList<int[]>();
        board = new Pieces[8][9];
        SetUp.regBoard(board);
    }
    public Pieces[][] getBoard(){
        return board;
    }
    public long gameLoop(){
        SetUp.regBoard(board);
        watch = new Watch();
        // watch.start();
        String move;
        boolean kingsSafe = true;
        int[][] list;
        //ArrayList<Long> microTimes = new ArrayList<Long>();
        int turns = 0;
        int total = 0;
        //checker starts black so white starts
        int[] mod1 = {-72, 27, 42, 33, 27, -9, -4, -9, -26, -43, 82, -54, -71, 19, -33, -59, -50, 2, 22, -34, 63, -22, -23, -51, 96, 24, -26, -25, -18, 15, 58, 3, -12, 82, 34, 2, 19, 3, -69, 39, 18, 17, -46, 76, 8, -17, -40, -52, -27, -7, 47, -12, 45, 14, -21, -54, -80, 13};
        int[] mod2 = new int[58];
        for(int i = 0; i < 58; i++){
            mod2[i]=mod1[i]+(int)(Math.random() * 100)-50;
        }
        char curColor = 'B';
        while (kingsSafe){
            turns+=1;
            watch.start(); // I just changed this
            curColor = curColor == 'B' ? 'W' : 'B';
            //System.out.println("It is " + curColor+"'s turn, please give your moveZ: row col direction distance");
            //System.out.println();
            //System.out.println("here are the possible moves");
            list = Moves.getAllMoves(board, moveHistory, curColor);
            if (list[0].length == 0) {
                System.out.println("No legal moves for " + curColor + ". Game over.");
                break; // or return, depending on your game logic
            }
            //for (int i = 0; i < list.length; i++){
            //    System.out.println("["+list[i][0]+", "+list[i][1]+", "+list[i][2]+", "+list[i][3]+"]");
            //}
            int rand = (int)(Math.random() * list.length);
            //System.out.println("["+list[rand][0]+", "+list[rand][1]+", "+list[rand][2]+", "+list[rand][3]+"]");
            //System.out.println("# of moves"+list.length);
            move = list[rand][0]+" "+list[rand][1]+" "+list[rand][2]+" "+list[rand][3];
            //move = input.nextLine();
            // int int direction distance
            //BotTools timesCalled = BotTools();
            if (mod1[0] == 0){//null as testing for king going missing
                int[] botMove;
                int depth = 2;
                if (curColor == 'B'){
                    if (turns > 50){
                        depth = 3;
                        if (turns > 100){
                            depth = 4;
                        }
                    }
                    botMove = BotTools.gradeStateDepth(board, mod1, depth, true);//mod 1 is white
                    //move = botMove[0]+" "+botMove[1]+" "+botMove[2]+" "+botMove[3];
                    //System.out.println("evaluation is: "+"["+botMove[0]+" "+botMove[1]+" "+botMove[2]+" "+botMove[3]+"] "+botMove[4]);
                }else{
                    //white tuah move
                    //move = input.nextLine();
                    if (turns > 50){
                        depth = 3;
                        if (turns > 100){
                            depth = 4;
                        }
                    }
                    botMove = BotTools.gradeStateDepth(board, mod2, depth, true);
                }
                move = botMove[0]+" "+botMove[1]+" "+botMove[2]+" "+botMove[3];
                System.out.println("evaluation is: "+"["+botMove[0]+" "+botMove[1]+" "+botMove[2]+" "+botMove[3]+"] "+botMove[4]);
                System.out.println("calls: "+BotTools.getCalls());
            }
            //System.out.println(BotTools.evaluation(board, mod));
            String[] moveList = move.split(" ");
            computerTakeTurn(Integer.parseInt(moveList[0]), Integer.parseInt(moveList[1]), Integer.parseInt(moveList[2]), Integer.parseInt(moveList[3]));
            if(curColor == 'W'){
                kingsSafe = Moves.getMoves(board, moveHistory, 'B');
                if ((!kingsSafe && Moves.isKingSafe(board, 'B'))||Moves.checkIfOnlyKings(board)){
                    System.out.println("it is a draw");
                    watch.stop();
                    total+=watch.getElapsedTimeMicros();
                    //SetUp.printMoveHistory(moveHistory);
                    break;
                }
            }else{
                kingsSafe = Moves.getMoves(board, moveHistory, 'W');
                if ((!kingsSafe && Moves.isKingSafe(board, 'W'))||Moves.checkIfOnlyKings(board)){
                    System.out.println("it is a draw");
                    watch.stop();
                    total+=watch.getElapsedTimeMicros();
                    //SetUp.printMoveHistory(moveHistory);
                    break;
                }
            }
            //this ensures that 3 fold repition stops the game
            if(turns > 999 && Moves.threeFoldRepition(moveHistory)){
                System.out.println("it is a draw by 3 fold repition");
                watch.stop();
                total+=watch.getElapsedTimeMicros();
                SetUp.printMoveHistory(moveHistory);
                SetUp.printBoard(board);
                break;
            }
            
            if (!kingsSafe){
                System.out.println(curColor+" has won");
            }
            watch.stop();
            total+=watch.getElapsedTimeMicros();
            //microTimes.add(watch.getElapsedTimeMicros());
            //System.out.println("one turn watch: "+watch.getElapsedTimeMicros());
            //SetUp.printBoard(board);

            if ( !(board[0][8] instanceof King)){
                System.out.println("black king is dead ------------");
                SetUp.printBoard(board);
            }
            if ( !(board[7][8] instanceof King)){
                System.out.println("White king is dead ------------");
                SetUp.printBoard(board);
            }
        }
        System.out.println(total/turns+" ave time in "+turns +" turns");
        //for (int i = 0; i < 58; i++){
        //    System.out.print(mod1[i] + ", ");
        //}
        return total/turns;
    }
    
    
    //should a=keep asking for proper cords if false is returned
    public boolean takeTurn(int row, int col, int direction, int distance){
        Pieces piece = board[row][col];
        if (piece.getColor() == 'O'){
            return false;
        }
        
        if (piece instanceof Pawn){
            //attack 
            //pawn captures, Distance needs to be -1 or 1 and direction needs to be odd
            if(direction % 2 == 1 && Moves.isIn(((Pawn) board[row][col]).checkMove2(board),distance)){
                ((Pawn) board[row][col]).move2(board, moveHistory, distance);//do i need distance
                return true;
            }
            if (Moves.isIn(((Pawn) board[row][col]).checkMove1(board),distance)){
                ((Pawn) board[row][col]).move1(board, moveHistory, distance);
                return true;
            }
        }
        if (piece instanceof Rook){
            if (direction % 2 == 0 && Moves.isIn(((Rook) board[row][col]).checkMove1(board, direction),distance)){
                ((Rook) board[row][col]).move1(board, moveHistory, direction, distance);
                return true;
            }
        }
        if (piece instanceof Knight){
            if (((Knight) board[row][col]).checkMove1(board, direction).length == 1){
                ((Knight) board[row][col]).move1(board, moveHistory, direction);
                return true;
            }
        }
        if (piece instanceof Bishop){
            if (direction % 2 == 1 && Moves.isIn(((Bishop) board[row][col]).checkMove1(board, direction),distance)){
                ((Bishop) board[row][col]).move1(board, moveHistory, direction, distance);
                return true;
            }
        }
        if (piece instanceof King){
            //distance is always 1
            if (Moves.isIn(((King) board[row][col]).checkMove1(board, direction),distance)){
                ((King) board[row][col]).move1(board, moveHistory, direction, distance);
                return true;
            }
        }
        if (piece instanceof Queen){
            if (Moves.isIn(((Queen) board[row][col]).checkMove1(board, direction),distance)){
                ((Queen) board[row][col]).move1(board, moveHistory, direction, distance);
                return true;
            }
        }
        return false;
    }

    public static boolean computerTakeTurn(Pieces [][] board, ArrayList<int[]>  moveHistory,int row, int col, int direction, int distance){
        Pieces piece = board[row][col];
        if (piece instanceof Pawn){
            //attack 
            //pawn captures, Distance needs to be -1 or 1 and direction needs to be odd
            if(direction % 2 == 1){
                ((Pawn) board[row][col]).move2(board, moveHistory, distance);//do i need distance
                return true;
            }
            ((Pawn) board[row][col]).move1(board, moveHistory, distance);
            return true;
        }
        if (piece instanceof Rook){
            ((Rook) board[row][col]).move1(board, moveHistory, direction, distance);
            return true;
        }
        if (piece instanceof Knight){
            ((Knight) board[row][col]).move1(board, moveHistory, direction);
            return true;
        }
        if (piece instanceof Bishop){
            ((Bishop) board[row][col]).move1(board, moveHistory, direction, distance);
            return true;
        }
        if (piece instanceof King){
            ((King) board[row][col]).move1(board, moveHistory, direction, distance);
            return true;
        }
        if (piece instanceof Queen){
            ((Queen) board[row][col]).move1(board, moveHistory, direction, distance);
            return true;
        }
        return false;
    }
    public boolean computerTakeTurn(int row, int col, int direction, int distance){
        Pieces piece = board[row][col];
        if (piece instanceof Pawn){
            //attack 
            //pawn captures, Distance needs to be -1 or 1 and direction needs to be odd
            if(direction % 2 == 1){
                ((Pawn) board[row][col]).move2(board, moveHistory, distance);//do i need distance
                return true;
            }
            ((Pawn) board[row][col]).move1(board, moveHistory, distance);
            return true;
        }
        if (piece instanceof Rook){
            ((Rook) board[row][col]).move1(board, moveHistory, direction, distance);
            return true;
        }
        if (piece instanceof Knight){
            ((Knight) board[row][col]).move1(board, moveHistory, direction);
            return true;
        }
        if (piece instanceof Bishop){
            ((Bishop) board[row][col]).move1(board, moveHistory, direction, distance);
            return true;
        }
        if (piece instanceof King){
            ((King) board[row][col]).move1(board, moveHistory, direction, distance);
            return true;
        }
        if (piece instanceof Queen){
            ((Queen) board[row][col]).move1(board, moveHistory, direction, distance);
            return true;
        }
        
        return false;
    }
}