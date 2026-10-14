# Java Selenium TestNG Framework — Comprehensive Structure

Maven starter framework following the attached architecture: driver factory/manager, page objects, reusable components, utilities, configuration, constants, listeners/retry, custom exceptions, models/POJOs, smoke/regression/data-provider/API test packages, environment properties, test data, schemas, Log4j 2, screenshots, reports, and CI-friendly Maven commands.

## Prerequisites
- JDK 21 and Maven 3.9+
- Chrome, Firefox or Edge installed
- Network access for first Maven dependency resolution

## Run
```bash
mvn clean test
mvn clean test -Dbrowser=chrome -Dheadless=true
mvn clean test -Denv=qa
mvn clean test -Psmoke
mvn clean test -Pregression
```

The UI example uses the public SauceDemo training application. Replace the URL, users, data, and selectors with your authorised test environment.

## Architecture
```text
src/main/java/com/framework/
├── driver/       DriverFactory, DriverManager
├── pages/        BasePage, LoginPage, HomePage, CheckoutPage
├── components/   HeaderComponent, NavigationComponent
├── utils/        WaitUtils, ScreenshotUtils, JavaScriptUtils
├── config/       ConfigReader
├── constants/    FrameworkConstants
├── listeners/    TestListener, RetryAnalyzer
├── exceptions/   FrameworkException
└── models/       User, TestData

src/test/java/com/tests/
├── base/         BaseTest
├── smoke/        LoginTest, SearchTest
├── regression/   CheckoutTest, PaymentTest
├── dataproviders/TestDataProvider
└── api/          ApiHealthTest (lightweight URL health check)

src/test/resources/
├── testng.xml
├── config/       qa, staging, prod properties
├── testdata/     users.json, checkout-data.json, testdata.xlsx
├── schemas/      response-schema.json
└── log4j2.xml
```

## Design principles
- Page Object Model and reusable page components
- Explicit waits; implicit wait is zero; no fixed sleeps
- ThreadLocal driver for method-level parallelism
- TestNG listener for result logging and failure screenshots
- Centralised environment configuration with system-property overrides
- Secrets must come from environment variables/secret management, never committed
- Review dependency versions against your organisation's security policy before production use

## Reporting
TestNG's native reports are generated under `target/surefire-reports`. Log4j output and failure screenshots are under `target/logs` and `target/screenshots`. Allure/Extent are intentionally not added as dependencies; integrate the reporter approved by your project.
