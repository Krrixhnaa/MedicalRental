@echo off
REM Quick-launch script - use this AFTER you've run run.bat at least once
REM (i.e. the "out" folder with compiled classes already exists).

if not exist out\medcare\MainFrame.class (
    echo [INFO] No compiled classes found yet - running full build instead...
    call run.bat
    exit /b
)

java -cp out;resources medcare.MainFrame
if errorlevel 1 pause
