@echo off
REM ============================================================
REM  MEDCARE - Online Medical Equipment Rental System
REM  Compile-and-run script for Windows.
REM
REM  Just double-click this file (or run it from Command Prompt).
REM  It will:
REM    1. Check that Java (JDK) is installed
REM    2. Compile all source files into the "out" folder
REM    3. Copy the QR code resource next to the compiled classes
REM    4. Launch the application
REM ============================================================

setlocal

echo ================================================
echo  MEDCARE - Java Swing GUI Prototype
echo ================================================
echo.

REM --- 1. Check Java is available -------------------------------------
where javac >nul 2>nul
if errorlevel 1 (
    echo [ERROR] "javac" was not found on your PATH.
    echo.
    echo This project needs a JDK ^(Java Development Kit^), version 11 or
    echo later. Please install one - for example, Eclipse Temurin from:
    echo     https://adoptium.net/
    echo and make sure "Add to PATH" is selected during installation.
    echo.
    pause
    exit /b 1
)

REM --- 2. Compile -------------------------------------------------------
echo Compiling Java source files...
if not exist out mkdir out

REM Build a list of all .java files under src\ and compile them together.
dir /s /b src\*.java > sources.txt
javac -d out -encoding UTF-8 @sources.txt
if errorlevel 1 (
    echo.
    echo [ERROR] Compilation failed - see the messages above.
    del sources.txt
    pause
    exit /b 1
)
del sources.txt
echo Compilation successful.
echo.

REM --- 3. Copy the QR code image next to the compiled classes -----------
REM (lets the app find it via the classpath no matter where you run from)
if exist resources\qr_hygiene.png (
    copy /Y resources\qr_hygiene.png out\qr_hygiene.png >nul
)

REM --- 4. Run -------------------------------------------------------------
echo Launching MEDCARE...
echo.
java -cp out;resources medcare.MainFrame

if errorlevel 1 (
    echo.
    echo [ERROR] The application exited with an error - see the messages above.
    pause
)

endlocal
