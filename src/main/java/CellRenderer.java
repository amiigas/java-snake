package main.java;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Custom list cell renderer.
 * Formats entries as name and score.
 */
class CellRenderer extends JLabel implements ListCellRenderer<Object> {

    @Override
    public Component getListCellRendererComponent(
      JList<?> list,           // the list
      Object value,            // value to display
      int index,               // cell index
      boolean isSelected,      // is the cell selected
      boolean cellHasFocus)    // does the cell have focus
    {
        Score score = (Score)value;
        String s = String.format("%-20s %d", score.name, score.points);
        setText(s);
        setEnabled(list.isEnabled());
        setFont(new Font("Menlo", Font.PLAIN, 14));
        setOpaque(true);
        return this;
    }
}
