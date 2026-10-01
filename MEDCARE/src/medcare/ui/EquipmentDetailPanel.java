package medcare.ui;

import medcare.MainFrame;
import medcare.model.Equipment;
import medcare.util.DummyData;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;

public class EquipmentDetailPanel extends JPanel implements MainFrame.Refreshable {

    private final MainFrame frame;
    private final JLabel nameLabel = new JLabel();
    private final JLabel priceLabel = new JLabel();
    private final JLabel metaLabel = new JLabel();
    private final JLabel ratingLabel = new JLabel();
    private final JLabel statusPill;
    private final JTextArea specs = new JTextArea();
    private final JLabel hygieneBadge = new JLabel();
    private final JLabel iconLabel = new JLabel();

    public EquipmentDetailPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(UITheme.GREY_BG);

        add(new TopNavBar(frame, "Search Equipment", "Mayank Aggarwal (Customer)",
                TopNavBar.item("Home", MainFrame.CUSTOMER_DASHBOARD),
                TopNavBar.item("Search Equipment", MainFrame.CATALOGUE),
                TopNavBar.item("My Bookings", MainFrame.MY_BOOKINGS),
                TopNavBar.item("Profile", MainFrame.PROFILE)), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(20, 0));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Left: image + gallery placeholder
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(340, 100));
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 100));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setOpaque(true);
        iconLabel.setBackground(UITheme.LIGHT_BLUE);
        iconLabel.setPreferredSize(new Dimension(320, 260));
        iconLabel.setMaximumSize(new Dimension(320, 260));
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(iconLabel);
        left.add(Box.createVerticalStrut(10));

        JButton hygieneBtn = UITheme.successButton("\u2713  View Hygiene Certificate (USP)");
        hygieneBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        hygieneBtn.setMaximumSize(new Dimension(320, 40));
        hygieneBtn.addActionListener(e -> new HygieneCertificateDialog(
                (Frame) SwingUtilities.getWindowAncestor(this), frame.selectedEquipment).setVisible(true));
        left.add(hygieneBtn);

        JLabel note = new JLabel("<html><i>Scan the in-app QR code to independently verify this " +
                "certificate before accepting delivery.</i></html>");
        note.setFont(UITheme.FONT_SMALL);
        note.setForeground(UITheme.GREY_TEXT);
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        note.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
        left.add(note);

        // Right: details + booking widget
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);

        nameLabel.setFont(UITheme.FONT_TITLE);
        nameLabel.setForeground(UITheme.NAVY);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel metaRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
        metaRow.setOpaque(false);
        metaRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        metaLabel.setFont(UITheme.FONT_BODY);
        metaLabel.setForeground(UITheme.GREY_TEXT);
        ratingLabel.setFont(UITheme.FONT_BODY);
        ratingLabel.setForeground(UITheme.GOLD_DARK);
        statusPill = UITheme.pill("Available", UITheme.LIGHT_GREEN, UITheme.GREEN_DARK);
        metaRow.add(metaLabel);
        metaRow.add(ratingLabel);
        metaRow.add(statusPill);

        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        priceLabel.setForeground(UITheme.NAVY);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        hygieneBadge.setFont(UITheme.FONT_SUB);
        hygieneBadge.setAlignmentX(Component.LEFT_ALIGNMENT);
        hygieneBadge.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        specs.setEditable(false);
        specs.setFont(UITheme.FONT_BODY);
        specs.setLineWrap(true);
        specs.setWrapStyleWord(true);
        specs.setOpaque(false);
        specs.setAlignmentX(Component.LEFT_ALIGNMENT);
        specs.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));

        JPanel bookingBox = UITheme.card();
        bookingBox.setBackground(Color.WHITE);
        bookingBox.setLayout(new GridBagLayout());
        bookingBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bookingBox.setMaximumSize(new Dimension(2000, 130));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 8, 4, 8);
        gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.WEST;
        bookingBox.add(new JLabel("Rental Start:"), gc);
        gc.gridx = 1;
        bookingBox.add(new JSpinner(new SpinnerDateModel()), gc);
        gc.gridx = 2;
        bookingBox.add(new JLabel("Rental End:"), gc);
        gc.gridx = 3;
        bookingBox.add(new JSpinner(new SpinnerDateModel()), gc);
        gc.gridx = 0; gc.gridy = 1; gc.gridwidth = 4;
        JButton bookBtn = UITheme.primaryButton("Book Now \u2192");
        bookBtn.addActionListener(e -> frame.navigate(MainFrame.BOOKING_FORM));
        bookingBox.add(bookBtn, gc);

        JLabel specsHeading = new JLabel("Specifications & Condition Notes:");
        specsHeading.setFont(UITheme.FONT_SUB);
        specsHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        specsHeading.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        right.add(nameLabel);
        right.add(Box.createVerticalStrut(6));
        right.add(metaRow);
        right.add(priceLabel);
        right.add(hygieneBadge);
        right.add(specsHeading);
        right.add(specs);
        right.add(bookingBox);

        body.add(left, BorderLayout.WEST);
        body.add(right, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

        onShow();
    }

    private String iconFor(String category) {
        if (category.contains("Mobility")) return "\uD83E\uDDBD";
        if (category.contains("Bed")) return "\uD83D\uDECF";
        if (category.contains("Respiratory")) return "\uD83E\uDEC1";
        if (category.contains("Monitor")) return "\uD83D\uDCFA";
        return "\uD83C\uDFE5";
    }

    @Override
    public void onShow() {
        Equipment eq = frame.selectedEquipment;
        if (eq == null) eq = DummyData.equipmentList().get(0);
        nameLabel.setText(eq.name);
        metaLabel.setText(eq.category + "  \u2022  " + eq.vendor + "  \u2022  " + eq.city);
        ratingLabel.setText("\u2605 " + eq.vendorRating + " Vendor Rating");
        priceLabel.setText("\u20B9" + (int) eq.pricePerDay + " / day   (Security Deposit: \u20B9" + (int) eq.deposit + ")");
        iconLabel.setText(iconFor(eq.category));
        int score = eq.hygieneScore;
        Color c = score >= 95 ? UITheme.SUCCESS : score >= 85 ? UITheme.GOLD_DARK : UITheme.DANGER;
        hygieneBadge.setForeground(c);
        hygieneBadge.setText("\u2713 Hygiene Score: " + score + "/100  \u2022  Condition: " + eq.condition);
        specs.setText(eq.name + " is available for short and long-term rental. It is inspected before "
                + "every dispatch and comes with a QR-verified digital hygiene certificate. Suitable for "
                + "home care, post-operative recovery and long-term patient assistance. Free doorstep "
                + "delivery and pickup included within city limits.");
    }
}
