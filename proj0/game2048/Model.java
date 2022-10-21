package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author CyberDJ
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        board.setViewingPerspective(side);
//        int dst1=0;
//        for (int r = board.size() - 1; r >= 0; r--) {
//            for (int c = 0; c < board.size(); c++) {
//                Tile t = board.tile(c, r);
//                if(t==null){
//                    System.out.print(0);
//                }else{
//                    System.out.print(board.tile(c,r).value());
//                    if (t.value()==2){
//                        System.out.println("row"+t.row());
//                        System.out.println("r"+r);
//                        dst1=mergeDestination1(c,r,t.value(),side);
//                        board.move(c, 3, t);
//                    }
//                }
//            }
//            System.out.println();
//        }
//
//        System.out.println(dst1);
//
//        System.out.println();
//        for (int r = board.size() - 1; r >= 0; r--) {
//            for (int c = 0; c < board.size(); c++) {
//                Tile t = board.tile(c, r);
//                if(t==null){
//                    System.out.print(0);
//                }else{
//                    System.out.print(board.tile(c,r).value());
//                }
//            }
//            System.out.println();
//        }





        boolean[][] mergedGrid=new boolean[board.size()][board.size()];
        for (int c = 0; c < board.size(); c++) {
            for (int r = board.size()-1; r >=0; r--) {
                Tile t = board.tile(c, r);
                if (t != null) {
                    int dst=mergeDestination1(c,r,t.value(),side);//不能传t,因为t.row和t.col是绝对的，应该直接传r和c
                    System.out.print(dst);
                    if(mergedGrid[c][dst]==false) {
                        boolean merged = board.move(c, dst, t);
                        if (dst!=r){
                            changed = true;
                        }
                        if (merged){
                            mergedGrid[c][dst]=true;
                            score+=t.value()*2;
                        }
                    }else{
                        if(dst-1>=0) {
                            boolean merged = board.move(c, dst - 1, t);
                            if (dst-1!=r){
                                changed = true;
                            }
                            changed = true;
                            if (merged) {
                                mergedGrid[c][dst] = true;
                                score += t.value() * 2;
                            }
                        }
                    }
                }
            }
        }

        checkGameOver();
        if (changed) {
            setChanged();
        }
        board.setViewingPerspective(Side.NORTH);
        return changed;
    }

    // the merge and add value method has already been implemented
    // the next challenge is to control that one time, one merge, do not merge recursively
    private int mergeDestination1(int c,int r,int val,Side side){
        board.setViewingPerspective(side);
        int dest=r;
        r+=1;
        for (;r<board.size();r++){
            if( board.tile(c,r)!=null){
                if (board.tile(c,r).value()==val){
                    return r;// same value, merge and replace
                }else{
                    return r-1;// different value, move to adjacent grid
                }
            }else{
                if (r== board.size()-1){
                    return r;// all empty, move to top
                }
            }
        }
        return dest;
    }
//    // the merge and add value method has already been implemented
//    // the next challenge is to control that one time, one merge, do not merge recursively
//    private int mergeDestination(Tile t,Side side){
//        board.setViewingPerspective(side);
//        int dest=t.row(),c=t.col();
//
//        for (int r=t.row()+1;r<board.size();r++){
//            if( board.tile(c,r)!=null){
//                if (board.tile(c,r).value()==t.value()){
//                    return r;// same value, merge and replace
//                }else{
//                    return r-1;// different value, move to adjacent grid
//                }
//            }else{
//                if (r== board.size()-1){
//                    return r;// all empty, move to top
//                }
//            }
//        }
//        return dest;
//    }


    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for (int i=0;i<b.size();i++){
            for (int j=0;j<b.size();j++){
                if (b.tile(b.size()-1-i,j)==null){return true;}
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for (int i=0;i<b.size();i++){
            for (int j=0;j<b.size();j++){
                if (b.tile(b.size()-1-i,j)!=null && b.tile(b.size()-1-i,j).value()==MAX_PIECE){return true;}
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if (emptySpaceExists(b)) {
            return true;
        }
        int[][] dir = new int[][]{{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                int X = b.size() - 1 - i; // convert array index to coordinate on the board
                int Y = j;
                int newX, newY;
                for (int d = 0; d < dir.length; d++) {
                    newX = X + dir[d][0];
                    newY = Y + dir[d][1];
                    if (newX >= 0 && newX < b.size() && newY >= 0 && newY < b.size()) {
                        if (b.tile(X, Y).value() == b.tile(newX, newY).value()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
