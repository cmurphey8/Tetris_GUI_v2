/**************************************************************************************************
 *
 *  Class to draw the sidePane that houses the next block and the score
 *  CALLED BY: View.java
 *  
 *  TODO: 
 *  (1) choose a Layout Manager to nest (add) here in SidePane
 *  (2) choose GUI element to house text “SCORE:”
 *  (3) choose GUI element to display score
 * 
 **************************************************************************************************/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class SidePane extends JPanel {

    public SidePane(Shape next, long score) {
        // SidePane layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // next block color grid
        Color[][] nextC = new Color[4][6];
        double[] x = next.getX();
        double[] y = next.getY();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                nextC[i][j] = Color.BLACK;
            }
        }
        for (int j = 0; j < 4; j++) {
            nextC[3 - (int) y[j]][(int) x[j]] = next.getC();
        }

        // next block grid init & add
        add(new GridPane(nextC, 6, 4));

        /* TODO:     
            (1) choose a Layout Manager to nest (add) here in SidePane
            (2) choose GUI element to house text “SCORE:”
            (3) choose GUI element to display score
            
            NOTE:   We could have declared a new class "ScorePanel extends JPanel" 
                    in a separate file to perform the updates below 
                    -> eg, as we did with GridPane, SidePane, Cell           
        */

        // score panel init
        JPanel ScorePanel = new JPanel();

        // (1) choose score panel layout
        ScorePanel.setLayout(new BorderLayout());
        ScorePanel.setBackground(Color.BLACK);

        // (2) init label to display score title
        JLabel textDisp = new JLabel("SCORE");
        textDisp.setBorder(new LineBorder(Color.CYAN, 3, true));

        // (2) set score title font-size & color
        textDisp.setFont(new Font(textDisp.getFont().getName(), Font.PLAIN, 20));
        textDisp.setForeground(Color.ORANGE);

        // (2) set preferred (minimum) size for this label
        textDisp.setPreferredSize(new Dimension(100,100));

        // (2) set label alignment 
        textDisp.setVerticalAlignment(SwingConstants.CENTER);
        textDisp.setHorizontalAlignment(SwingConstants.CENTER);
        
        // (2) add score title label to NORTH component of BorderLayout
        ScorePanel.add(textDisp, BorderLayout.NORTH);
        
        // (3) init label to display score var
        JLabel scoreDisp = new JLabel(String.valueOf(score));
        scoreDisp.setBorder(new LineBorder(Color.CYAN, 3, true));

        // (3) set score title font-size & color
        scoreDisp.setFont(new Font(scoreDisp.getFont().getName(), Font.PLAIN, 25));
        scoreDisp.setForeground(Color.CYAN);

        // (3) set label alignment
        scoreDisp.setHorizontalAlignment(SwingConstants.CENTER);

        // (3) add score var label CENTER component of BorderLayout
        //     NOTE: CENTER component will fill remaining space!
        ScorePanel.add(scoreDisp, BorderLayout.CENTER);

        // (1) add scorePanel to sidePane
        add(ScorePanel);
    }
}