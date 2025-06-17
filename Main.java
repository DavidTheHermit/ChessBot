public class Main
{
    public static void main(String[] args)
    {
        Game game = new Game();
        long totalTime = 0;
        for (int i = 0; i < 10001; i++){
            totalTime += game.gameLoop();
        }
        System.out.println(totalTime/10000 + " is the ave over 2000 games" );
    //goals
    //fix king being missplaced
    //between getAllMoves and when the computer takes a turn, the turn has become invalid
    // it is not computer take turn as when using the og take turn, it works
    //does not occur when row becomes negitive
    //when on right of board, the yphase into the phantom row
    //happend to directions 3, 5, and rarely 7

    //turn color into a char
    //develop history to replace [1-6][8] phantom peices
    }
}