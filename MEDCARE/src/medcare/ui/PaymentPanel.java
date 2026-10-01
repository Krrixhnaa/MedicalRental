package medcare.ui;

import medcare.MainFrame;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;

public class PaymentPanel extends JPanel {

    public PaymentPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(UITheme.GREY_BG);
        add(new TopNavBar(frame, "Search Equipment", "Mayank Aggarwal (Customer)",
                TopNavBar.item("Home", MainFrame.CUSTOMER_DASHBOARD),
                TopNavBar.item("Search Equipment", MainFrame.CATALOGUE),
                TopNavBar.item("My Bookings", MainFrame.MY_BOOKINGS),
                TopNavBar.item("Profile", MainFrame.PROFILE)), BorderLayout.NORTH);

        JPanel wrap = new JPanel(new GridBagLayout());
        wrap.setOpaque(false);

        JPanel card = UITheme.card();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(460, 480));

        JLabel title = UITheme.pageTitle("Payment");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel amount = new JLabel("\u20B9 1,860");
        amount.setFont(new Font("SansSerif", Font.BOLD, 32));
        amount.setForeground(UITheme.NAVY);
        amount.setAlignmentX(Component.CENTER_ALIGNMENT);
        amount.setBorder(BorderFactory.createEmptyBorder(6, 0, 20, 0));

        ButtonGroup group = new ButtonGroup();
        JRadioButton upi = new JRadioButton("UPI (Google Pay / PhonePe / Paytm)", true);
        JRadioButton card1 = new JRadioButton("Credit / Debit Card");
        JRadioButton nb = new JRadioButton("Net Banking");
        for (JRadioButton r : new JRadioButton[]{upi, card1, nb}) {
            r.setOpaque(false);
            r.setFont(UITheme.FONT_BODY);
            r.setAlignmentX(Component.LEFT_ALIGNMENT);
            group.add(r);
        }

        JPanel methodBox = new JPanel();
        methodBox.setLayout(new BoxLayout(methodBox, BoxLayout.Y_AXIS));
        methodBox.setOpaque(false);
        methodBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        methodBox.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        methodBox.add(upi);
        methodBox.add(Box.createVerticalStrut(6));
        methodBox.add(card1);
        methodBox.add(Box.createVerticalStrut(6));
        methodBox.add(nb);

        JTextField upiId = new JTextField("mayank@upi");
        upiId.setAlignmentX(Component.LEFT_ALIGNMENT);
        upiId.setMaximumSize(new Dimension(2000, 32));
        JPanel upiField = new JPanel(new BorderLayout(0, 4));
        upiField.setOpaque(false);
        upiField.setAlignmentX(Component.LEFT_ALIGNMENT);
        upiField.setMaximumSize(new Dimension(2000, 60));
        upiField.setBorder(BorderFactory.createEmptyBorder(16, 30, 10, 30));
        JLabel upiLabel = new JLabel("Enter UPI ID");
        upiLabel.setFont(UITheme.FONT_SMALL);
        upiField.add(upiLabel, BorderLayout.NORTH);
        upiField.add(upiId, BorderLayout.CENTER);

        JLabel secure = new JLabel("\uD83D\uDD12  Payments are processed securely via a PCI-DSS compliant gateway. MEDCARE never stores your card details.");
        secure.setFont(UITheme.FONT_SMALL);
        secure.setForeground(UITheme.GREY_TEXT);
        secure.setAlignmentX(Component.CENTER_ALIGNMENT);
        secure.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        JButton payBtn = UITheme.primaryButton("Pay \u20B9 1,860");
        payBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        payBtn.setMaximumSize(new Dimension(300, 42));
        payBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Payment Successful!\nInvoice #INV-88123 has been e-mailed to you.\nBooking ID: BK-20512",
                    "Payment Confirmed", JOptionPane.INFORMATION_MESSAGE);
            frame.navigate(MainFrame.MY_BOOKINGS);
        });

        card.add(Box.createVerticalStrut(10));
        card.add(title);
        card.add(amount);
        card.add(methodBox);
        card.add(upiField);
        card.add(Box.createVerticalGlue());
        card.add(secure);
        card.add(payBtn);

        wrap.add(card);
        add(wrap, BorderLayout.CENTER);
    }
}
