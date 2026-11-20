@echo off
set JAVA_RUNNER="C:\Program Files\Java\jdk-17\bin\java.exe"
if not exist %JAVA_RUNNER% set JAVA_RUNNER=java

%JAVA_RUNNER% -cp "bin;lib\sqlite-jdbc.jar" com.apartment.Main
pause
