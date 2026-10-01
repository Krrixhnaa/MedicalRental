package medcare.ui;

import medcare.MainFrame;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    public LoginPanel(MainFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(UITheme.GREY_BG);

        JPanel card = UITheme.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(420, 480));
        card.setBackground(Color.WHITE);

        JLabel logo = new JLabel("\u2295 MEDCARE");
        logo.setFont(new Font("SansSerif", Font.BOLD, 28));
        logo.setForeground(UITheme.NAVY);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tagline = new JLabel("Online Medical Equipment Rental System");
        tagline.setFont(UITheme.FONT_SMALL);
        tagline.setForeground(UITheme.GREY_TEXT);
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Sign in to your account");
        title.setFont(UITheme.FONT_HEADING);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(22, 0, 14, 0));

        JTextField email = new JTextField("mayank.aggarwal@example.com");
        JPasswordField password = new JPasswordField("••••••••");

        JComboBox<String> role = new JComboBox<>(new String[]{"Customer / Caregiver", "Vendor / Supplier", "Administrator"});

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 10));
        form.setOpaque(false);
        form.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.setBorder(BorderFactory.createEmptyBorder(4, 30, 4, 30));
        form.add(labeled("Login as", role));
        form.add(labeled("Email / Phone Number", email));
        form.add(labeled("Password", password));

        JLabel forgot = new JLabel("Forgot Password?");
        forgot.setForeground(UITheme.NAVY);
        forgot.setFont(UITheme.FONT_SMALL);
        forgot.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgot.setBorder(BorderFactory.createEmptyBorder(6, 0, 14, 0));

        JButton loginBtn = UITheme.primaryButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(340, 40));
        loginBtn.addActionListener(e -> {
            String selected = (String) role.getSelectedItem();
            if (selected.startsWith("Administrator")) frame.navigate(MainFrame.ADMIN_DASHBOARD);
            else if (selected.startsWith("Vendor")) frame.navigate(MainFrame.VENDOR_DASHBOARD);
            else frame.navigate(MainFrame.CUSTOMER_DASHBOARD);
        });

        JPanel registerRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        registerRow.setOpaque(false);
        registerRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel noAcc = new JLabel("New here?");
        noAcc.setFont(UITheme.FONT_SMALL);
        JButton toRegister = new JButton("Create an account");
        toRegister.setFont(UITheme.FONT_SMALL);
        toRegister.setBorderPainted(false);
        toRegister.setContentAreaFilled(false);
        toRegister.setForeground(UITheme.NAVY);
        toRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toRegister.addActionListener(e -> frame.navigate(MainFrame.REGISTER));
        registerRow.add(noAcc);
        registerRow.add(toRegister);

        JLabel usp = new JLabel("<html><center><b>USP:</b> Every rental includes a QR-Verified<br>Digital Hygiene Certificate</center></html>", SwingConstants.CENTER);
        usp.setFont(UITheme.FONT_SMALL);
        usp.setForeground(UITheme.GREEN_DARK);
        usp.setAlignmentX(Component.CENTER_ALIGNMENT);
        usp.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        card.add(Box.createVerticalStrut(28));
        card.add(logo);
        card.add(tagline);
        card.add(title);
        card.add(form);
        card.add(forgot);
        card.add(loginBtn);
        card.add(Box.createVerticalStrut(10));
        card.add(registerRow);
        card.add(usp);

        add(card);
    }

    private JPanel labeled(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        JLabel l = new JLabel(label);
        l.setFont(UITheme.FONT_SMALL);
        l.setForeground(UITheme.GREY_TEXT);
        p.add(l, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }
}
