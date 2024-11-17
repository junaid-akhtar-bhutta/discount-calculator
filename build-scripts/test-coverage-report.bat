@echo off

cd ../
:: Run tests

./mvnw clean test > test-coverage-report.log

./mvnw jacoco:report >> test-coverage-report2.log

pause