@echo off

cd ../
:: Clean and install the Maven project
./mvnw clean install > build.log

:: Pause the script execution
pause