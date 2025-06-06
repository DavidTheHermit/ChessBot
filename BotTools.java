public class BotTools {
    //returns a move plus the evalve?
    public static int timesCalled = 0;
    public BotTools(){
        timesCalled = 0; 
    }
    
    public static int getCalls(){
        return timesCalled;
    }
    
    public static int[] gradeStateDepth(Pieces[][] board, int[] mod, int depth, boolean start){
        //if (start){
        //    public Watch watch = new Watch();
        //    watch.start();
        //}
        boolean maximize;
        if(depth == 0){
            return new int[] { BotTools.evaluation(board, mod) };
        }else{
            String color = "B";
            maximize = false;
            if (board[1][8].getColor().equals("B")){
                color = "W";
               maximize = true;
            }
            int[][] branches = Moves.getAllMoves(board, color);
            int[]evalves = new int[branches.length];
            for (int i = 0; i < branches.length; i++){
                Pieces[][] newBoard = Moves.deepCopy(board);
                Game.computerTakeTurn(newBoard,branches[i][0],branches[i][1],branches[i][2],branches[i][3]);
                evalves[i] = gradeStateDepth(newBoard, mod, depth - 1, false)[0];
            }
            int bestMoveIndex = 0;
            for (int i = 1; i < branches.length; i++){
                if((maximize&&evalves[i] >= evalves[bestMoveIndex]) || (!maximize&&evalves[i] < evalves[bestMoveIndex])){
                    bestMoveIndex = i;
                }
            }
            int[] returningObj = new int[5];
            for(int i = 0; i < 4; i++){
                returningObj[i] = branches[bestMoveIndex][i];
            }
            returningObj[4] = evalves[bestMoveIndex];
            return returningObj;
        }
        
        //if (start){
        //    watch.stop();
        //    System.out.println("evaluation time: "+watch.getElapsedTimeMillis());
        //}
    }
    public static int evaluation(Pieces[][] board, int[] mod){
        
        //Watch watch = new Watch();
        //watch.start();
        int carrots = 0;
        //determine number of moves 0-3 are for possible moves
        carrots+=Moves.getAllMoves(board, "W").length*mod[0]+mod[1];
        carrots-=Moves.getAllMoves(board, "B").length*mod[2]+mod[3];
        
        
        //3 fold repition is not being correctly identifies
        
        boolean kingsSafe;
        if(board[1][8].getColor().equals("B")){
            kingsSafe = Moves.getMoves(board, "B");
            if ((board[1][8].getRow() == board[5][8].getRow() && board[1][8].getCol() == board[5][8].getCol())||(!kingsSafe && Moves.isKingSafe(board, "B"))||Moves.checkIfOnlyKings(board)){
                return -999999;
            }
        }else{
            kingsSafe = Moves.getMoves(board, "W");
            if ((board[1][8].getRow() == board[5][8].getRow() && board[1][8].getCol() == board[5][8].getCol())||(!kingsSafe && Moves.isKingSafe(board, "W"))||Moves.checkIfOnlyKings(board)){
                return 999999;
            }
        }
        if (!kingsSafe){
            if(board[1][8].getColor().equals("W")){
                return -999999;
            }
            return 999999;
        }


        //check for control over middle to outside
        //4-5 pawn 6-7 rook 8-9 knight 10-11 bishop 12-13king 14-15queen
        //4-15 is for outside
        //16-27 is for 1st ring
        //28-39 2nd
        //40-51 3rd- central
        //plus 12 for each ring
        //52 and 53 are for doubled pawns
        //54 and 55 are for pass pawns
        //56 and 57 are for Lonely pawns
        for (int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if (!board[row][col].getPiece().equals("-")){
                    int addOrSubtractCarrots = -1;
                    int ring = -1;
                    Pieces piece = board[row][col];
                    if (piece.getColor().equals("W")){
                        addOrSubtractCarrots = 1;
                    }
                    //gives us the position for the ring index when using the mod
                    int ringMod = 0;
                    while(ring == -1){
                        if (row == 0+ringMod || row == 7-ringMod || col == 0+ringMod|| col == 7-ringMod){
                            ring = 12 * ringMod;
                            break;
                        }
                        ringMod++;
                    }
                    //this will detect what kinda peice it is 
                    String pieceName = piece.getPiece();
                    
                    switch(pieceName){
                        case "P":
                            carrots+=addOrSubtractCarrots*(mod[4 + ring] * 1 + mod[5 + ring]);
                            int direction = piece.getDirection();
                            if (board[row+direction][col].getPiece().equals(pieceName) && board[row+direction][col].getColor().equals(piece.getColor())){
                                carrots+=addOrSubtractCarrots*(mod[52] * 1 + mod[53]);
                            }
                            if (Moves.isPassPawn(board, row, col, direction)){
                                carrots+=addOrSubtractCarrots*(mod[54] * 1 + mod[55]);
                                //System.out.println(row+" "+col+" is a pass pawn");
                            }
                            if (Moves.isIsolatedPawn(board, col, direction)){
                                carrots+=addOrSubtractCarrots*(mod[56] * 1 + mod[57]);
                                //System.out.println(row+" "+col+" is a loney pawn");
                            }
                            break;
                        case "R":
                            carrots+=addOrSubtractCarrots*(mod[6 + ring] * 5 + mod[7 + ring]);
                            break;
                        case "N":
                            carrots+=addOrSubtractCarrots*(mod[8 + ring] * 3 + mod[9 + ring]);
                            break;
                        case "B":
                            carrots+=addOrSubtractCarrots*(mod[10 + ring] * 3 + mod[11 + ring]);
                            break;
                        case "K":
                            carrots+=addOrSubtractCarrots*(mod[12 + ring] * 1 + mod[13 + ring]);
                            break;
                        default:
                            carrots+=addOrSubtractCarrots*(mod[14 + ring] * 9 + mod[15 + ring]);
                    }
                }
            }
        }
        //watch.stop();
        //System.out.println("evaluation time: "+watch.getElapsedTimeMillis());
        timesCalled ++;
        return carrots;
    }
}