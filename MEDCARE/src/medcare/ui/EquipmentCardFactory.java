package medcare.ui;

import medcare.MainFrame;
import medcare.model.Equipment;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;

public class EquipmentCardFactory {

    public static JPanel build(Equipment eq, MainFrame frame) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(250, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER, 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));

        JLabel img = new JLabel(iconFor(eq.category), SwingConstants.CENTER);
        img.setFont(new Font("SansSerif", Font.PLAIN, 40));
        img.setOpaque(true);
        img.setBackground(UITheme.LIGHT_BLUE);
        img.setPreferredSize(new Dimension(100, 80));
        img.setMaximumSize(new Dimension(2000, 80));
        img.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel name = new JLabel(eq.name);
        name.setFont(UITheme.FONT_SUB);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        name.setBorder(BorderFactory.createEmptyBorder(8, 0, 2, 0));

        JLabel meta = new JLabel(eq.category + " \u2022 " + eq.city);
        meta.setFont(UITheme.FONT_SMALL);
        meta.setForeground(UITheme.GREY_TEXT);
        meta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        JLabel price = new JLabel("\u20B9" + (int) eq.pricePerDay + "/day");
        price.setFont(UITheme.FONT_SUB);
        price.setForeground(UITheme.NAVY);
        JLabel rating = new JLabel("\u2605 " + eq.vendorRating);
        rating.setFont(UITheme.FONT_SMALL);
        rating.setForeground(UITheme.GOLD_DARK);
        row.add(price, BorderLayout.WEST);
        row.add(rating, BorderLayout.EAST);

        Color hygieneColor = eq.hygieneScore >= 95 ? UITheme.SUCCESS
                : eq.hygieneScore >= 85 ? UITheme.GOLD_DARK : UITheme.DANGER;
        JLabel hygiene = new JLabel("\u2713 Hygiene Score: " + eq.hygieneScore + "/100");
        hygiene.setFont(UITheme.FONT_SMALL.deriveFont(Font.BOLD));
        hygiene.setForeground(hygieneColor);
        hygiene.setAlignmentX(Component.LEFT_ALIGNMENT);
        hygiene.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        boolean available = eq.availability.equals("Available");
        JButton viewBtn = available ? UITheme.primaryButton("View Details") : UITheme.secondaryButton("Notify Me");
        viewBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        viewBtn.setMaximumSize(new Dimension(2000, 32));
        viewBtn.addActionListener(e -> {
            frame.selectedEquipment = eq;
            frame.navigate(MainFrame.EQUIPMENT_DETAIL);
        });

        JLabel status = UITheme.pill(eq.availability,
                available ? UITheme.LIGHT_GREEN : UITheme.LIGHT_GOLD,
                available ? UITheme.GREEN_DARK : UITheme.GOLD_DARK);
        status.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(img);
        card.add(name);
        card.add(meta);
        card.add(row);
        card.add(hygiene);
        card.add(status);
        card.add(Box.createVerticalStrut(8));
        card.add(viewBtn);
        return card;
    }

    private static String iconFor(String category) {
        if (category.contains("Mobility")) return "\uD83E\uDDBD";
        if (category.contains("Bed")) return "\uD83D\uDECF";
        if (category.contains("Respiratory")) return "\uD83E\uDEC1";
        if (category.contains("Monitor")) return "\uD83D\uDCFA";
        return "\uD83C\uDFE5";
    }
}
