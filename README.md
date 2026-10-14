# Java Selenium Test Automation Framework

A Maven-based test automation starter framework built with **Java 21, Selenium WebDriver 4, TestNG, REST Assured, Jackson, and Log4j2**. It demonstrates reusable UI automation architecture, environment-based configuration, shared utilities, test data models, and API testing patterns.

> **Status:** This is a starter framework, not a fully production-validated solution. Review sample tests, test data, browser support, and reporting integration before adopting it in a production pipeline.

## Technology Stack

- **Language:** Java 21
- **Build:** Maven 3.9+
- **UI automation:** Selenium WebDriver
- **Test runner:** TestNG
- **API automation:** REST Assured (for API tests that use it)
- **JSON mapping:** Jackson
- **Logging:** Log4j2
- **Design:** Page Object Model (POM), reusable UI components, factory/manager pattern

## Prerequisites

- JDK 21 configured in `JAVA_HOME`
- Maven 3.9+ configured in `PATH`
- Chrome, Firefox, or Edge installed, depending on the configured browser
- Network access for the first Maven dependency download
- Access to the application/API under test

Check your installation:

```bash
java -version
mvn -version
```

## Quick Start

Run the configured test suite:

```bash
mvn clean test
```

Choose the browser and headless mode:

```bash
mvn clean test -Dbrowser=chrome -Dheadless=true
```

Choose an environment:

```bash
mvn clean test -Denv=qa
mvn clean test -Denv=staging
```

Run a suite profile if the matching Maven profiles are defined in your current `pom.xml`:

```bash
mvn clean test -Psmoke
mvn clean test -Pregression
```

> **Note:** Maven properties and profiles must match those defined in the checked-in `pom.xml`. If a command is not recognised or has no effect, check `pom.xml`, `ConfigReader`, and the TestNG suite configuration.

## Framework Architecture

```text
src/main/java/com/framework/
├── driver/          # DriverFactory, DriverManager
├── pages/           # BasePage and application page objects
├── components/      # Reusable UI components
├── utils/           # Wait, screenshot, and JavaScript utilities
├── config/          # ConfigReader
├── constants/       # Framework constants
├── exceptions/      # Framework-specific exceptions
└── models/          # POJOs and test-data models

src/test/java/com/tests/
├── base/            # BaseTest and test lifecycle support
├── smoke/           # Fast, high-priority checks
├── regression/      # Broader UI regression tests
├── dataproviders/   # TestNG data providers
└── api/             # API tests and endpoint checks

src/test/resources/
├── config/          # Base and environment-specific properties
├── testdata/        # JSON and other sample test data
├── schemas/         # Example response schema
├── testng.xml       # TestNG suite configuration
└── log4j2.xml       # Logging configuration
```

The exact files and resource locations can vary by revision. Use the actual package names and paths in the repository if they differ from this overview.

## Main Components

| Component | Responsibility |
|---|---|
| `DriverFactory` | Creates and configures browser driver instances. |
| `DriverManager` | Manages access to the current thread's WebDriver. |
| `BaseTest` | Provides shared test setup and teardown. |
| `BasePage` | Encapsulates common page actions and wait behaviour. |
| Page objects | Keep page-specific locators and interactions together. |
| UI components | Encapsulate reusable page sections such as headers and navigation. |
| `ConfigReader` | Loads environment configuration and runtime overrides. |
| Utilities | Provide common wait, screenshot, and JavaScript operations. |
| TestNG support | Supports suite execution, listeners, data providers, and retry examples. |
| Models | Represent test data and API request/response payloads. |

## Configuration

Configuration is stored in properties files under the project's configuration resources. The framework supports environment selection through `-Denv=...`; verify the exact property-loading order in `ConfigReader.java`.

Example configuration (replace placeholder values with your authorised test environment):

```properties
base.url=https://your-test-application.example
api.base.url=https://jsonplaceholder.typicode.com
api.user.create.path=/posts
```

The API properties above are for a JSONPlaceholder practice test. Add them to the properties file that your current `ConfigReader` actually loads. A missing property will cause a configuration error.

Keep passwords, API tokens, and other secrets out of source control. Use environment variables or your CI/CD secret store instead.

## UI Automation

The sample UI flow uses the public SauceDemo training application. Replace the URL, test users, test data, and selectors with values appropriate for your authorised test environment.

Framework practices demonstrated include:

- Page Object Model and reusable page components
- Explicit waits for browser interactions
- A zero implicit-wait strategy
- Thread-local driver management for isolated test sessions
- Failure logging and screenshot support where configured

