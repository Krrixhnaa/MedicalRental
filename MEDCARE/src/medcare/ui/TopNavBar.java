package medcare.ui;

import medcare.MainFrame;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Top menu/navigation bar shown on dashboard-style screens.
 *
 * Each menu entry is a clickable label that either navigates to a real
 * screen (if a MainFrame target screen ID is given) or shows a friendly
 * "not built in this prototype yet" message (if the target is null /
 * SOON) -- clicking never silently does nothing.
 */
public class TopNavBar extends JPanel {

    /** Sentinel target meaning "no screen for this yet - show a notice". */
    public static final String SOON = null;

    /** One clickable menu entry: visible label + the screen it navigates to. */
    public static class Item {
        final String label;
        final String target;
        public Item(String label, String target) { this.label = label; this.target = target; }
    }

    public static Item item(String label, String target) { return new Item(label, target); }

    public TopNavBar(MainFrame frame, String activeLabel, String roleName, Item... menuItems) {
        setLayout(new BorderLayout());
        setBackground(UITheme.NAVY);
        setPreferredSize(new Dimension(100, 56));
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel logo = new JLabel("\u2295 MEDCARE");
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));
        logo.setForeground(Color.WHITE);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 0));
        left.setOpaque(false);
        left.add(logo);
        for (Item it : menuItems) {
            JLabel l = new JLabel(it.label);
            l.setFont(UITheme.FONT_SUB);
            boolean active = it.label.equals(activeLabel);
            l.setForeground(active ? new Color(0xFF, 0xD5, 0x4F) : Color.WHITE);
            l.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            l.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (it.target != null) {
                        frame.navigate(it.target);
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "\"" + it.label + "\" is out of scope for the current GUI prototype.\n"
                                        + "It is documented in the SRS but not yet wired up to a screen here.",
                                "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!active) l.setForeground(new Color(0xC9, 0xDD, 0xF2));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    if (!active) l.setForeground(Color.WHITE);
                }
            });
            left.add(l);
        }

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 0));
        right.setOpaque(false);
        JLabel role = new JLabel(roleName);
        role.setForeground(Color.WHITE);
        role.setFont(UITheme.FONT_SMALL);
        JButton logout = new JButton("Logout");
        logout.setFont(UITheme.FONT_SMALL);
        logout.setFocusPainted(false);
        logout.setBackground(UITheme.NAVY_DARK);
        logout.setForeground(Color.WHITE);
        logout.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logout.addActionListener(e -> frame.navigate(MainFrame.LOGIN));
        right.add(role);
        right.add(logout);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
    }
}
