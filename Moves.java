import java.util.ArrayList;
public class Moves {
    public Moves(){
        System.out.println(" ");
    }
        //deep copy function
    public static Pieces[][] deepCopy(Pieces[][] original) {
        Pieces[][] copy = new Pieces[8][9]; // Create a new 7x8 array
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 9; col++) {
                if (original[row][col] != null) {
                    copy[row][col] = original[row][col].getCopy(); // Polymorphic copy
                } else {
                    copy[row][col] = null;
                }
            }
        }
        return copy;
    }

    
    public static boolean isIn(int[] arr, int obj){
        for (int element: arr){
            if (element == obj){
                return true;
            }
        }
        return false;
    }
    
    public static String printArray(int[] arr) {
        StringBuilder array = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            array.append(arr[i]);
            if (i < arr.length - 1) {
                array.append(", ");
            }
        }
        array.append("]");
        return array.toString();
    }
    
    public static int[] cleanZeros(int[] arr){
        //gets rid of all Zeros
        int zeroCounter = 0;
        for (int checkForZero: arr){
            if (checkForZero == 0){
                zeroCounter++;
            }
        }
        if (zeroCounter > 0){
            int[] nArr = new int[arr.length - zeroCounter];
            int slotIndex = 0;
            for (int checkForNonZero: arr){
                if (checkForNonZero != 0){
                    nArr[slotIndex] = checkForNonZero;
                }
            }
            return nArr;
        }
        return arr;
    }
    
    public static boolean isPassPawn(Pieces[][] board, int startRow, int column, int direction){
        for (int col = column-1; col < column+2; col++){
            if(col > -1 && col < 8){
                for (int row = startRow; row > -1 && row < 8; row+=direction){
                    if (board[row][col].getPiece().equals("P") && board[row][col].getDirection() != direction){
                        return false;
                    }
                
                }
            }
        }
        return true;
    }

    public static boolean isIsolatedPawn(Pieces[][] board, int column, int direction){
        for (int col = column-1; col < column+2; col++){
            if(col > -1 && col < 8){
                for (int row = 0; row < 8; row+=1){
                    if (board[row][col].getPiece().equals("P") && board[row][col].getDirection() == direction){
                        return false;
                    }
                
                }
            }
        }
        return true;
    }

    //checks if a path is clear for the piece to move
    public static int checkClearPathCardinal(Pieces[][] board, int row, int col, int direction, String color){
        //for row
        if (direction == 2){
            for(int distance = 1; col+distance<8; distance++){
                if(board[row][col+distance].getColor().equals("-")){
                    continue;
                }
                if(board[row][col+distance].getColor().equals(color)){
                    return distance - 1;
                }
                return distance;
            }
            return 7-col;
        }
        if (direction == 6){
            for(int distance = 1; col - distance>-1; distance++){
                if(board[row][col-distance].getColor().equals("-")){
                    continue;
                }
                if(board[row][col-distance].getColor().equals(color)){
                    return distance - 1;
                }
                return distance;
            }
            return col;
        }
        if (direction == 0){
            for(int distance = 1; row-distance>-1; distance++){
                if(board[row-distance][col].getColor().equals("-")){
                    continue;
                }
                if(board[row-distance][col].getColor().equals(color)){
                    return distance - 1;
                }
                return distance;
            }
            return row;
        }
        if (direction == 4){
            for(int distance = 1; row+distance<8; distance++){
                if(board[row+distance][col].getColor().equals("-")){
                    continue;
                }
                if(board[row+distance][col].getColor().equals(color)){
                    return distance - 1;
                }
                return distance;
            }
            return 7-row;
        }
        throw new UnsupportedOperationException("Your direction given has not met the format requirments of being N, S, E, or W");
    }
    
    public static boolean checkIfOnlyKings(Pieces[][] board){
        int BN = 0;
        int BB = 0;
        int WR = 0; 
        int BR = 0;
        int WN = 0;
        int WB = 0;
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                String piece = board[row][col].getPiece();
                if (piece.equals("-") || piece.equals("K")){
                    continue;
                }
                if(piece.equals("B")){
                    switch(board[row][col].getColor()){
                        case "W":
                            WB++;
                            if (WB == 2){
                                return false;
                            }
                            WR = (row + col) % 2;
                            break;
                        default:
                            BB++;
                            if (BB == 2){
                                return false;
                            }
                            BR = (row + col) % 2;
                    }
                    continue;
                }
                if(piece.equals("N")){
                    switch(board[row][col].getColor()){
                        case "W":
                            WN++;
                            if (WN == 2){
                                return false;
                            }
                            break;
                        default:
                            BN++;
                            if (BN == 2){
                                return false;
                            }
                    }
                    continue;
                }
                return false;
            }
        }
        if(BB == 1 && WB == 1 && WR != BR){
            return false;
        }
        return true;
    }
    
    //pawn checker
    public static int checkPawn(Pieces[][] board, int row, int col, String color){
    //problem
        if(color.equals("W")){
            for(int distance = 1; distance < 3; distance++){
                int newRow = row - distance;
                if (newRow < 0 || newRow >= 8) return distance - 1;
                if (board[newRow][col].getColor().equals("-")){
                    continue;
                }
                return distance - 1;
            }
            return 2;
        } else {
            for(int distance = 1; distance < 3; distance++){
                int newRow = row + distance;
                if (newRow < 0 || newRow >= 8) return distance - 1;
                if (board[newRow][col].getColor().equals("-")){
                    continue;
                }
                return distance - 1;
            }
            return 2;
        }
    }
    
    public static int checkClearDiagonal(Pieces[][] board, int startRow, int startCol,int direction, String color){
        //one method
        if (direction == 5){
            for (int i = 1; i<8; i++){
                startRow++;
                startCol--;
                if(startRow<8&&startCol>-1){
                    if(board[startRow][startCol].getColor().equals("-") && startRow != 7 && startCol != 0){
                        continue;
                    }
                    if(board[startRow][startCol].getColor().equals(color)){
                        //checks if the end space is move able or capurable
                        return i-1;
                    }else{
                        return i;
                    }
                }else{
                    return i-1;
                }
            }
        }
        if (direction == 1){
            for (int i = 1; i<8; i++){
                startRow--;
                startCol++;
                if(startRow>-1&&startCol<8){
                    if(board[startRow][startCol].getColor().equals("-") && startRow != 0 && startCol != 7){
                        continue;
                    }
                    if(board[startRow][startCol].getColor().equals(color)){
                        //checks if the end space is move able or capurable
                        return i-1;
                    }else{
                        return i;
                    }
                }else{
                    return i-1;
                }
            }
        }
        if (direction == 7){
            for (int i = 1; i<8; i++){
                startRow--;
                startCol--;
                if(startRow>-1&&startCol>-1){
                    if(board[startRow][startCol].getColor().equals("-") && startRow != 0 && startCol != 0){
                        continue;
                    }
                    if(board[startRow][startCol].getColor().equals(color)){
                        //checks if the end space is move able or capurable
                        return i-1;
                    }else{
                        return i;
                    }
                }else{
                    return i-1;
                }
            }
        }
        if (direction == 3){
            for (int i = 1; i<8; i++){
                startRow++;
                startCol++;
                if(startRow<8&&startCol<8){
                    if(board[startRow][startCol].getColor().equals("-") && startRow != 7 && startCol != 7){
                        continue;
                    }
                    if(board[startRow][startCol].getColor().equals(color)){
                        //checks if the end space is move able or capurable
                        return i-1;
                    }else{
                        return i;
                    }
                }else{
                    return i-1;
                }
            }
        }
        throw new UnsupportedOperationException("Direction was not formated correctly as the direction is" + direction);
    }
    //do the check caridna; but speficy for black and the ncheck if rook or queen on carina; and diagonal is bishop and queen
    //this gets all possible moves and then gives a list of cordinates, 0
    
    //king is not detecting 2 1 as a possible move
    
    public static boolean getMoves(Pieces[][] board, String color){
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                if (board[row][col] == null||!board[row][col].getColor().equals(color)){
                    continue;
                }
                //allall
                if (board[row][col] instanceof Pawn){
                    //System.out.println(Moves.printArray(((Pawn) board[row][col]).checkMove1(board)));
                    if (((Pawn) board[row][col]).checkMove1(board).length > 0){  
                        return true;
                    }
                    if(((Pawn) board[row][col]).checkMove2(board).length > 0){
                        return true;
                    }
                    continue;
                }
                if (board[row][col] instanceof Rook){
                    for(int i = 0; i < 7; i+=2){
                        if(((Rook) board[row][col]).checkMove1(board, i).length > 0){
                            return true;
                        }
                    }
                    continue;
                }
                if (board[row][col] instanceof Knight){
                    for(int i = 0; i < 8; i++){
                        if (((Knight) board[row][col]).checkMove1(board, i).length == 1){
                            return true;
                        }
                    }
                    continue;
                }
                if (board[row][col] instanceof Bishop){
                    for (int direction = 1; direction < 8; direction+=2){
                        if(((Bishop) board[row][col]).checkMove1(board, direction).length > 0){
                            return true;
                        }
                    }
                    continue;
                }
                if (board[row][col] instanceof King){
                    for (int direction = 0; direction < 8; direction++){
                        if(((King) board[row][col]).checkMove1(board, direction).length > 0){
                            return true;
                        }
                    }
                    continue;
                }
                
                if (board[row][col] instanceof Queen){
                    for (int direction = 0; direction < 8; direction++){
                        if(((Queen) board[row][col]).checkMove1(board, direction).length > 0){
                            return true;
                        }
                        
                    }
                    continue;
                }
            }
        }
        return false;
    }
    
    public static int[][] getAllMoves(Pieces[][] board, String color){
        //outputs arays of arrays that show what user input would be needed to do a move
        ArrayList<Integer> moves = new ArrayList<Integer>();
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                //for(int i = 0; i < moves.size(); i++){
                //    System.out.print(moves[i]+" ");
                //}
                //System.out.println("");
                if (!board[row][col].getColor().equals(color)){
                    continue;
                }
                //allall
                if (board[row][col] instanceof Pawn){
                    //System.out.println(Moves.printArray(((Pawn) board[row][col]).checkMove1(board)));
                    for (int distance : ((Pawn) board[row][col]).checkMove1(board)){  
                        moves.add(row);
                        moves.add(col);
                        moves.add(0);
                        moves.add(distance);
                    }
                    for(int distance : ((Pawn) board[row][col]).checkMove2(board)){
                        moves.add(row);
                        moves.add(col);
                        moves.add(1);
                        moves.add(distance);
                    }
                    continue;
                }
                if (board[row][col] instanceof Rook){
                    for(int i = 0; i < 7; i+=2){
                        for(int distance : ((Rook) board[row][col]).checkMove1(board, i)){
                            moves.add(row);
                            moves.add(col);
                            moves.add(i);
                            moves.add(distance);
                        }
                    }
                    continue;
                }
                if (board[row][col] instanceof Knight){
                    for(int i = 0; i < 8; i++){
                        if (((Knight) board[row][col]).checkMove1(board, i).length == 1){
                            moves.add(row);
                            moves.add(col);
                            moves.add(i);
                            moves.add(0);
                        }
                    }
                    continue;
                }
                if (board[row][col] instanceof Bishop){
                    for (int direction = 1; direction < 8; direction+=2){
                        for(int distance : ((Bishop) board[row][col]).checkMove1(board, direction)){
                            moves.add(row);
                            moves.add(col);
                            moves.add(direction);
                            moves.add(distance);
                        }
                    }
                    continue;
                }
                if (board[row][col] instanceof King){
                    for (int direction = 0; direction < 8; direction++){
                        for(int distance : ((King) board[row][col]).checkMove1(board, direction)){
                            moves.add(row);
                            moves.add(col);
                            moves.add(direction);
                            moves.add(distance);
                        }
                    }
                    continue;
                }
                
                if (board[row][col] instanceof Queen){
                    for (int direction = 0; direction < 8; direction++){
                        for(int distance : ((Queen) board[row][col]).checkMove1(board, direction)){
                            moves.add(row);
                            moves.add(col);
                            moves.add(direction);
                            moves.add(distance);
                        }
                        
                    }
                }
            }
        }
        int[][] returnArray = new int[moves.size() / 4][4];
        for (int i = 0; i < moves.size() / 4; i++) {
            returnArray[i][0] = moves.get(i * 4);
            returnArray[i][1] = moves.get(i * 4 + 1);
            returnArray[i][2] = moves.get(i * 4 + 2);
            returnArray[i][3] = moves.get(i * 4 + 3);
        }
        return returnArray;
    }
    
    public static boolean isKingSafe(Pieces[][] board,String color){
        
        //needs to check if king is near
        
        //for row
        //goes left first
        //this calls and finds which king location we should use
        int row = board[7][8].getRow();
        int col = board[7][8].getCol();
        if (color.equals("B")){
            row = board[0][8].getRow();
            col = board[0][8].getCol();
        }
        //System.out.println(row+" "+col);
        for (int i = col-1; i>-1; i--){
            if (board[row][i].getColor().equals("-")){
                continue;
            }
            if(board[row][i].getColor().equals(color)){
                break;
            }
            if( board[row][i] instanceof Queen||board[row][i] instanceof Rook||(board[row][i] instanceof King && i == col-1)){
                return false;
            }
        }
        for (int i = col+1; i<8; i++){
            if (board[row][i].getColor().equals("-")){
                continue;
            }
            if(board[row][i].getColor().equals(color)){
                break;
            }
            if(board[row][i] instanceof Queen||board[row][i] instanceof Rook||(board[row][i] instanceof King && i == col+1)){
                return false;
            }
        }
        
        //for a colum
        //up
        for (int i = row-1; i>-1; i--){
            if (board[i][col].getColor().equals("-")){
                continue;
            }
            if(board[i][col].getColor().equals(color)){
                break;
            }
            if(board[i][col] instanceof Queen||board[i][col] instanceof Rook||(board[i][col] instanceof King && i == row-1)){
                
                return false;
            }
        }
        for (int i = row+1; i<8; i++){
            //String colour = board[row][i].getColor();
            if (board[i][col].getColor().equals("-")){
                continue;
            }
            if(board[i][col].getColor().equals(color)){
                break;
            }
            if(board[i][col] instanceof Queen||board[i][col] instanceof Rook||(board[i][col] instanceof King && i == row+1)){
                
                return false;
            }
        }
        
        //both north checks need to check for pawns when the king is white
        
        //north east first
        int testR = row-1;
        int testC = col+1;
        //needs to check for opisite color in direction of pawn
        while(testR > -1&& testC < 8){
            if (board[testR][testC].getColor().equals("-")){
                testR--;
                testC++;
                continue;
            }
            if(board[testR][testC].getColor().equals(color)){
                break;
            }
            if(board[testR][testC] instanceof Queen||board[testR][testC] instanceof Bishop||(board[row-1][col+1] instanceof Pawn && board[row-1][col+1].getColor().equals("B")) ||(board[testR][testC] instanceof King && testR == row-1 &&  testC == col+1)){
                
                return false;
            }
            testR--;
            testC++;
        }
        
        //north West first
        testR = row-1;
        testC = col-1;
        //needs to check for opisite color in direction of pawn
        while(testR > -1&& testC > -1){
            //checks if space
            if (board[testR][testC].getColor().equals("-")){
                //add -- and ++
                testR--;
                testC--;
                continue;
            }
            //checls if it is a impermiable thing
            if(board[testR][testC].getColor().equals(color)){
                break;
            }
            
            if(board[testR][testC] instanceof Queen||board[testR][testC] instanceof Bishop||(board[row-1][col-1] instanceof Pawn && board[row-1][col-1].getColor().equals("B"))||(board[testR][testC] instanceof King && testR == row-1 &&  testC == col-1)){
                return false;
            }
            testR--;
            testC--;
        }
        
        //South West first
        testR = row+1;
        testC = col-1;
        //needs to check for opisite color in direction of pawn
        while(testR < 8 && testC > -1){
            //checks if space
            if (board[testR][testC].getColor().equals("-")){
                testR++;
                testC--;
                continue;
            }
            //checls if it is a impermiable thing
            if(board[testR][testC].getColor().equals(color)){
                break;
            }
            if(board[testR][testC] instanceof Queen||board[testR][testC] instanceof Bishop||(board[row+1][col-1] instanceof Pawn && board[row+1][col-1].getColor().equals("W"))||(board[testR][testC] instanceof King && testR == row+1 &&  testC == col-1)){
                return false;
            }
            testR++;
            testC--;
        }
        //South East first
        testR = row+1;
        testC = col+1;
        while(testR < 8 && testC < 8){
            //checks if space
            if (board[testR][testC].getColor().equals("-")){
                testR++;
                testC++;
                continue;
            }
            //checls if it is a impermiable thing
            if(board[testR][testC].getColor().equals(color)){
                break;
            }
            if(board[testR][testC] instanceof Queen||board[testR][testC] instanceof Bishop||(board[row+1][col+1] instanceof Pawn && board[row+1][col+1].getColor().equals("W"))||(board[testR][testC] instanceof King && testR == row+1 &&  testC == col+1)){
                return false;
            }
            testR++;
            testC++;
        }
        
        //checks for knights
        //south
        if (col < 7 && row < 6 && !board[row + 2][col + 1].getColor().equals(color) && board[row + 2][col + 1] instanceof Knight){
            return false;
        }
        if (col > 0 && row < 6 && !board[row + 2][col - 1].getColor().equals(color) && board[row + 2][col - 1] instanceof Knight){
            return false;
        }
        //north
        if (col > 0 && row > 1 && !board[row - 2][col - 1].getColor().equals(color) && board[row - 2][col - 1] instanceof Knight){
            return false;
        }
        if (col < 7 && row > 1 && !board[row - 2][col + 1].getColor().equals(color) && board[row - 2][col + 1] instanceof Knight){
            return false;
        }
        
        //east
        if (col > 1 && row < 7 && !board[row + 1][col - 2].getColor().equals(color) && board[row + 1][col - 2] instanceof Knight){
            return false;
        }
        if (col > 1 && row > 0 && !board[row - 1][col - 2].getColor().equals(color) && board[row - 1][col - 2] instanceof Knight){
            return false;
        }
        
        //West
        if (col < 6 && row < 7 && !board[row + 1][col + 2].getColor().equals(color) && board[row + 1][col + 2] instanceof Knight){
            return false;
        }
        if (col < 6 && row > 0 && !board[row - 1][col + 2].getColor().equals(color) && board[row - 1][col + 2] instanceof Knight){
            return false;
        }
        
        return true;
    }
    
}