Prefer stable selectors such as `data-testid` when the application provides them. Avoid fixed sleeps and avoid exception handling that masks genuine application defects.

## API Automation

API tests can use REST Assured for HTTP requests and Jackson for JSON serialization/deserialization.

The following public practice configuration is suitable for learning:

```properties
api.base.url=https://jsonplaceholder.typicode.com
api.user.create.path=/posts
```

JSONPlaceholder is a fake testing API. Its POST endpoint simulates resource creation and does **not** permanently store the submitted resource. Match the request POJO and assertions to the selected endpoint's contract; for `/posts`, fields such as `userId`, `title`, and `body` are appropriate.

Typical API test flow:

1. Create a request model (POJO).
2. Serialize the object to JSON, or pass the object to REST Assured.
3. Send the request with the required content type and headers.
4. Assert the HTTP status and relevant response fields.
5. Deserialize the response JSON into a model when useful.
6. Avoid logging sensitive request or response data.

## Test Data and Test Groups

TestNG groups such as `smoke` and `regression` can be used to organise tests. The actual selected tests depend on the annotations, `testng.xml`, Maven Surefire configuration, and any Maven profiles defined in `pom.xml`.

Keep test data separate from page-object code. For parallel tests, avoid sharing mutable objects or test data between test methods.

## Reports, Logs, and Screenshots

The framework can produce several types of test evidence. Each has a different purpose.

| Output | Purpose | Location |
|---|---|---|
| TestNG/Maven Surefire | Test totals, status, failures and stack traces | `target/surefire-reports/` |
| Log4j2 logs | Execution diagnostics and framework messages | `target/logs/` if configured |
| Failure screenshots | Browser state captured at failure time | `target/screenshots/` if configured |
| Allure HTML report | Rich report with tests, statuses, steps, labels and attachments | `target/site/allure-maven-plugin/` after generation |

**Important:** Allure is an optional integration. The commands below work only after its dependency and Maven plugin have been added to `pom.xml`. Running `mvn clean` removes the previous `target` directory and all reports/results in it.

### 1. TestNG / Maven Surefire report

Run the tests:

```bash
mvn clean test
```

Inspect:

```text
target/surefire-reports/
```

Depending on your Maven Surefire and TestNG configuration, this directory can contain HTML summaries, `TEST-*.xml` files, and text reports with failure details. XML and text result files are the baseline output; exact HTML filenames vary by plugin configuration.

### 2. Log4j2 logs

After execution, inspect `target/logs/` if that output path is configured in `log4j2.xml`. Review messages around the failing test's timestamp and follow the exception/stack trace. The configured appender determines the exact directory and filename. If no file appears, confirm that the Log4j2 configuration is on the test classpath and that framework code writes log messages.

### 3. Failure screenshots

Screenshots are saved only if the screenshot utility is called and the TestNG listener is registered/configured to capture failures.

Expected directory (if configured):

```text
target/screenshots/
```

Verify that the listener is registered in `testng.xml`, with `@Listeners`, or by the project's supported listener mechanism; that the WebDriver is still available when capture occurs; and that the screenshot utility creates the destination directory. For parallel tests, use unique filenames so tests do not overwrite one another.

### 4. Allure HTML report (recommended for rich reporting)

Allure has two stages:

1. **Test execution** writes raw results to `target/allure-results/`.
2. **Report generation** turns those raw results into an HTML report.

#### Step 1 — Add the Allure TestNG dependency

Add the version property inside the existing `<properties>` section and the dependency inside the existing `<dependencies>` section in `pom.xml`. Do not create duplicate sections. Check the selected versions against your organisation's dependency policy.

