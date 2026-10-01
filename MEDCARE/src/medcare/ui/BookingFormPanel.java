package medcare.ui;

import medcare.MainFrame;
import medcare.model.Equipment;
import medcare.util.DummyData;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;

public class BookingFormPanel extends JPanel implements MainFrame.Refreshable {

    private final MainFrame frame;
    private final JLabel summaryTitle = new JLabel();
    private final JLabel summaryPrice = new JLabel();
    private final JLabel totalLabel = new JLabel();

    public BookingFormPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UITheme.GREY_BG);
        add(new TopNavBar(frame, "Search Equipment", "Mayank Aggarwal (Customer)",
                TopNavBar.item("Home", MainFrame.CUSTOMER_DASHBOARD),
                TopNavBar.item("Search Equipment", MainFrame.CATALOGUE),
                TopNavBar.item("My Bookings", MainFrame.MY_BOOKINGS),
                TopNavBar.item("Profile", MainFrame.PROFILE)), BorderLayout.NORTH);

        JPanel body = new JPanel(new GridBagLayout());
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(0, 0, 0, 20);
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 1;

        // Left: address & schedule form
        JPanel left = UITheme.card();
        left.setBackground(Color.WHITE);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        JLabel title = UITheme.pageTitle("Confirm Booking Details");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));

        JTextField addr = new JTextField("221B, Vaishali Nagar, Jaipur, Rajasthan - 302021");
        JTextField contact = new JTextField("+91 98xxxxxx30");
        JComboBox<String> slot = new JComboBox<>(new String[]{"9 AM - 12 PM", "12 PM - 3 PM", "3 PM - 6 PM", "6 PM - 9 PM"});
        JTextArea instructions = new JTextArea("Please call before arriving. Second floor, no lift.", 3, 20);
        instructions.setLineWrap(true);
        instructions.setFont(UITheme.FONT_BODY);

        for (JComponent[] pair : new JComponent[][]{
                {label("Delivery Address"), addr},
                {label("Contact Number"), contact},
                {label("Preferred Delivery Slot"), slot},
                {label("Delivery Instructions"), new JScrollPane(instructions)}}) {
            JPanel row = new JPanel(new BorderLayout(0, 4));
            row.setOpaque(false);
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
            row.add(pair[0], BorderLayout.NORTH);
            row.add(pair[1], BorderLayout.CENTER);
            left.add(row);
        }

        JCheckBox hygieneAck = new JCheckBox("I have reviewed the equipment's Digital Hygiene Certificate (USP)", true);
        hygieneAck.setOpaque(false);
        hygieneAck.setFont(UITheme.FONT_SMALL);
        hygieneAck.setForeground(UITheme.GREEN_DARK);
        hygieneAck.setAlignmentX(Component.LEFT_ALIGNMENT);

        left.add(title, 0);
        left.add(hygieneAck);

        // Right: order summary
        JPanel right = UITheme.card();
        right.setBackground(Color.WHITE);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setPreferredSize(new Dimension(320, 100));

        JLabel sumHeading = UITheme.heading("Order Summary");
        sumHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        sumHeading.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        summaryTitle.setFont(UITheme.FONT_SUB);
        summaryTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        summaryPrice.setFont(UITheme.FONT_BODY);
        summaryPrice.setForeground(UITheme.GREY_TEXT);
        summaryPrice.setAlignmentX(Component.LEFT_ALIGNMENT);
        summaryPrice.setBorder(BorderFactory.createEmptyBorder(2, 0, 14, 0));

        JSeparator sep = new JSeparator();
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        sep.setMaximumSize(new Dimension(2000, 1));

        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        totalLabel.setForeground(UITheme.NAVY);
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        totalLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 20, 0));

        JButton proceed = UITheme.primaryButton("Proceed to Payment \u2192");
        proceed.setAlignmentX(Component.LEFT_ALIGNMENT);
        proceed.setMaximumSize(new Dimension(2000, 40));
        proceed.addActionListener(e -> frame.navigate(MainFrame.PAYMENT));

        right.add(sumHeading);
        right.add(summaryTitle);
        right.add(summaryPrice);
        right.add(sep);
        right.add(totalLabel);
        right.add(proceed);

        gc.gridx = 0; gc.weightx = 0.62;
        body.add(left, gc);
        gc.gridx = 1; gc.weightx = 0.38; gc.insets = new Insets(0, 0, 0, 0);
        body.add(right, gc);

        add(body, BorderLayout.CENTER);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(UITheme.FONT_SMALL);
        l.setForeground(UITheme.GREY_TEXT);
        return l;
    }

    @Override
    public void onShow() {
        Equipment eq = frame.selectedEquipment;
        if (eq == null) eq = DummyData.equipmentList().get(0);
        summaryTitle.setText(eq.name);
        summaryPrice.setText("6 days \u00d7 \u20B9" + (int) eq.pricePerDay + "/day  +  \u20B9" + (int) eq.deposit + " deposit");
        double total = eq.pricePerDay * 6 + eq.deposit;
        totalLabel.setText("Total Payable: \u20B9" + (int) total);
    }
}
