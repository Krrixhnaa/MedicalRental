package medcare.ui;

import medcare.MainFrame;
import medcare.util.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminDashboardPanel extends JPanel {

    public AdminDashboardPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(UITheme.GREY_BG);
        add(new TopNavBar(frame, "Dashboard", "Admin (Super User)",
                TopNavBar.item("Dashboard", MainFrame.ADMIN_DASHBOARD),
                TopNavBar.item("Vendors", TopNavBar.SOON),
                TopNavBar.item("Equipment", TopNavBar.SOON),
                TopNavBar.item("Bookings", TopNavBar.SOON),
                TopNavBar.item("Reports", MainFrame.REPORTS)), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = UITheme.pageTitle("Administrator Dashboard");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel sub = new JLabel("Platform overview \u2014 " + java.time.LocalDate.now());
        sub.setFont(UITheme.FONT_SMALL);
        sub.setForeground(UITheme.GREY_TEXT);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        sub.setBorder(BorderFactory.createEmptyBorder(2, 0, 16, 0));

        JPanel stats = new JPanel(new GridLayout(1, 5, 12, 0));
        stats.setOpaque(false);
        stats.setMaximumSize(new Dimension(2000, 90));
        stats.setAlignmentX(Component.LEFT_ALIGNMENT);
        stats.add(stat("Total Bookings", "1,248", UITheme.LIGHT_BLUE));
        stats.add(stat("Active Vendors", "37", UITheme.LIGHT_GREEN));
        stats.add(stat("Pending KYC", "5", UITheme.LIGHT_GOLD));
        stats.add(stat("Revenue (MTD)", "\u20B92.4L", UITheme.LIGHT_BLUE));
        stats.add(stat("Avg. Hygiene Score", "96.4", UITheme.LIGHT_GREEN));

        JLabel section1 = UITheme.heading("Pending Vendor Verifications (KYC)");
        section1.setAlignmentX(Component.LEFT_ALIGNMENT);
        section1.setBorder(BorderFactory.createEmptyBorder(22, 0, 8, 0));

        String[] cols1 = {"Vendor ID", "Vendor Name", "City", "Documents", "Submitted On", "Action"};
        Object[][] data1 = {
                {"VEN-118", "SafeHands Medical Rentals", "Jaipur", "GST, Shop Licence", "01 Sep 2026", "Approve / Reject"},
                {"VEN-119", "QuickCare Equipment Hub", "Jodhpur", "GST, PAN", "02 Sep 2026", "Approve / Reject"},
                {"VEN-120", "Sunrise Health Aids", "Udaipur", "GST, Shop Licence", "03 Sep 2026", "Approve / Reject"},
        };
        JTable table1 = buildTable(cols1, data1);
        JScrollPane scroll1 = new JScrollPane(table1);
        scroll1.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll1.setPreferredSize(new Dimension(100, 130));
        scroll1.setMaximumSize(new Dimension(2000, 130));

        JLabel section2 = UITheme.heading("Recent Disputes / Flags");
        section2.setAlignmentX(Component.LEFT_ALIGNMENT);
        section2.setBorder(BorderFactory.createEmptyBorder(20, 0, 8, 0));

        String[] cols2 = {"Booking ID", "Customer", "Issue Type", "Details", "Status"};
        Object[][] data2 = {
                {"BK-20301", "R. Sharma", "Hygiene Mismatch (USP)", "Certificate showed 98, item looked unclean", "Under Review"},
                {"BK-20287", "P. Singh", "Late Delivery", "Delivered 2 hrs after slot", "Resolved"},
        };
        JTable table2 = buildTable(cols2, data2);
        JScrollPane scroll2 = new JScrollPane(table2);
        scroll2.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll2.setPreferredSize(new Dimension(100, 100));
        scroll2.setMaximumSize(new Dimension(2000, 100));

        JButton reportsBtn = UITheme.secondaryButton("Open Full Reports & Analytics \u2192");
        reportsBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        reportsBtn.addActionListener(e -> frame.navigate(MainFrame.REPORTS));
        reportsBtn.setMaximumSize(new Dimension(280, 40));
        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnWrap.setOpaque(false);
        btnWrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnWrap.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        btnWrap.add(reportsBtn);

        content.add(title);
        content.add(sub);
        content.add(stats);
        content.add(section1);
        content.add(scroll1);
        content.add(section2);
        content.add(scroll2);
        content.add(btnWrap);

        JScrollPane outer = new JScrollPane(content);
        outer.setBorder(null);
        outer.getVerticalScrollBar().setUnitIncrement(16);
        add(outer, BorderLayout.CENTER);
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

    private JPanel stat(String label, String value, Color bg) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(bg);
        p.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif", Font.BOLD, 19));
        v.setForeground(UITheme.NAVY);
        JLabel l = new JLabel(label);
        l.setFont(UITheme.FONT_SMALL);
        l.setForeground(UITheme.GREY_TEXT);
        p.add(v);
        p.add(l);
        return p;
    }
}