```xml
<properties>
    <!-- Keep existing properties; add only if not already present. -->
    <allure.version>2.29.1</allure.version>
</properties>

<dependencies>
    <!-- Keep existing dependencies; add only if missing. -->
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-testng</artifactId>
        <version>${allure.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

If your project manages dependency versions through a parent POM or BOM, follow that convention rather than duplicating version management.

#### Step 2 — Add the Allure Maven plugin

Inside the existing `<build><plugins>` section of `pom.xml`, add:

```xml
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.15.0</version>
</plugin>
```

Keep the existing Maven Surefire plugin and other build plugins. This plugin enables the `allure:report` and `allure:serve` Maven goals.

#### Step 3 — Run the tests

```bash
mvn clean test
```

When the Allure TestNG adapter is active, the raw results should be generated under:

```text
target/allure-results/
```

The directory normally contains result/container JSON files and any attachments. If it is missing, check the dependency, test execution output, and adapter configuration.

#### Step 4 — Generate the HTML report

**Option A — Generate static HTML files:**

```bash
mvn allure:report
```

If Maven cannot resolve the `allure` prefix, use the fully qualified plugin command:

```bash
mvn io.qameta.allure:allure-maven:2.15.0:report
```

The report is typically generated at:

```text
target/site/allure-maven-plugin/
```

Open the generated `index.html` in a browser.

**Option B — Generate and serve the report locally (easiest):**

```bash
mvn allure:serve
```

If Maven cannot resolve the `allure` plugin prefix (for example, it reports that the prefix cannot be found), invoke the plugin using its fully qualified coordinates instead:

```bash
mvn io.qameta.allure:allure-maven:2.15.0:serve
```

The fully qualified form tells Maven exactly which plugin and version to run, so it does not need to resolve the short `allure` prefix. The plugin must still be downloadable from your configured Maven repositories.

This goal generates a report from the available results and starts a local server, usually opening the report in your default browser. Stop the server with `Ctrl+C`.

**Always run tests before generating a report.** Do not run `mvn clean` between test execution and report generation because it removes `target/allure-results`.

#### Step 5 — Read the Allure report

Depending on the Allure version and metadata provided by the tests, the report can show:

- **Overview:** totals, pass/fail/broken/skipped statuses, and execution duration.
- **Suites:** tests grouped by suite/class.
- **Behaviours:** feature/story grouping when labels or annotations are added.
- **Failures:** assertion messages, exceptions, and stack traces.
- **Attachments:** screenshots, logs, and other evidence explicitly attached by the code.
- **Trends:** historical results, if previous results are retained and supplied between runs.

#### Step 6 — Add readable test names and descriptions (optional)

Add Allure annotations to test classes or methods to make reports more meaningful:

```java
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Feature("Authentication")
public class LoginTest {

    @Test
    @Story("Valid user login")
    @Description("Verify that a valid user can sign in successfully.")
    public void validUserCanLogin() {
        // Keep the existing test implementation here.
    }
}
```

You can also use Allure steps to show meaningful actions in a test. Do not put passwords, access tokens, or sensitive customer data in descriptions, labels, logs, or attachments.

### 5. Attach Selenium screenshots to Allure

Saving an image under `target/screenshots/` does not automatically embed it in Allure. The listener/test must attach the image to the Allure lifecycle. The following helper is an example; connect it to your existing TestNG failure listener and use the real driver getter from your `DriverManager`.

```java
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public final class AllureAttachments {

    private AllureAttachments() {
    }

    public static void attachScreenshot(WebDriver driver, String name) {
        if (driver == null) {
            return;
        }

        byte[] screenshot =
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                name,
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );
    }
}
```

Call `AllureAttachments.attachScreenshot(driver, "Failure screenshot")` from the failure callback in your existing TestNG listener, while the browser is still open. If the screenshot is taken after the driver is closed, capture will fail. This helper is not automatically wired into the framework merely by adding the class.

### Everyday Allure workflow

```bash
# Run tests and produce raw Allure results
mvn clean test

# Generate and open the report locally (plugin prefix)
mvn allure:serve

# Alternative: fully qualified plugin coordinates
mvn io.qameta.allure:allure-maven:2.15.0:serve
```

For CI/CD, archive `target/allure-results/` as a build artifact and generate/publish the HTML report in a later pipeline step. Preserve previous result data if you want trend charts across builds.

## Troubleshooting

**`Missing config key: api.base.url`**
- Add `api.base.url` to the properties file loaded for the selected environment.
- Confirm the `-Denv` value and the paths used by `ConfigReader`.

**`Unsupported browser` or an empty browser value**
- Check the browser defaults and system-property configuration in `pom.xml`.
- Confirm the browser name is supported by `DriverFactory`.

**Tests pass locally but fail in headless mode**
- Check viewport-dependent behaviour, browser setup, timing, and environment differences.
- Prefer explicit waits and reliable selectors rather than adding fixed sleeps.

**SLF4J provider warning**
- This warning is separate from a missing configuration property. Review the logging dependencies used by REST Assured and the framework before adding another logging provider, to avoid conflicting bindings.

## Before Production Use

- Replace sample URLs, accounts, and test data.
- Verify configuration precedence and secret management.
- Validate browser and parallel execution behaviour in your CI environment.
- Review dependencies for your organisation's security and licensing requirements.
- Add and validate the approved report integration (for example, Allure); documentation alone does not enable it.
- Replace demo API assertions with checks against the real API contract.

## License

Add the license and usage terms applicable to your project.
