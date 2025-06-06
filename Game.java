import java.util.ArrayList;
import java.util.Scanner;
public class Game {
    public Watch watch;
    Pieces[][] board;
    Scanner input = new Scanner(System.in);
    public Game(){
        board = new Pieces[8][9];
        SetUp.regBoard(board);
    }
    public Pieces[][] getBoard(){
        return board;
    }
    public void gameLoop(){
        watch = new Watch();
        // watch.start();
        String move;
        boolean kingsSafe = true;
        int[][] list;
        //ArrayList<Long> microTimes = new ArrayList<Long>();
        int turns = 0;
        int total = 0;
        //checker starts black so white starts
        int[] mod1 = new int[58];
        for(int i = 0; i < 58; i++){
            mod1[i]=(int)(Math.random() * 100);
        }
        int[] mod2 = new int[58];
        for(int i = 0; i < 58; i++){
            mod2[i]=(int)(Math.random() * 100)-50;
        }
        while (kingsSafe){
            turns+=1;
            watch.start(); // I just changed this
            String curColor = "W";
            if(board[1][8].getColor().equals("W")){
                curColor = "B";
            }
            
            //System.out.println("It is " + curColor+"'s turn, please give your moveZ: row col direction distance");
            //System.out.println();
            //System.out.println("here are the possible moves");
            list = Moves.getAllMoves(board, curColor);
            //for (int i = 0; i < list.length; i++){
            //    System.out.println("["+list[i][0]+", "+list[i][1]+", "+list[i][2]+", "+list[i][3]+"]");
            //}
            //int rand = (int)(Math.random() * list.length);
            //System.out.println("["+list[rand][0]+", "+list[rand][1]+", "+list[rand][2]+", "+list[rand][3]+"]");
            System.out.println("# of moves"+list.length);
            //move = list[rand][0]+" "+list[rand][1]+" "+list[rand][2]+" "+list[rand][3];
            //move = input.nextLine();
            // int int direction distance
            //BotTools timesCalled = BotTools();
            int[] botMove;
            if (curColor.equals("B")){
                botMove = BotTools.gradeStateDepth(board, mod1, 2, true);//mod 1 is white
            }else{
                botMove = BotTools.gradeStateDepth(board, mod2, 2, true);
            }
            move = botMove[0]+" "+botMove[1]+" "+botMove[2]+" "+botMove[3];
            System.out.println("evaluation is: "+"["+botMove[0]+" "+botMove[1]+" "+botMove[2]+" "+botMove[3]+"] "+botMove[4]);
            System.out.println("calls: "+BotTools.getCalls());
            //System.out.println(BotTools.evaluation(board, mod));
            String[] moveList = move.split(" ");
            computerTakeTurn(Integer.parseInt(moveList[0]), Integer.parseInt(moveList[1]), Integer.parseInt(moveList[2]), Integer.parseInt(moveList[3]));
            if(curColor.equals("W")){
                kingsSafe = Moves.getMoves(board, "B");
                if ((!kingsSafe && Moves.isKingSafe(board, "B"))||Moves.checkIfOnlyKings(board)){
                    System.out.println("it is a draw");
                    break;
                }
            }else{
                kingsSafe = Moves.getMoves(board, "W");
                if ((!kingsSafe && Moves.isKingSafe(board, "W"))||Moves.checkIfOnlyKings(board)){
                    System.out.println("it is a draw");
                    break;
                }
            }
            if (!kingsSafe||(turns > 6 && board[1][8].getRow() == board[5][8].getRow() && board[1][8].getCol() == board[5][8].getCol())){
                System.out.println(curColor+" has won");
                kingsSafe = false;//this ensures that 3 fold repition stops the game
            }
            watch.stop();
            total+=watch.getElapsedTimeMicros();
            //microTimes.add(watch.getElapsedTimeMicros());
            //System.out.println("one turn watch: "+watch.getElapsedTimeMicros());
            SetUp.printBoard(board);
        }
        System.out.println(total/turns+" ave time in "+turns +" turns");
        if (board[1][8].getColor().equals("B")){
            mod1 = mod2;
        }
        for (int i = 0; i < 58; i++){
            System.out.print(mod1[i] + ", ");
        }
    }
    
    
    //should a=keep asking for proper cords if false is returned
    public boolean takeTurn(int row, int col, int direction, int distance){
        Pieces piece = board[row][col];
        if (piece.getColor().equals("-")){
            return false;
        }
        
        if (piece instanceof Pawn){
            //attack 
            //pawn captures, Distance needs to be -1 or 1 and direction needs to be odd
            if(direction % 2 == 1 && Moves.isIn(((Pawn) board[row][col]).checkMove2(board),distance)){
                ((Pawn) board[row][col]).move2(board, distance);//do i need distance
                return true;
            }
            if (Moves.isIn(((Pawn) board[row][col]).checkMove1(board),distance)){
                ((Pawn) board[row][col]).move1(board, distance);
                return true;
            }
        }
        if (piece instanceof Rook){
            if (direction % 2 == 0 && Moves.isIn(((Rook) board[row][col]).checkMove1(board, direction),distance)){
                ((Rook) board[row][col]).move1(board, direction, distance);
                return true;
            }
        }
        if (piece instanceof Knight){
            if (((Knight) board[row][col]).checkMove1(board, direction).length == 1){
                ((Knight) board[row][col]).move1(board, direction);
                return true;
            }
        }
        if (piece instanceof Bishop){
            if (direction % 2 == 1 && Moves.isIn(((Bishop) board[row][col]).checkMove1(board, direction),distance)){
                ((Bishop) board[row][col]).move1(board, direction, distance);
                return true;
            }
        }
        if (piece instanceof King){
            //distance is always 1
            if (Moves.isIn(((King) board[row][col]).checkMove1(board, direction),distance)){
                ((King) board[row][col]).move1(board, direction, distance);
                return true;
            }
        }
        if (piece instanceof Queen){
            if (Moves.isIn(((Queen) board[row][col]).checkMove1(board, direction),distance)){
                ((Queen) board[row][col]).move1(board, direction, distance);
                return true;
            }
        }
        return false;
    }

