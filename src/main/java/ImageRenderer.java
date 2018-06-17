import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        JLabel lbl = new JLabel();
        ImageIcon icon = new ImageIcon((String) value);
        icon = new ImageIcon(icon.getImage().getScaledInstance(120, 100, BufferedImage.SCALE_SMOOTH));
        lbl.setIcon(icon);
        return lbl;
    }
}