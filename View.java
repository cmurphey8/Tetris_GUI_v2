/**************************************************************************************************
 *
 *  Controller Class for this program >> replaces StdDraw -> Nothing to do here! 
 *  ACCESSED BY: Tetra.java
 *  
 **************************************************************************************************/
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.LinkedList;
import java.util.TreeSet;
import java.util.NoSuchElementException;
import java.io.*;
import java.util.Scanner; // Import the Scanner class to read text files

public class View extends TetraSet { 
    // game-play variables
    JFrame frame = new JFrame();
    Color[][] frameC;
    Color[] nextC;

    // game-over variables
    boolean highScore;
    int newIndex;
    String[] savedScores;

    // global sizing
    final Dimension dim = new Dimension(475, 600);
    KeyListener test = new Listener();

    // key-listener variables
    private static Object keyLock = new Object();

    // queue of typed key characters
    private static LinkedList<Character> keysTyped = new LinkedList<Character>();

    // set of key codes currently pressed down
    private static TreeSet<Integer> keysDown = new TreeSet<Integer>();

    // frame constructor
    public View (Shape next) {
        frameC = new Color[gridY][gridX];
        initialFrame(next);
        frame.setVisible(true);
        frame.addKeyListener(test);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(dim);
        frame.setTitle("Tetris");
        frame.setLayout(new BorderLayout());
    }

    public void initialFrame(Shape next) {
        for (int i = 0; i < gridY; i++)
            for (int j = 0; j < gridX; j++)
                frameC[i][j] = superC[gridY - 1 - i][j];

        playLayout(next, 0);
    }

    public void nextFrame(Shape next, Shape tetroid, long score) {
        double[] x = tetroid.getX();
        double[] y = tetroid.getY();
        for (int i = 0; i < gridY; i++)
            for (int j = 0; j < gridX; j++)
                frameC[i][j] = superC[gridY - 1 - i][j];
        
        
        for (int i = 0; i < 4; i++)
            frameC[gridY - 1 - (int) y[i]][(int) x[i]] = tetroid.getC();
    
        playLayout(next, score);
    }
    
    public void playLayout(Shape next, long score) {
        // fill CENTER >> (expands) >> component of BorderLayout() with gridY x gridX cells 
        frame.add(new GridPane(frameC, gridX, gridY), BorderLayout.CENTER);
        
        // fill LINE_END component of BorderLayout() >> (fixed)
        frame.add(new SidePane(next, score), BorderLayout.LINE_END);

        // refresh the frame
        frame.revalidate();
    }

    // true if new high score << >> setup high scores page
    public boolean gameOver(Shape next, long score) {
        savedScores = new String[10];
        int index = 0;

        String newInitials = " _ _ _";

        // read in saved scores from file
        try {
            File myObj = new File("scores.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                savedScores[index++] = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        highScore = false;
        newIndex = -1;
        for (int i = 0; i < index; i++) {
            if (score > Long.parseLong(savedScores[i].split(" ")[0])) {
                highScore = true;
                newIndex = i;
                for (int j = index - 1; j > i; j--) {
                    savedScores[j] = savedScores[j - 1];
                }

                savedScores[i] = String.valueOf(score) + newInitials;
                break;
            }
        }

        frame.add(new ScoresPane(highScore, savedScores), BorderLayout.CENTER);
        frame.add(new SidePane(next, score), BorderLayout.LINE_END);
        frame.revalidate();
        return highScore;
    }

    // update high scores page for each new key entry until initials are complete
    public void gameInitials(Shape next, long score, String initials) {
        String newInitials = " _ _ _";
        switch (initials.length()) {
            case 1:
                newInitials = " " + initials + " _ _";
                break;
            case 2:
                newInitials = " " + initials + " _";
                break;
            case 3:
                newInitials = " " + initials;
                break;
        }

        if (highScore) {
            savedScores[newIndex] = String.valueOf(score) + newInitials;
        } 

        frame.add(new ScoresPane(highScore, savedScores), BorderLayout.CENTER);
        frame.add(new SidePane(next, score), BorderLayout.LINE_END);
        frame.revalidate();
    }

    // write high scores to file after initials are complete
    public void addHighScore(long score, String initials) {
        if (highScore) {
            try {
                File myObj = new File("scores.txt");
                FileWriter myWriter = new FileWriter(myObj, false);
                for (int i = 0; i < savedScores.length; i++) {
                    myWriter.write(savedScores[i] + "\n");
                }
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }    
        }
    }

    /***************************************************************************
    *  Keyboard interactions >> ripped from StdDraw
    ***************************************************************************/
    /**
    * Returns true if the user has typed a key (that has not yet been processed).
    *
    * @return {@code true} if the user has typed a key (that has not yet been processed
    *         by {@link #nextKeyTyped()}; {@code false} otherwise
    */
    public boolean hasNextKeyTyped() {
        synchronized (keyLock) {
            return !keysTyped.isEmpty();
        }
    }

    /**
     * Returns the next key that was typed by the user (that your program has not already processed).
     * This method should be preceded by a call to {@link #hasNextKeyTyped()} to ensure
     * that there is a next key to process.
     * This method returns a Unicode character corresponding to the key
     * typed (such as {@code 'a'} or {@code 'A'}).
     * It cannot identify action keys (such as F1 and arrow keys)
     * or modifier keys (such as control).
     *
     * @return the next key typed by the user (that your program has not already processed).
     * @throws NoSuchElementException if there is no remaining key
     */
    public char nextKeyTyped() {
        synchronized (keyLock) {
            if (keysTyped.isEmpty()) {
                throw new NoSuchElementException("your program has already processed all keystrokes");
            }
            return keysTyped.remove(keysTyped.size() - 1);
            // return keysTyped.removeLast();
        }
    }

    /**
     * Returns true if the given key is being pressed.
     * <p>
     * This method takes the keycode (corresponding to a physical key)
    *  as an argument. It can handle action keys
    * (such as F1 and arrow keys) and modifier keys (such as shift and control).
    * See {@link KeyEvent} for a description of key codes.
    *
    * @param  keycode the key to check if it is being pressed
    * @return {@code true} if {@code keycode} is currently being pressed;
    *         {@code false} otherwise
    */
    public boolean isKeyPressed(int keycode) {
        synchronized (keyLock) {
            return keysDown.contains(keycode);
        }
    }
    
    private class Listener implements KeyListener {
        /**
         * This method cannot be called directly.
         */
        @Override
        public void keyTyped(KeyEvent e) {
            synchronized (keyLock) {
                keysTyped.addFirst(e.getKeyChar());
            }
        }

        /**
         * This method cannot be called directly.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            synchronized (keyLock) {
                keysDown.add(e.getKeyCode());
            }
        }

        /**
         * This method cannot be called directly.
         */
        @Override
        public void keyReleased(KeyEvent e) {
            synchronized (keyLock) {
                keysDown.remove(e.getKeyCode());
            }
        }
	} // end of private class
} 