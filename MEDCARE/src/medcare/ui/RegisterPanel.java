package medcare.ui;

import medcare.MainFrame;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {

    public RegisterPanel(MainFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(UITheme.GREY_BG);

        JPanel card = UITheme.card();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(460, 560));

        JLabel title = UITheme.pageTitle("Create your MEDCARE account");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sub = new JLabel("Register as a customer, caregiver or equipment vendor");
        sub.setFont(UITheme.FONT_SMALL);
        sub.setForeground(UITheme.GREY_TEXT);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub.setBorder(BorderFactory.createEmptyBorder(4, 0, 16, 0));

        JTextField name = new JTextField("Mayank Aggarwal");
        JTextField emailF = new JTextField("mayank.aggarwal@example.com");
        JTextField phone = new JTextField("+91 98xxxxxx30");
        JPasswordField pwd = new JPasswordField("••••••••");
        JPasswordField pwd2 = new JPasswordField("••••••••");
        JComboBox<String> type = new JComboBox<>(new String[]{"Customer / Caregiver", "Vendor / Supplier"});
        JTextArea addr = new JTextArea("221B, Vaishali Nagar, Jaipur, Rajasthan - 302021", 2, 20);
        addr.setLineWrap(true);
        addr.setFont(UITheme.FONT_BODY);

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 8));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));
        form.add(labeled("Register as", type));
        form.add(labeled("Full Name", name));
        form.add(labeled("Email Address", emailF));
        form.add(labeled("Phone Number", phone));
        form.add(labeled("Address", new JScrollPane(addr)));
        form.add(labeled("Password", pwd));
        form.add(labeled("Confirm Password", pwd2));

        JCheckBox otp = new JCheckBox("I agree to verify my account via OTP sent to my phone/email", true);
        otp.setOpaque(false);
        otp.setFont(UITheme.FONT_SMALL);
        otp.setAlignmentX(Component.CENTER_ALIGNMENT);
        otp.setBorder(BorderFactory.createEmptyBorder(10, 24, 4, 24));

        JButton registerBtn = UITheme.primaryButton("Register & Send OTP");
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setMaximumSize(new Dimension(380, 40));
        registerBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "An OTP has been sent to your registered phone/email.\n(Demo mode — OTP verification skipped.)",
                    "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
            frame.navigate(MainFrame.LOGIN);
        });

        JButton back = new JButton("Already have an account? Login");
        back.setFont(UITheme.FONT_SMALL);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setForeground(UITheme.NAVY);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.addActionListener(e -> frame.navigate(MainFrame.LOGIN));

        card.add(Box.createVerticalStrut(20));
        card.add(title);
        card.add(sub);
        card.add(form);
        card.add(otp);
        card.add(Box.createVerticalStrut(12));
        card.add(registerBtn);
        card.add(Box.createVerticalStrut(6));
        card.add(back);

        JScrollPane scroll = new JScrollPane(card);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        add(scroll);
    }

    private JPanel labeled(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(0, 3));
        p.setOpaque(false);
        JLabel l = new JLabel(label);
        l.setFont(UITheme.FONT_SMALL);
        l.setForeground(UITheme.GREY_TEXT);
        p.add(l, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }
}
