# MEDCARE — Java Swing GUI Prototype

Online Medical Equipment Rental System
Prepared by: Mayank Aggarwal (PIET25CA030 / 25EPTCA029 / Section F2)
Submitted to: Vibhav Dadhich

## What this is

A working Java Swing desktop GUI prototype implementing every major screen
described in the project's SRS, including the project's Unique Selling
Proposition (USP): a **QR-Verified Digital Hygiene Certificate** shown on
the Equipment Detail screen.

## Folder structure

```
medcare_gui/
├── src/medcare/            Java source code (package "medcare")
│   ├── MainFrame.java       Main window, CardLayout navigation
│   ├── ScreenshotRunner.java  Dev utility used to generate screenshots/
│   ├── model/                Equipment.java, Booking.java (data classes)
│   ├── ui/                   One class per screen (Login, Register,
│   │                         Dashboard, Catalogue, Equipment Detail,
│   │                         Hygiene Certificate dialog (USP), Booking
│   │                         Form, Payment, My Bookings/Tracking, Admin
│   │                         Dashboard, Vendor Dashboard, Reports)
│   └── util/                 UITheme.java (colours/fonts), DummyData.java
├── resources/
│   └── qr_hygiene.png        Sample QR code used by the Hygiene Certificate
├── screenshots/              12 PNG screenshots, one per screen
└── README.md                 This file
```

## How to run on Windows (easiest way)

1. Make sure a JDK is installed (Java 11+). If you're not sure, download
   Eclipse Temurin from https://adoptium.net/ and install it — tick
   "Add to PATH" during setup.
2. Double-click **`run.bat`** in this folder.
   - First run: it compiles everything into an `out\` folder, then launches the app.
   - It checks for Java and gives you a clear message if it's missing.
3. After the first successful build, you can use **`run_quick.bat`** to
   relaunch instantly without recompiling (useful if you're just demoing
   the app repeatedly and haven't changed any code).

If you'd rather run it from the command line yourself (Windows, macOS or
Linux), see the "How to compile and run" section below.

## How to compile and run (command line, any OS)

Requires JDK 17 or later.

```bash
cd medcare_gui
mkdir -p out
javac -d out -encoding UTF-8 $(find src -name "*.java")
java -cp out medcare.MainFrame
```

A window titled "MEDCARE - Online Medical Equipment Rental System" will
open on the Login screen. Use the "Login as" dropdown to explore the
Customer, Vendor or Administrator views, or click "Create an account" to
see the Register screen.

To view the project's USP in action: Login as Customer → click
"View Details" on any equipment card → click
"✓ View Hygiene Certificate (USP)".

## Regenerating the screenshots

`ScreenshotRunner` is a small developer utility (not part of the shipped
app) that opens every screen in turn and saves a PNG using
`java.awt.Robot`. It needs a real (or virtual) display:

```bash
Xvfb :99 -screen 0 1280x900x24 &
export DISPLAY=:99
java -cp out medcare.ScreenshotRunner screenshots
```

## Notes

- All data shown (equipment, bookings, vendors) is sample/dummy data
  defined in `util/DummyData.java`, wired up for demonstration purposes.
- The QR code shown in the Hygiene Certificate dialog is a real,
  scannable QR code (generated with Python's `qrcode` library) encoding
  a sample certificate ID, so it can be scanned with any phone camera
  during a live demo.
- This prototype maps directly onto the functional requirements in the
  SRS: e.g. the Hygiene Certificate dialog implements FR-HYG-01 to
  FR-HYG-05, the Booking Form implements FR-BOOK-01 to FR-BOOK-05, etc.
