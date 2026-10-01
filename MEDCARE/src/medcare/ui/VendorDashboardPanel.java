package medcare.ui;

import medcare.MainFrame;
import medcare.util.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VendorDashboardPanel extends JPanel {

    public VendorDashboardPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(UITheme.GREY_BG);
        add(new TopNavBar(frame, "My Listings", "CareWell Medical Supplies (Vendor)",
                TopNavBar.item("My Listings", MainFrame.VENDOR_DASHBOARD),
                TopNavBar.item("Add Equipment", MainFrame.VENDOR_DASHBOARD),
                TopNavBar.item("Bookings", MainFrame.VENDOR_DASHBOARD),
                TopNavBar.item("Payouts", TopNavBar.SOON)), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(20, 0));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Left: Add Equipment form
        JPanel left = UITheme.card();
        left.setBackground(Color.WHITE);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(340, 100));

        JLabel formTitle = UITheme.heading("Add New Equipment");
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        formTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JTextField nameF = new JTextField("Manual Wheelchair (Standard)");
        JComboBox<String> catF = new JComboBox<>(new String[]{"Mobility Aids", "Hospital Beds & Furniture", "Respiratory Support", "Patient Monitoring"});
        JTextField priceF = new JTextField("50");
        JTextField depositF = new JTextField("1000");
        JTextField stockF = new JTextField("3");
        JComboBox<String> sanitizeF = new JComboBox<>(new String[]{"UV-C Sterilisation", "Chemical Disinfection", "Autoclave + UV-C Sterilisation"});

        for (JComponent[] pair : new JComponent[][]{
                {smallLabel("Equipment Name"), nameF},
                {smallLabel("Category"), catF},
                {smallLabel("Rent Price / Day (\u20B9)"), priceF},
                {smallLabel("Security Deposit (\u20B9)"), depositF},
                {smallLabel("Stock Quantity"), stockF},
                {smallLabel("Sanitisation Method (for USP Certificate)"), sanitizeF}}) {
            JPanel row = new JPanel(new BorderLayout(0, 3));
            row.setOpaque(false);
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            row.add(pair[0], BorderLayout.NORTH);
            row.add(pair[1], BorderLayout.CENTER);
            left.add(row);
        }

        JCheckBox certify = new JCheckBox("Mark as sanitised now (auto-generates Hygiene Certificate)", true);
        certify.setOpaque(false);
        certify.setFont(UITheme.FONT_SMALL);
        certify.setForeground(UITheme.GREEN_DARK);
        certify.setAlignmentX(Component.LEFT_ALIGNMENT);
        certify.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));

        JButton addBtn = UITheme.primaryButton("Add Equipment to Catalogue");
        addBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        addBtn.setMaximumSize(new Dimension(2000, 38));
        addBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Equipment added successfully!\nA Digital Hygiene Certificate (QR) has been generated.",
                "Success", JOptionPane.INFORMATION_MESSAGE));

        left.add(formTitle, 0);
        left.add(certify);
        left.add(addBtn);

        // Right: listings + bookings
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);

        JLabel listLabel = UITheme.heading("My Equipment Listings");
        listLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        listLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        String[] cols1 = {"Equipment", "Category", "Price/Day", "Stock", "Hygiene Score", "Status"};
        Object[][] data1 = {
                {"Foldable Wheelchair (Deluxe)", "Mobility Aids", "\u20B960", "4", "98/100", "Available"},
                {"CPAP Machine (Auto)", "Respiratory Support", "\u20B9220", "2", "97/100", "Available"},
                {"Walker with Wheels", "Mobility Aids", "\u20B940", "6", "99/100", "Available"},
        };
        JTable t1 = buildTable(cols1, data1);
        JScrollPane s1 = new JScrollPane(t1);
        s1.setAlignmentX(Component.LEFT_ALIGNMENT);
        s1.setMaximumSize(new Dimension(2000, 130));

        JLabel bookLabel = UITheme.heading("Incoming Bookings for My Equipment");
        bookLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bookLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 8, 0));

        String[] cols2 = {"Booking ID", "Customer", "Equipment", "Dates", "Status", "Action"};
        Object[][] data2 = {
                {"BK-20458", "Mayank A.", "Foldable Wheelchair (Deluxe)", "01-07 Sep", "Delivered", "View"},
                {"BK-20470", "Priya S.", "CPAP Machine (Auto)", "05-12 Sep", "Pending Dispatch", "Dispatch"},
        };
        JTable t2 = buildTable(cols2, data2);
        JScrollPane s2 = new JScrollPane(t2);
        s2.setAlignmentX(Component.LEFT_ALIGNMENT);
        s2.setMaximumSize(new Dimension(2000, 100));

        right.add(listLabel);
        right.add(s1);
        right.add(bookLabel);
        right.add(s2);

        body.add(left, BorderLayout.WEST);
        body.add(right, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);
    }

    private JLabel smallLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(UITheme.FONT_SMALL);
        l.setForeground(UITheme.GREY_TEXT);
        return l;
    }

    private JTable buildTable(String[] cols, Object[][] data) {
        DefaultTableModel m = new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable t = new JTable(m);
        t.setRowHeight(30);
        t.setFont(UITheme.FONT_BODY);
        t.getTableHeader().setFont(UITheme.FONT_SUB);
        t.getTableHeader().setBackground(UITheme.NAVY);
        t.getTableHeader().setForeground(Color.WHITE);
        t.setGridColor(UITheme.BORDER);
        return t;
    }
}