    public static boolean computerTakeTurn(Pieces [][] board,int row, int col, int direction, int distance){
        Pieces piece = board[row][col];
        if (piece instanceof Pawn){
            //attack 
            //pawn captures, Distance needs to be -1 or 1 and direction needs to be odd
            if(direction % 2 == 1){
                ((Pawn) board[row][col]).move2(board, distance);//do i need distance
                return true;
            }
            ((Pawn) board[row][col]).move1(board, distance);
            return true;
        }
        if (piece instanceof Rook){
            ((Rook) board[row][col]).move1(board, direction, distance);
            return true;
        }
        if (piece instanceof Knight){
            ((Knight) board[row][col]).move1(board, direction);
            return true;
        }
        if (piece instanceof Bishop){
            ((Bishop) board[row][col]).move1(board, direction, distance);
            return true;
        }
        if (piece instanceof King){
            ((King) board[row][col]).move1(board, direction, distance);
            return true;
        }
        if (piece instanceof Queen){
            ((Queen) board[row][col]).move1(board, direction, distance);
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
                ((Pawn) board[row][col]).move2(board, distance);//do i need distance
                return true;
            }
            ((Pawn) board[row][col]).move1(board, distance);
            return true;
        }
        if (piece instanceof Rook){
            ((Rook) board[row][col]).move1(board, direction, distance);
            return true;
        }
        if (piece instanceof Knight){
            ((Knight) board[row][col]).move1(board, direction);
            return true;
        }
        if (piece instanceof Bishop){
            ((Bishop) board[row][col]).move1(board, direction, distance);
            return true;
        }
        if (piece instanceof King){
            ((King) board[row][col]).move1(board, direction, distance);
            return true;
        }
        if (piece instanceof Queen){
            ((Queen) board[row][col]).move1(board, direction, distance);
            return true;
        }
        return false;
    }
}