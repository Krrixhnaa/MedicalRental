package medcare.ui;

import medcare.MainFrame;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;

public class ReportsPanel extends JPanel {

    public ReportsPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(UITheme.GREY_BG);
        add(new TopNavBar(frame, "Reports", "Admin (Super User)",
                TopNavBar.item("Dashboard", MainFrame.ADMIN_DASHBOARD),
                TopNavBar.item("Vendors", TopNavBar.SOON),
                TopNavBar.item("Equipment", TopNavBar.SOON),
                TopNavBar.item("Bookings", TopNavBar.SOON),
                TopNavBar.item("Reports", MainFrame.REPORTS)), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        headerRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerRow.setMaximumSize(new Dimension(2000, 50));
        JLabel title = UITheme.pageTitle("Reports & Analytics");
        JPanel exportBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        exportBtns.setOpaque(false);
        exportBtns.add(UITheme.secondaryButton("Export as CSV"));
        exportBtns.add(UITheme.secondaryButton("Export as PDF"));
        headerRow.add(title, BorderLayout.WEST);
        headerRow.add(exportBtns, BorderLayout.EAST);

        JComboBox<String> range = new JComboBox<>(new String[]{"Last 7 Days", "Last 30 Days", "This Quarter", "This Year"});
        range.setAlignmentX(Component.LEFT_ALIGNMENT);
        range.setMaximumSize(new Dimension(180, 30));
        JPanel rangeWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rangeWrap.setOpaque(false);
        rangeWrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        rangeWrap.setBorder(BorderFactory.createEmptyBorder(6, 0, 14, 0));
        rangeWrap.add(range);

        JPanel chartCard = UITheme.card();
        chartCard.setBackground(Color.WHITE);
        chartCard.setLayout(new BorderLayout());
        chartCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        chartCard.setMaximumSize(new Dimension(2000, 320));
        chartCard.setPreferredSize(new Dimension(100, 320));
        JLabel chartTitle = UITheme.heading("Revenue by Equipment Category (\u20B9 in thousands)");
        chartCard.add(chartTitle, BorderLayout.NORTH);
        chartCard.add(new BarChart(), BorderLayout.CENTER);

        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 16, 0));
        bottomRow.setOpaque(false);
        bottomRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomRow.setMaximumSize(new Dimension(2000, 260));
        bottomRow.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        JPanel hygieneCard = UITheme.card();
        hygieneCard.setBackground(Color.WHITE);
        hygieneCard.setLayout(new BorderLayout());
        JLabel hygieneTitle = UITheme.heading("Platform-wide Hygiene Score Trend (USP Metric)");
        hygieneCard.add(hygieneTitle, BorderLayout.NORTH);
        hygieneCard.add(new LineChart(), BorderLayout.CENTER);

        JPanel summaryCard = UITheme.card();
        summaryCard.setBackground(Color.WHITE);
        summaryCard.setLayout(new GridLayout(0, 1, 0, 10));
        summaryCard.add(UITheme.heading("Summary"));
        summaryCard.add(summaryRow("Total Bookings This Month", "312"));
        summaryCard.add(summaryRow("Total Revenue This Month", "\u20B92,41,600"));
        summaryCard.add(summaryRow("Most Rented Category", "Mobility Aids"));
        summaryCard.add(summaryRow("Avg. Hygiene Score (USP)", "96.4 / 100"));
        summaryCard.add(summaryRow("Hygiene Disputes Raised", "2 (0.6%)"));

        bottomRow.add(hygieneCard);
        bottomRow.add(summaryCard);

        content.add(headerRow);
        content.add(rangeWrap);
        content.add(chartCard);
        content.add(bottomRow);

        add(content, BorderLayout.CENTER);
    }

    private JPanel summaryRow(String label, String value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel l = new JLabel(label);
        l.setFont(UITheme.FONT_BODY);
        l.setForeground(UITheme.GREY_TEXT);
        JLabel v = new JLabel(value);
        v.setFont(UITheme.FONT_SUB);
        v.setForeground(UITheme.NAVY);
        p.add(l, BorderLayout.WEST);
        p.add(v, BorderLayout.EAST);
        return p;
    }

    static class BarChart extends JPanel {
        private final String[] labels = {"Mobility\nAids", "Hospital\nBeds", "Respiratory\nSupport", "Patient\nMonitoring", "Home-Care\nAccessories"};
        private final int[] values = {86, 64, 58, 40, 22};

        BarChart() { setOpaque(false); setPreferredSize(new Dimension(100, 220)); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            int margin = 40, bottom = 40;
            int max = 100;
            int n = values.length;
            int chartH = h - bottom - 10;
            int barW = (w - 2 * margin) / (n * 2);
            g2.setColor(UITheme.BORDER);
            g2.drawLine(margin, h - bottom, w - 10, h - bottom);
            for (int i = 0; i < n; i++) {
                int barH = (int) ((double) values[i] / max * chartH);
                int x = margin + i * (2 * barW) + barW / 2;
                int y = h - bottom - barH;
                g2.setColor(UITheme.NAVY);
                g2.fillRoundRect(x, y, barW, barH, 6, 6);
                g2.setColor(UITheme.GREY_TEXT);
                g2.setFont(UITheme.FONT_SMALL);
                g2.drawString(String.valueOf(values[i]), x + barW / 2 - 8, y - 6);
                String[] lines = labels[i].split("\n");
                for (int li = 0; li < lines.length; li++) {
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(lines[li], x + barW / 2 - fm.stringWidth(lines[li]) / 2, h - bottom + 16 + li * 13);
                }
            }
        }
    }

    static class LineChart extends JPanel {
        private final int[] values = {92, 93, 95, 94, 96, 97, 98};

        LineChart() { setOpaque(false); setPreferredSize(new Dimension(100, 180)); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            int margin = 30, bottom = 26, top = 20;
            int min = 85, max = 100;
            int n = values.length;
            int usableW = w - 2 * margin;
            int usableH = h - top - bottom;
            g2.setColor(UITheme.BORDER);
            g2.drawLine(margin, h - bottom, w - 10, h - bottom);
            g2.setColor(UITheme.SUCCESS);
            g2.setStroke(new BasicStroke(2.4f));
            int prevX = -1, prevY = -1;
            for (int i = 0; i < n; i++) {
                int x = margin + (int) ((double) i / (n - 1) * usableW);
                int y = top + usableH - (int) ((double) (values[i] - min) / (max - min) * usableH);
                if (prevX != -1) g2.drawLine(prevX, prevY, x, y);
                g2.fillOval(x - 3, y - 3, 6, 6);
                prevX = x; prevY = y;
            }
            g2.setColor(UITheme.GREY_TEXT);
            g2.setFont(UITheme.FONT_SMALL);
            g2.drawString("Week 1", margin - 10, h - bottom + 16);
            g2.drawString("Week 7", w - 50, h - bottom + 16);
        }
    }
}
