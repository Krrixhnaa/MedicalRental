package medcare.ui;

import medcare.model.Equipment;
import medcare.util.UITheme;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * USP FEATURE: QR-Verified Digital Hygiene Certificate.
 * Shows the sanitisation history of a specific equipment unit so the
 * customer can independently verify hygiene compliance before accepting
 * delivery. This directly implements FR-HYG-01/02/03 from the SRS.
 */
public class HygieneCertificateDialog extends JDialog {

    public HygieneCertificateDialog(Frame owner, Equipment eq) {
        super(owner, "Digital Hygiene Certificate", true);
        if (eq == null) eq = medcare.util.DummyData.equipmentList().get(0);
        setSize(480, 560);
        setLocationRelativeTo(owner);
        setResizable(false);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel header = new JLabel("\uD83C\uDFE5 MEDCARE Sanitisation Passport");
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.setForeground(UITheme.NAVY);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel verified = UITheme.pill("\u2713 QR-VERIFIED CERTIFICATE", UITheme.LIGHT_GREEN, UITheme.GREEN_DARK);
        verified.setAlignmentX(Component.CENTER_ALIGNMENT);
        verified.setFont(UITheme.FONT_SMALL.deriveFont(Font.BOLD));

        // QR code image.
        // Loaded robustly so it works however the app is launched:
        //  1) as a classpath resource (works when resources/ is copied next
        //     to the compiled .class files - this is what run.bat does), or
        //  2) as a plain file relative to the current working directory
        //     (works when running "java -cp out medcare.MainFrame" from the
        //     project root).
        JLabel qrLabel = new JLabel();
        qrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        qrLabel.setBorder(BorderFactory.createEmptyBorder(16, 0, 12, 0));
        ImageIcon icon = loadQrIcon();
        if (icon != null) {
            qrLabel.setIcon(icon);
        } else {
            qrLabel.setText("[QR CODE — resources/qr_hygiene.png not found]");
        }

        JLabel scanHint = new JLabel("Scan with any QR reader to verify this certificate on medcare.in/verify");
        scanHint.setFont(UITheme.FONT_SMALL);
        scanHint.setForeground(UITheme.GREY_TEXT);
        scanHint.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel details = new JPanel(new GridLayout(0, 2, 8, 10));
        details.setBackground(UITheme.LIGHT_GOLD);
        details.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.GOLD_DARK, 1),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)));
        details.setAlignmentX(Component.CENTER_ALIGNMENT);
        details.setMaximumSize(new Dimension(2000, 200));

        addRow(details, "Equipment ID:", eq.id);
        addRow(details, "Equipment:", eq.name);
        addRow(details, "Last Sanitised On:", eq.lastSanitizedDate);
        addRow(details, "Sanitised By:", eq.sanitizationTechnician);
        addRow(details, "Method Used:", eq.sanitizationMethod);
        addRow(details, "Condition Grade:", eq.condition);

        JPanel scorePanel = new JPanel();
        scorePanel.setOpaque(false);
        scorePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scorePanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 8, 0));
        Color scoreColor = eq.hygieneScore >= 95 ? UITheme.SUCCESS
                : eq.hygieneScore >= 85 ? UITheme.GOLD_DARK : UITheme.DANGER;
        JLabel scoreLabel = new JLabel("Hygiene Score:  " + eq.hygieneScore + " / 100");
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        scoreLabel.setForeground(scoreColor);
        scorePanel.add(scoreLabel);

        JTextArea disclaimer = new JTextArea(
                "This certificate is generated automatically whenever a vendor marks an item as " +
                "sanitised after inspection (FR-HYG-02). Report a mismatch between this certificate " +
                "and the physical item at 'Report Issue' during delivery confirmation.");
        disclaimer.setFont(UITheme.FONT_SMALL);
        disclaimer.setForeground(UITheme.GREY_TEXT);
        disclaimer.setLineWrap(true);
        disclaimer.setWrapStyleWord(true);
        disclaimer.setEditable(false);
        disclaimer.setOpaque(false);
        disclaimer.setAlignmentX(Component.CENTER_ALIGNMENT);
        disclaimer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton closeBtn = UITheme.primaryButton("Close");
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.addActionListener(e -> dispose());

        main.add(header);
        main.add(Box.createVerticalStrut(6));
        main.add(verified);
        main.add(qrLabel);
        main.add(scanHint);
        main.add(details);
        main.add(scorePanel);
        main.add(disclaimer);
        main.add(Box.createVerticalStrut(6));
        main.add(closeBtn);

        setContentPane(main);
    }

    private void addRow(JPanel p, String label, String value) {
        JLabel l = new JLabel(label);
        l.setFont(UITheme.FONT_SMALL.deriveFont(Font.BOLD));
        l.setForeground(UITheme.GOLD_DARK);
        JLabel v = new JLabel(value);
        v.setFont(UITheme.FONT_BODY);
        p.add(l);
        p.add(v);
    }

    /**
     * Tries, in order: (1) the classpath ("/qr_hygiene.png", present when
     * resources/ was copied next to the .class files), (2) a few relative
     * file-system locations from the current working directory. Returns
     * null if none of them resolve, so the caller can show a text fallback
     * instead of a broken image icon.
     */
    private ImageIcon loadQrIcon() {
        try {
            java.net.URL url = getClass().getResource("/qr_hygiene.png");
            if (url != null) return new ImageIcon(url);
        } catch (Exception ignored) { }

        String[] candidates = {
                "resources/qr_hygiene.png",
                "./resources/qr_hygiene.png",
                "../resources/qr_hygiene.png",
                "medcare_gui/resources/qr_hygiene.png",
        };
        for (String path : candidates) {
            File f = new File(path);
            if (f.isFile()) return new ImageIcon(f.getAbsolutePath());
        }
        return null;
    }
}
