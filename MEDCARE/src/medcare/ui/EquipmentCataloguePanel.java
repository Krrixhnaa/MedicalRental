package medcare.ui;

import medcare.MainFrame;
import medcare.model.Equipment;
import medcare.util.DummyData;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EquipmentCataloguePanel extends JPanel {

    public EquipmentCataloguePanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(UITheme.GREY_BG);

        add(new TopNavBar(frame, "Search Equipment", "Mayank Aggarwal (Customer)",
                TopNavBar.item("Home", MainFrame.CUSTOMER_DASHBOARD),
                TopNavBar.item("Search Equipment", MainFrame.CATALOGUE),
                TopNavBar.item("My Bookings", MainFrame.MY_BOOKINGS),
                TopNavBar.item("Profile", MainFrame.PROFILE)), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(16, 0));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        // --- Filters sidebar ---
        JPanel filters = UITheme.card();
        filters.setLayout(new BoxLayout(filters, BoxLayout.Y_AXIS));
        filters.setPreferredSize(new Dimension(230, 100));
        filters.setBackground(Color.WHITE);

        JLabel fTitle = UITheme.heading("Filters");
        fTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JComboBox<String> category = new JComboBox<>(DummyData.categories().toArray(new String[0]));
        JComboBox<String> city = new JComboBox<>(DummyData.cities().toArray(new String[0]));
        JSlider price = new JSlider(0, 500, 300);
        price.setMajorTickSpacing(250);
        price.setPaintTicks(true);
        price.setPaintLabels(true);
        price.setOpaque(false);
        JCheckBox onlyAvailable = new JCheckBox("Show only Available", true);
        onlyAvailable.setOpaque(false);
        JCheckBox highHygiene = new JCheckBox("Hygiene Score \u2265 95 (USP)", false);
        highHygiene.setOpaque(false);
        highHygiene.setForeground(UITheme.GREEN_DARK);
        highHygiene.setFont(UITheme.FONT_SMALL.deriveFont(Font.BOLD));

        JButton apply = UITheme.primaryButton("Apply Filters");
        apply.setAlignmentX(Component.LEFT_ALIGNMENT);
        apply.setMaximumSize(new Dimension(2000, 34));

        for (JComponent c : new JComponent[]{fTitle, sectionLabel("Category"), category,
                sectionLabel("City"), city, sectionLabel("Max Price / Day (\u20B9)"), price,
                sectionLabel(""), onlyAvailable, highHygiene}) {
            c.setAlignmentX(Component.LEFT_ALIGNMENT);
            c.setMaximumSize(new Dimension(2000, c.getPreferredSize().height));
            filters.add(c);
            filters.add(Box.createVerticalStrut(8));
        }
        filters.add(Box.createVerticalStrut(6));
        filters.add(apply);

        // --- Results grid ---
        JPanel resultsWrap = new JPanel(new BorderLayout());
        resultsWrap.setOpaque(false);

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        JLabel count = new JLabel("6 results found in Jaipur");
        count.setFont(UITheme.FONT_BODY);
        JComboBox<String> sort = new JComboBox<>(new String[]{"Sort: Relevance", "Price: Low to High", "Price: High to Low", "Hygiene Score", "Vendor Rating"});
        topRow.add(count, BorderLayout.WEST);
        topRow.add(sort, BorderLayout.EAST);
        topRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel grid = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 14));
        grid.setOpaque(false);
        List<Equipment> items = DummyData.equipmentList();
        for (Equipment eq : items) grid.add(EquipmentCardFactory.build(eq, frame));

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        resultsWrap.add(topRow, BorderLayout.NORTH);
        resultsWrap.add(scroll, BorderLayout.CENTER);

        body.add(filters, BorderLayout.WEST);
        body.add(resultsWrap, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);
    }

    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(UITheme.FONT_SMALL.deriveFont(Font.BOLD));
        l.setForeground(UITheme.GREY_TEXT);
        return l;
    }
}
