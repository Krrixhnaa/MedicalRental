package medcare.ui;

import medcare.MainFrame;
import medcare.model.Booking;
import medcare.util.DummyData;
import medcare.util.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyBookingsPanel extends JPanel {

    public MyBookingsPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(UITheme.GREY_BG);
        add(new TopNavBar(frame, "My Bookings", "Mayank Aggarwal (Customer)",
                TopNavBar.item("Home", MainFrame.CUSTOMER_DASHBOARD),
                TopNavBar.item("Search Equipment", MainFrame.CATALOGUE),
                TopNavBar.item("My Bookings", MainFrame.MY_BOOKINGS),
                TopNavBar.item("Profile", MainFrame.PROFILE)), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = UITheme.pageTitle("My Bookings & Order Tracking");
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));

        String[] cols = {"Booking ID", "Equipment", "Start Date", "End Date", "Status", "Amount", "Hygiene Cert.", "Actions"};
        List<Booking> bookings = DummyData.bookingList();
        Object[][] data = new Object[bookings.size()][cols.length];
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            data[i][0] = b.bookingId;
            data[i][1] = b.equipmentName;
            data[i][2] = b.startDate;
            data[i][3] = b.endDate;
            data[i][4] = b.status;
            data[i][5] = "\u20B9" + (int) b.totalAmount;
            data[i][6] = "\u2713 Verified";
            data[i][7] = actionFor(b.status);
        }

        DefaultTableModel model = new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(34);
        table.setFont(UITheme.FONT_BODY);
        table.getTableHeader().setFont(UITheme.FONT_SUB);
        table.getTableHeader().setBackground(UITheme.NAVY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(UITheme.LIGHT_BLUE);
        table.setGridColor(UITheme.BORDER);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UITheme.BORDER));

        // Tracking timeline strip for the active booking
        JPanel timelineCard = UITheme.card();
        timelineCard.setBackground(Color.WHITE);
        timelineCard.setLayout(new BorderLayout());
        timelineCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(16, 0, 0, 0),
                timelineCard.getBorder()));
        JLabel timelineTitle = UITheme.heading("Live Tracking - BK-20461 (Electric Hospital Bed)");
        timelineCard.add(timelineTitle, BorderLayout.NORTH);
        timelineCard.add(new TrackingTimeline(), BorderLayout.CENTER);

        content.add(title, BorderLayout.NORTH);
        content.add(scroll, BorderLayout.CENTER);
        content.add(timelineCard, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
    }

    private String actionFor(String status) {
        switch (status) {
            case "Delivered": return "Request Return";
            case "Confirmed": return "Track / Cancel";
            case "Returned": return "Rate & Review";
            default: return "View";
        }
    }

    /** Small custom-painted horizontal delivery-progress tracker. */
    static class TrackingTimeline extends JPanel {
        private final String[] steps = {"Booked", "Confirmed", "Dispatched", "Out for Delivery", "Delivered"};
        private final int activeStep = 2;

        TrackingTimeline() {
            setPreferredSize(new Dimension(100, 90));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            int margin = 60;
            int y = h / 2 - 10;
            int usable = w - 2 * margin;
            int n = steps.length;
            g2.setStroke(new BasicStroke(4));
            g2.setColor(UITheme.BORDER);
            g2.drawLine(margin, y, w - margin, y);
            g2.setColor(UITheme.NAVY);
            int activeX = margin + (int) ((double) activeStep / (n - 1) * usable);
            g2.drawLine(margin, y, activeX, y);

            for (int i = 0; i < n; i++) {
                int x = margin + (int) ((double) i / (n - 1) * usable);
                boolean done = i <= activeStep;
                g2.setColor(done ? UITheme.NAVY : Color.WHITE);
                g2.fillOval(x - 9, y - 9, 18, 18);
                g2.setColor(UITheme.NAVY);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(x - 9, y - 9, 18, 18);
                g2.setFont(UITheme.FONT_SMALL);
                FontMetrics fm = g2.getFontMetrics();
                String label = steps[i];
                g2.drawString(label, x - fm.stringWidth(label) / 2, y + 30);
            }
        }
    }
}
