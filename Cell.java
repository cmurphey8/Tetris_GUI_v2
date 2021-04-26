/**************************************************************************************************
 *
 *  JButton extension Class to draw each block in the tetris grid -> Nothing to do here!
 *  NOTE: buttons in this class are deactivated!
 *  CALLED BY: GridPane.java
 * 
 **************************************************************************************************/

import java.awt.*;
import javax.swing.*;

public class Cell extends JButton {
    public Color background;
    public float hsbVals[];

    public Cell(Color background) {
        // update class vars
        this.background = background;
        hsbVals = Color.RGBtoHSB(   background.getRed(),
                                    background.getGreen(),
                                    background.getBlue(), null );
        
        // dull colors from standard - drop shadow
        Color shadow = Color.getHSBColor( hsbVals[0], hsbVals[1], 0.8f * hsbVals[2] ); 

        // lowlight setup for drop shadow
        setBackground(shadow);

        // disable rollover, click effects
        setEnabled(false);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(27, 27);
    }

    private void doDrawing(Graphics g) {       

        // option dull colors from standard - inner light >> no dimming at the moment!
        Color inner = Color.getHSBColor( hsbVals[0], hsbVals[1], hsbVals[2] ); //0.95f * hsbVals[2] ); 

        int x = 0;
        int y = 0;
        int hw = 26;
        int buffer = 4;
        
        // cast g to from Graphics to Graphics2D
        Graphics2D g2d = (Graphics2D) g;

        // draw on 3D block effects - drop shadow (highlight all but bottom-right)
        g2d.setColor(inner);
        g2d.fillRect(x, y, hw - buffer, hw - buffer);

        // draw on 3D block effects - outer outline
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, hw, hw);

        // draw on 3D block effects - cross lines
        g2d.drawLine(x, y, x + hw, y + hw);
        g2d.drawLine(x, y + hw, x + hw, y);

        // draw on 3D block effects - inner box over cross lines
        g2d.setColor(inner);
        g2d.fillRect(x + buffer, y + buffer, hw - 2 * buffer, hw - 2 * buffer);

        // draw on 3D block effects - inner outline
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x + buffer, y + buffer, hw - 2 * buffer, hw - 2 * buffer);
    }

    @Override
    public void paintComponent(Graphics g) {
        // override implicit JButton method *paintComponent* to execute *doDrawing*
        super.paintComponent(g);
        doDrawing(g);
    }
}
