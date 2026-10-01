package medcare;

import medcare.ui.*;
import medcare.model.Equipment;
import medcare.util.DummyData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Developer/test utility (NOT part of the shipped application) used to
 * generate the screenshot set for the project report. It instantiates
 * each screen inside a real JFrame (rendered via the Xvfb virtual
 * display during headless CI/demo capture) and saves a PNG using
 * java.awt.Robot.
 *
 * Usage:  java medcare.ScreenshotRunner <output-directory>
 */
public class ScreenshotRunner {

    public static void main(String[] args) throws Exception {
        String outDir = args.length > 0 ? args[0] : "screenshots";
        new File(outDir).mkdirs();
        Robot robot = new Robot();

        capture(outDir, "01_login", () -> {
            JFrame f = baseFrame("MEDCARE - Login");
            f.setContentPane(new LoginPanel(dummyFrame()));
            return f;
        }, robot);

        capture(outDir, "02_register", () -> {
            JFrame f = baseFrame("MEDCARE - Register");
            f.setContentPane(new RegisterPanel(dummyFrame()));
            return f;
        }, robot);

        capture(outDir, "03_customer_dashboard", () -> {
            JFrame f = baseFrame("MEDCARE - Customer Dashboard");
            f.setContentPane(new CustomerDashboardPanel(dummyFrame()));
            return f;
        }, robot);

        capture(outDir, "04_equipment_catalogue", () -> {
            JFrame f = baseFrame("MEDCARE - Equipment Catalogue");
            f.setContentPane(new EquipmentCataloguePanel(dummyFrame()));
            return f;
        }, robot);

        capture(outDir, "05_equipment_detail", () -> {
            JFrame f = baseFrame("MEDCARE - Equipment Detail");
            MainFrame mf = dummyFrame();
            mf.selectedEquipment = DummyData.equipmentList().get(1);
            EquipmentDetailPanel p = new EquipmentDetailPanel(mf);
            f.setContentPane(p);
            return f;
        }, robot);

        captureHygieneCertificate(outDir, robot);

        capture(outDir, "07_booking_form", () -> {
            JFrame f = baseFrame("MEDCARE - Booking Form");
            MainFrame mf = dummyFrame();
            mf.selectedEquipment = DummyData.equipmentList().get(1);
            f.setContentPane(new BookingFormPanel(mf));
            return f;
        }, robot);

        capture(outDir, "08_payment", () -> {
            JFrame f = baseFrame("MEDCARE - Payment");
            f.setContentPane(new PaymentPanel(dummyFrame()));
            return f;
        }, robot);

        capture(outDir, "09_my_bookings_tracking", () -> {
            JFrame f = baseFrame("MEDCARE - My Bookings");
            f.setContentPane(new MyBookingsPanel(dummyFrame()));
            return f;
        }, robot);

        capture(outDir, "10_admin_dashboard", () -> {
            JFrame f = baseFrame("MEDCARE - Admin Dashboard");
            f.setContentPane(new AdminDashboardPanel(dummyFrame()));
            return f;
        }, robot);

        capture(outDir, "11_vendor_dashboard", () -> {
            JFrame f = baseFrame("MEDCARE - Vendor Dashboard");
            f.setContentPane(new VendorDashboardPanel(dummyFrame()));
            return f;
        }, robot);

        capture(outDir, "12_reports_analytics", () -> {
            JFrame f = baseFrame("MEDCARE - Reports & Analytics");
            f.setContentPane(new ReportsPanel(dummyFrame()));
            return f;
        }, robot);

        capture(outDir, "13_profile", () -> {
            JFrame f = baseFrame("MEDCARE - My Profile");
            f.setContentPane(new ProfilePanel(dummyFrame()));
            return f;
        }, robot);

        System.out.println("All screenshots captured to: " + outDir);
        System.exit(0);
    }

    private static MainFrame dummyFrame() {
        // A MainFrame instance purely to satisfy navigation references;
        // it is never shown on screen during screenshot capture.
        return new MainFrame();
    }

    private static JFrame baseFrame(String title) {
        JFrame f = new JFrame(title);
        f.setUndecorated(false);
        f.setSize(1180, 760);
        f.setLocation(40, 40);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        return f;
    }

    interface FrameSupplier { JFrame get(); }

    /** Special case: the Hygiene Certificate is a modal JDialog, not a JPanel screen,
     *  so it needs its own capture routine that grabs the dialog's own bounds. */
    private static void captureHygieneCertificate(String outDir, Robot robot) throws Exception {
        JFrame parent = baseFrame("MEDCARE - Equipment Detail (behind dialog)");
        MainFrame mf = dummyFrame();
        mf.selectedEquipment = DummyData.equipmentList().get(0);
        parent.setContentPane(new EquipmentDetailPanel(mf));
        parent.setVisible(true);
        Thread.sleep(300);

        Equipment eq = DummyData.equipmentList().get(0);
        final HygieneCertificateDialog[] holder = new HygieneCertificateDialog[1];
        SwingUtilities.invokeAndWait(() -> {
            HygieneCertificateDialog dlg = new HygieneCertificateDialog(parent, eq);
            dlg.setModalityType(Dialog.ModalityType.MODELESS);
            holder[0] = dlg;
            dlg.setVisible(true);
        });
        Thread.sleep(500);
        HygieneCertificateDialog dlg = holder[0];
        Rectangle bounds = dlg.getBounds();
        BufferedImage img = robot.createScreenCapture(bounds);
        javax.imageio.ImageIO.write(img, "png", new File(outDir, "06_hygiene_certificate_USP.png"));
        System.out.println("Captured 06_hygiene_certificate_USP");
        dlg.dispose();
        parent.dispose();
    }

    private static void capture(String outDir, String name, FrameSupplier supplier, Robot robot) throws Exception {
        JFrame f = supplier.get();
        f.setVisible(true);
        Thread.sleep(400);
        SwingUtilities.invokeAndWait(() -> { f.revalidate(); f.repaint(); });
        Thread.sleep(300);
        Rectangle bounds = f.getBounds();
        BufferedImage img = robot.createScreenCapture(bounds);
        javax.imageio.ImageIO.write(img, "png", new File(outDir, name + ".png"));
        System.out.println("Captured " + name);
        Thread.sleep(150);
        f.dispose();
    }
}
