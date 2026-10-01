package medcare.util;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Central place for the colour palette, fonts and small UI helper
 * factory-methods used across every screen of the MEDCARE prototype.
 * Keeping this in one class ensures a consistent look-and-feel that
 * mirrors the colour scheme used in the project's SRS diagrams.
 */
public class UITheme {
    public static final Color NAVY        = new Color(0x1F, 0x4E, 0x79);
    public static final Color NAVY_DARK   = new Color(0x14, 0x36, 0x52);
    public static final Color LIGHT_BLUE  = new Color(0xDC, 0xE9, 0xF7);
    public static final Color LIGHT_GREEN = new Color(0xE8, 0xF3, 0xE5);
    public static final Color GREEN_DARK  = new Color(0x2E, 0x5B, 0x2E);
    public static final Color LIGHT_GOLD  = new Color(0xFD, 0xF3, 0xD8);
    public static final Color GOLD_DARK   = new Color(0x7A, 0x5C, 0x00);
    public static final Color GREY_BG     = new Color(0xF4, 0xF6, 0xF9);
    public static final Color GREY_TEXT   = new Color(0x59, 0x59, 0x59);
    public static final Color WHITE       = Color.WHITE;
    public static final Color DANGER      = new Color(0x8A, 0x1F, 0x1F);
    public static final Color SUCCESS     = new Color(0x1E, 0x7A, 0x34);
    public static final Color BORDER      = new Color(0xCC, 0xCC, 0xCC);

    public static final Font FONT_TITLE   = new Font("SansSerif", Font.BOLD, 22);
    public static final Font FONT_HEADING = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_SUB     = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_BODY    = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_SMALL   = new Font("SansSerif", Font.PLAIN, 11);
    public static final Font FONT_BUTTON  = new Font("SansSerif", Font.BOLD, 13);

    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        styleButton(b, NAVY, WHITE);
        return b;
    }

    public static JButton secondaryButton(String text) {
        JButton b = new JButton(text);
        styleButton(b, LIGHT_BLUE, NAVY);
        return b;
    }

    public static JButton dangerButton(String text) {
        JButton b = new JButton(text);
        styleButton(b, new Color(0xC0, 0x39, 0x2B), WHITE);
        return b;
    }

    public static JButton successButton(String text) {
        JButton b = new JButton(text);
        styleButton(b, new Color(0x27, 0xAE, 0x60), WHITE);
        return b;
    }

    private static void styleButton(JButton b, Color bg, Color fg) {
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(FONT_BUTTON);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.setBorderPainted(false);
    }

    public static JLabel heading(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_HEADING);
        l.setForeground(NAVY);
        return l;
    }

    public static JLabel pageTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_TITLE);
        l.setForeground(NAVY);
        return l;
    }

    public static TitledBorder sectionBorder(String title) {
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER, 1), " " + title + " ");
        tb.setTitleFont(FONT_SUB);
        tb.setTitleColor(NAVY);
        return tb;
    }

    public static JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        return p;
    }

    public static JLabel pill(String text, Color bg, Color fg) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(bg);
        l.setForeground(fg);
        l.setFont(FONT_SMALL.deriveFont(Font.BOLD));
        l.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        return l;
    }
}
