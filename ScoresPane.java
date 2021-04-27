/**************************************************************************************************
 *
 *  JPanel extension Class with a BorderLayout to host High Scores
 *  CALLED BY: View.java
 * 
 **************************************************************************************************/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ScoresPane extends JPanel {
    BorderLayout layout = new BorderLayout();
    GridLayout scoresLayout = new GridLayout(10,0);

    public ScoresPane(boolean highScore, String[] savedScores) {
        // borderlayout on outer scores panel
        setLayout(layout);

        // prompt to add initials in NORTH panel of border laout if high score was achieved
        if (highScore) {
            // add prompt in displayGrid subpanel >> grid layout to set all text display in one column
            JPanel displayGrid = new JPanel();
            displayGrid.setBackground(Color.BLACK);
            displayGrid.setLayout(new GridLayout(2,0));

            // adjust parameters specific to this label before adding it to displayGrid
            JLabel label = new JLabel("New High Score!");
            label.setForeground(Color.GREEN);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 20));
            displayGrid.add(label);    

            // adjust parameters specific to this label before adding it to displayGrid
            label = new JLabel("Enter Your Initials");
            label.setForeground(Color.ORANGE);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            displayGrid.add(label);   

            // add displayGrid to the NORTH subpanel of the scores panel
            displayGrid.setBorder(new LineBorder(Color.GREEN, 3, true));
            add(displayGrid, BorderLayout.NORTH);
        }
        
        // display intitials in CENTER panel of border layout >> fill remaining space
        JPanel scoresGrid = new JPanel();
        scoresGrid.setBackground(Color.BLACK);
        scoresGrid.setLayout(scoresLayout);

        // rotate through colors with each label for visual effect
        Color[] rotation = {Color.CYAN, Color.ORANGE, Color.MAGENTA, Color.GREEN, Color.YELLOW};
        for (int i = 0; i < savedScores.length; i++) {
            // notice we can reuse "label" in a loop just as we did manually for the displayGrid labels
            JLabel label = new JLabel(savedScores[i]);
            label.setForeground(rotation[(i % 5)]);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            scoresGrid.add(label);
        }
        // add scoresGrid to the CENTER border layout panel
        scoresGrid.setBorder(new LineBorder(Color.ORANGE, 3, true));
        add(scoresGrid, BorderLayout.CENTER);
    }
}
