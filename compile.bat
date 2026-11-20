@echo off
echo ===================================================================
echo Compiling Apartment Maintenance & Resident Complaint Management App
echo ===================================================================

if not exist bin mkdir bin

set JAVA_COMPILER="C:\Program Files\Java\jdk-17\bin\javac.exe"
if not exist %JAVA_COMPILER% set JAVA_COMPILER=javac

dir /s /b src\*.java > sources.txt
%JAVA_COMPILER% -cp "lib\sqlite-jdbc.jar" -d bin @sources.txt
del sources.txt

if %ERRORLEVEL% equ 0 (
    echo [SUCCESS] Build completed successfully into bin/ directory!
) else (
    echo [ERROR] Build failed!
)
pause
