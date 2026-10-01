package medcare.ui;

import medcare.MainFrame;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;

/** Simple account/profile screen (Customer). */
public class ProfilePanel extends JPanel {

    public ProfilePanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(UITheme.GREY_BG);
        add(new TopNavBar(frame, "Profile", "Mayank Aggarwal (Customer)",
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

        JLabel title = UITheme.pageTitle("My Profile");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        JTextField name = new JTextField("Mayank Aggarwal");
        JTextField email = new JTextField("mayank.aggarwal@example.com");
        JTextField phone = new JTextField("+91 98xxxxxx30");
        JTextArea addr = new JTextArea("221B, Vaishali Nagar, Jaipur, Rajasthan - 302021", 2, 20);
        addr.setLineWrap(true);
        addr.setFont(UITheme.FONT_BODY);

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 10));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));
        form.add(labeled("Full Name", name));
        form.add(labeled("Email Address", email));
        form.add(labeled("Phone Number", phone));
        form.add(labeled("Address", new JScrollPane(addr)));

        JLabel hygieneNote = new JLabel("<html><b>Avg. Hygiene Score across your rentals:</b> 97/100 "
                + "<span style='color:#2E5B2E'>&#10003;</span></html>");
        hygieneNote.setFont(UITheme.FONT_SMALL);
        hygieneNote.setAlignmentX(Component.CENTER_ALIGNMENT);
        hygieneNote.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 0));

        JButton save = UITheme.primaryButton("Save Changes");
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.setMaximumSize(new Dimension(300, 40));
        save.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Profile updated successfully.", "Saved", JOptionPane.INFORMATION_MESSAGE));

        card.add(title);
        card.add(form);
        card.add(hygieneNote);
        card.add(save);

        wrap.add(card);
        add(wrap, BorderLayout.CENTER);
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
