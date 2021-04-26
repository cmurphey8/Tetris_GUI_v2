/**************************************************************************************************
 *
 *  JPanel extension Class with a GridBagLayout to house (gridY x gridX) Cell objects
 *  NOTE: Cells represent the background (black) and tetroid (color[row][col]) blocks!
 *  CALLED BY: View.java & SidePane.java
 **************************************************************************************************/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ScoresPane extends JPanel {
    BorderLayout layout = new BorderLayout();
    GridLayout scoresLayout = new GridLayout(10,0);

    public ScoresPane(boolean highScore, int newIndex, String[] savedScores) {
        setLayout(layout);

        if (highScore) {
            JPanel displayGrid = new JPanel();
            displayGrid.setBackground(Color.BLACK);
            displayGrid.setLayout(new GridLayout(2,0));

            JLabel label = new JLabel("New High Score!");
            label.setForeground(Color.GREEN);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 20));
            displayGrid.add(label);    

            label = new JLabel("Enter Your Initials");
            label.setForeground(Color.ORANGE);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            displayGrid.add(label);   

            displayGrid.setBorder(new LineBorder(Color.GREEN, 3, true));
            add(displayGrid, BorderLayout.NORTH);
        }
        
        JPanel scoresGrid = new JPanel();
        scoresGrid.setBackground(Color.BLACK);
        scoresGrid.setLayout(scoresLayout);

        Color[] rotation = {Color.CYAN, Color.ORANGE, Color.MAGENTA, Color.GREEN, Color.YELLOW};
        for (int i = 0; i < savedScores.length; i++) {
            JLabel label = new JLabel(savedScores[i]);
            label.setForeground(rotation[(i % 5)]);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            scoresGrid.add(label);
        }
        scoresGrid.setBorder(new LineBorder(Color.ORANGE, 3, true));
        add(scoresGrid, BorderLayout.CENTER);
    }
}