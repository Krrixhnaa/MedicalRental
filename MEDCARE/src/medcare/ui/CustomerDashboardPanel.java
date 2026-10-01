package medcare.ui;

import medcare.MainFrame;
import medcare.model.Equipment;
import medcare.util.DummyData;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomerDashboardPanel extends JPanel {

    public CustomerDashboardPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(UITheme.GREY_BG);

        add(new TopNavBar(frame, "Home", "Mayank Aggarwal (Customer)",
                TopNavBar.item("Home", MainFrame.CUSTOMER_DASHBOARD),
                TopNavBar.item("Search Equipment", MainFrame.CATALOGUE),
                TopNavBar.item("My Bookings", MainFrame.MY_BOOKINGS),
                TopNavBar.item("Profile", MainFrame.PROFILE)), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel welcome = new JLabel("Welcome back, Mayank \uD83D\uDC4B");
        welcome.setFont(UITheme.FONT_TITLE);
        welcome.setForeground(UITheme.NAVY);

        JLabel sub = new JLabel("Find and rent verified, sanitised medical equipment near you.");
        sub.setFont(UITheme.FONT_BODY);
        sub.setForeground(UITheme.GREY_TEXT);
        sub.setBorder(BorderFactory.createEmptyBorder(2, 0, 16, 0));

        // Quick search bar
        JPanel searchBar = UITheme.card();
        searchBar.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));
        JTextField search = new JTextField("Search wheelchairs, oxygen concentrators, hospital beds...", 34);
        JComboBox<String> cat = new JComboBox<>(DummyData.categories().toArray(new String[0]));
        JButton searchBtn = UITheme.primaryButton("Search");
        searchBtn.addActionListener(e -> frame.navigate(MainFrame.CATALOGUE));
        searchBar.add(new JLabel("\uD83D\uDD0D"));
        searchBar.add(search);
        searchBar.add(cat);
        searchBar.add(searchBtn);

        // USP highlight banner
        JPanel uspBanner = new JPanel(new BorderLayout());
        uspBanner.setBackground(UITheme.LIGHT_GREEN);
        uspBanner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.GREEN_DARK, 1),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)));
        JLabel uspText = new JLabel("<html>\u2705 <b>Our USP:</b> Every piece of equipment carries a "
                + "<b>QR-Verified Digital Hygiene Certificate</b> showing its last sanitisation date, "
                + "technician and hygiene score &mdash; scan it before you accept delivery.</html>");
        uspText.setFont(UITheme.FONT_BODY);
        uspBanner.add(uspText, BorderLayout.CENTER);
        uspBanner.setPreferredSize(new Dimension(1080, 50));
        uspBanner.setMaximumSize(new Dimension(1080, 60));
        uspBanner.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Quick stat cards
        JPanel stats = new JPanel(new GridLayout(1, 4, 14, 0));
        stats.setOpaque(false);
        stats.setPreferredSize(new Dimension(1080, 80));
        stats.setMaximumSize(new Dimension(1080, 90));
        stats.setAlignmentX(Component.LEFT_ALIGNMENT);
        stats.add(statCard("Active Rentals", "2", UITheme.LIGHT_BLUE));
        stats.add(statCard("Completed Rentals", "5", UITheme.LIGHT_GREEN));
        stats.add(statCard("Avg. Hygiene Score", "97 / 100", UITheme.LIGHT_GOLD));
        stats.add(statCard("Wallet Credits", "\u20B9250", UITheme.LIGHT_BLUE));

        JLabel featuredLabel = UITheme.heading("Featured Equipment Near You");
        featuredLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 8, 0));
        featuredLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel grid = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 14));
        grid.setOpaque(false);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);
        List<Equipment> items = DummyData.equipmentList();
        for (int i = 0; i < Math.min(6, items.size()); i++) {
            grid.add(EquipmentCardFactory.build(items.get(i), frame));
        }

        searchBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(welcome);
        content.add(sub);
        content.add(searchBar);
        content.add(Box.createVerticalStrut(14));
        content.add(uspBanner);
        content.add(Box.createVerticalStrut(14));
        content.add(stats);
        content.add(featuredLabel);
        content.add(grid);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel statCard(String label, String value, Color bg) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(bg);
        p.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif", Font.BOLD, 20));
        v.setForeground(UITheme.NAVY);
        JLabel l = new JLabel(label);
        l.setFont(UITheme.FONT_SMALL);
        l.setForeground(UITheme.GREY_TEXT);
        p.add(v);
        p.add(l);
        return p;
    }
}
