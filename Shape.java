/**********************************************************************************
 *
 *  Class for tetroid block control flow -> Nothing to do here!
 *              
 **********************************************************************************/

import java.awt.Color;

// abstract classes do not get constructors!
public abstract class Shape extends TetraSet {

    private double[] x;
    private double[] y;

    public boolean overLaps(double x0, double y0) {
        // update x0, y0 of tetroid
        hover(x0, y0);

        // gather elements to compare
        getXY();

        // return true if tetroid indexes are null in the blob Color[][] array
        for (int i = 0; i < 4; i++)
            if (superC[(int) y[i]][(int) x[i]] != null) 
                return true;

        // else return false                         
        return false;
    }

    public boolean inBounds(double x0, double y0) {
        // update x0, y0 of tetroid
        hover(x0, y0);

        // gather elements to compare
        getXY();

        // return false if tetroid indexes are outside of our grid
        for (int i = 0; i < 4; i++)
            if (x[i] >= gridX || x[i] < 0 || y[i] >= gridY || y[i] < 0) 
                return false;        

        // else return true
        return true;
    }

    public boolean clicked(double testX, double testY) {
        // gather elements to compare
        getXY();

        // return true if this tetroid was clicked on
        for (int i = 0; i < 4; i++){
            if ((int) x[i] == testX && (int) y[i] == testY) 
                return true;
        }
                    
        // else return false
        return false;
    }

    public void slam() {
        while (drop()) {}
    }

    public boolean drop() {
        getXY();
        int yTest = (int) y[0] - 1;
        if (inBounds(x[0], yTest)) {
            if (overLaps(x[0], yTest)){
                hover(x[0], y[0] + 1);
                return false;
            }    
        }
        else {
            hover(x[0], y[0] + 1);
            return false;
        } 
        return true;
    }

    public void move(int xDir) {
        getXY();
        int xTest = (int) x[0] + xDir;
        if (inBounds(xTest, y[0])) {
            if (overLaps(xTest, y[0])){
                hover(x[0] - xDir, y[0]);
            }    
        }
        else hover(x[0] - xDir, y[0]);
    }

    public void rotateSuper() {
        int tmp = getRotation();

        getXY();
        rotate();
        hover(x[0], y[0]);

        getXY();
        if (inBounds(x[0], y[0])) {
            if (overLaps(x[0], y[0])){
                while(getRotation() != tmp)
                    rotate();
                hover(x[0], y[0]);
            }
            return;    
        }
        while(getRotation() != tmp)
            rotate();
        hover(x[0], y[0]);
    }

    private void getXY() {
        x = getX();
        y = getY();
    }

    public abstract void hover(double x, double y);
    public abstract double[] getX();
    public abstract double[] getY();
    public abstract Color getC();
    public abstract int getRotation();
    public abstract void rotate();
}
