package medcare;

import medcare.ui.*;
import javax.swing.*;
import java.awt.*;

/**
 * MEDCARE - Online Medical Equipment Rental System
 * Java Swing GUI Prototype
 *
 * MainFrame hosts every screen inside a CardLayout and exposes simple
 * navigate(...) methods that each screen panel calls to move to the
 * next step in its workflow (Login -> Dashboard -> Catalogue -> ... ).
 */
public class MainFrame extends JFrame {

    public static final String LOGIN = "LOGIN";
    public static final String REGISTER = "REGISTER";
    public static final String CUSTOMER_DASHBOARD = "CUSTOMER_DASHBOARD";
    public static final String CATALOGUE = "CATALOGUE";
    public static final String EQUIPMENT_DETAIL = "EQUIPMENT_DETAIL";
    public static final String BOOKING_FORM = "BOOKING_FORM";
    public static final String PAYMENT = "PAYMENT";
    public static final String MY_BOOKINGS = "MY_BOOKINGS";
    public static final String ADMIN_DASHBOARD = "ADMIN_DASHBOARD";
    public static final String VENDOR_DASHBOARD = "VENDOR_DASHBOARD";
    public static final String REPORTS = "REPORTS";
    public static final String PROFILE = "PROFILE";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    // shared state between screens (selected equipment id, etc.)
    public medcare.model.Equipment selectedEquipment;

    public MainFrame() {
        setTitle("MEDCARE - Online Medical Equipment Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 760);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);

        cards.add(new LoginPanel(this), LOGIN);
        cards.add(new RegisterPanel(this), REGISTER);
        cards.add(new CustomerDashboardPanel(this), CUSTOMER_DASHBOARD);
        cards.add(new EquipmentCataloguePanel(this), CATALOGUE);
        cards.add(new EquipmentDetailPanel(this), EQUIPMENT_DETAIL);
        cards.add(new BookingFormPanel(this), BOOKING_FORM);
        cards.add(new PaymentPanel(this), PAYMENT);
        cards.add(new MyBookingsPanel(this), MY_BOOKINGS);
        cards.add(new AdminDashboardPanel(this), ADMIN_DASHBOARD);
        cards.add(new VendorDashboardPanel(this), VENDOR_DASHBOARD);
        cards.add(new ReportsPanel(this), REPORTS);
        cards.add(new ProfilePanel(this), PROFILE);

        setContentPane(cards);
        navigate(LOGIN);
    }

    public void navigate(String screen) {
        cardLayout.show(cards, screen);
        // Refresh dynamic panels each time they are shown
        for (Component c : cards.getComponents()) {
            if (c instanceof Refreshable && c.isVisible()) {
                ((Refreshable) c).onShow();
            }
        }
    }

    /** Implemented by panels that need to refresh their data every time they are navigated to. */
    public interface Refreshable {
        void onShow();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
