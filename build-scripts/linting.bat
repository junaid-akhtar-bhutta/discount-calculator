@echo off

cd ../
:: Generate checkstyle report

./mvnw checkstyle:checkstyle > linting.log
:: Pause the script execution
pause