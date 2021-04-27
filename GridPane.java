/**************************************************************************************************
 *
 *  JPanel extension Class with a GridBagLayout to house (gridY x gridX) Cell objects
 *  NOTE: Cells represent the background (black) and tetroid (color[row][col]) blocks!
 *  CALLED BY: View.java & SidePane.java
 **************************************************************************************************/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GridPane extends JPanel {
    GridBagLayout layout = new GridBagLayout();
    public GridPane(Color[][] color, int gridX, int gridY) {

        setBorder(new LineBorder(Color.CYAN, 3, true));
        setBackground(Color.DARK_GRAY);
        setLayout(layout);
        updateGrid(color, gridX, gridY);
    }

    public void updateGrid(Color[][] color, int gridX, int gridY) {
        // for (Component comp : getComponents()) {
        //     GridBagConstraints gbc = layout.getConstraints(comp);
        //     System.out.println(gbc.gridx + " , " + gbc.gridy);
        //     remove(comp);
        // }

        GridBagConstraints gbc = new GridBagConstraints();
        for (int row = 0; row < gridY; row++) {
            for (int col = 0; col < gridX; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                Color pass = (color[row][col] == null)? Color.BLACK : color[row][col];
                add(new Cell(pass), gbc);
            }
        }
    }
}