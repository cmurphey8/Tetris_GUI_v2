/**************************************************************************************************
 *  Compilation:  javac Tetra.java
 *  Execution:    java Tetra
 *  Dependencies: StdDraw.java Shape.java Tetroid*.java
 * 
 *  Usage:  Move blocks with keys: a (left), a (right), s (down), w (rotate), space (slam down)
 * 
 *  GOAL: Complete the SidePane Class -> add a scoreboard!
 * 
 *  Discussion: 
 * 
 *  (1) What is the significance of the TetraSet class elements being declared protected and static?
 * 
 *  (2) What do we gain from having our Shape and Display classes extend TetraSet? 
 * 
 *  (3) Making only this one change, could we set the global tetroid object in Tetra to type TetraSet? 
 *          -> Why or why not? 
 *
 *  (4) Making only this one change, could we set the global templates array in Display to type TetraSet? 
 *          -> Why or why not?
 * 
 *  (5) Suppose we were determined to change Shape from abstract to interface, 
 *      but we did not want to add any more methods to our Tetroids. How could we accomplish this?
 * 
 *  EXTRA PRACTICE: Find a suitable end-game procedure to break out of the while(true) loop below!
 * 
 **************************************************************************************************/

public class Tetra { 
    // final global constants
    public final static int DELAY = 10;
    public final static int gridX = 10;
    public final static int gridY = 20;

    // global objects
    public static Shape tetroid;
    public static TetraSet blob = new TetraSet(gridX, gridY);
    // public static Display background = new Display();

    public static View frameView;

    // global block select
    public static int nextBlock;
    public static Shape nextTetroid;

    public static long score;

    public static void main(String[] args) {   
        boolean inPlay = false;
        nextBlock = -1;
        select(nextBlock);

        frameView = new View(nextTetroid);
        long time0 = System.currentTimeMillis();
        while (!blob.gameOver()) {
            long timeDelta = System.currentTimeMillis() - time0;
            // select a new block if last block no longer in play
            if (!inPlay) {
                inPlay = select(nextBlock);
            }

            // process key entries if object is in play
            if (frameView.hasNextKeyTyped() && inPlay) {
                switch (frameView.nextKeyTyped()) {
                    case ' ':    // space bar >> slam down
                        tetroid.slam();
                        break;
                    case 'a':    // a >> move left
                        tetroid.move(-1);
                        break;
                    case 'w':    // w >> rotate
                        tetroid.rotateSuper();
                        break;
                    case 'd':    // d >> move right
                        tetroid.move(1);
                        break;
                    case 's':    // s >> speed down
                        tetroid.drop();
                        break;
                }
            }

            // if block IN play: drop on-time; remove from play if floored
            if (inPlay) {
                // reload frame with Tetroid updates
                frameView.nextFrame(nextTetroid, tetroid, score);
                if (timeDelta > 500) {
                    if (!tetroid.drop()) {
                        inPlay = unselect();
                    }
                    time0 = System.currentTimeMillis();
                }
                try {
                    Thread.sleep(DELAY);
                }
                catch (InterruptedException e) {
                    System.out.println("Error sleeping");
                }
            }
        } // end of while

        // game over sequence - delay
        try {
            Thread.sleep(DELAY * 100);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }

        // game over sequence - call for initials
        if (frameView.gameOver(nextTetroid, score)) {
            String initials = "";
            while (initials.length() < 3) {
                if (frameView.hasNextKeyTyped()) {
                    initials += Character.toUpperCase(frameView.nextKeyTyped());
                    frameView.gameInitials(nextTetroid, score, initials);
                }
            }
            frameView.addHighScore(score, initials);
        }
    }
                
    // remove block from play if floored
    public static boolean unselect() {
        int lines = blob.update(tetroid);
        if (lines > 0) score += 40 * Math.pow(3, lines);
        tetroid = null;
        return false;
    }

    // reinit block if no block in play
    public static boolean select(int k) {
        // init a new block of type k, centered at the top of the grid
        if (k >= 0) {
            initNew(k, gridX / 2, gridY - 2);
        }
        initNext(nextBlock(), 2, 1);
        return true;
    }

    // select the next block to play (per NES sequence)
    public static int nextBlock() {
        // select a random block [0,7)
        int nextRandom = uniform(7);
        
        // if random block is the same as the last, try again
        if (nextBlock == nextRandom) {
            nextBlock = uniform(7);

            // regardless of outcome, go with it
            return nextBlock;
        }

        // else go with it
        nextBlock = nextRandom;
        return nextBlock;
    }

    // uniform random integer in [0, n) 
    public static int uniform(int n) {
        return (int) (Math.random() * n);
    } 

    public static void initNext(int k, double x, double y) {
        // add a new block to the tetroids array by the template type identified
        switch (k) {
            case 0: 
                nextTetroid = new TetroidI(x, y);
                break;
            case 1: 
                x--;
                nextTetroid = new TetroidJ(x, y);
                break;
            case 2: 
                x++;
                nextTetroid = new TetroidL(x, y);
                break;
            case 3: 
                nextTetroid = new TetroidO(x, y);
                break;
            case 4: 
                y++;
                nextTetroid = new TetroidS(x, y);
                break;
            case 5: 
                nextTetroid = new TetroidT(x, y);
                break;
            case 6:
                y++;
                nextTetroid = new TetroidZ(x, y);
                break;
        }
    }

    public static void initNew(int k, double x, double y) {
        // add a new block to the tetroids array by the template type identified
        switch (k) {
            case 0: 
                tetroid = new TetroidI(x, y);
                break;
            case 1: 
                tetroid = new TetroidJ(x, y);
                break;
            case 2: 
                tetroid = new TetroidL(x, y);
                break;
            case 3: 
                tetroid = new TetroidO(x, y);
                break;
            case 4: 
                tetroid = new TetroidS(x, y);
                break;
            case 5: 
                tetroid = new TetroidT(x, y);
                break;
            case 6:
                tetroid = new TetroidZ(x, y);
                break;
        }
    }
} 