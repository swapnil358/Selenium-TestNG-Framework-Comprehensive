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

- **TestNG/Surefire reports:** `target/surefire-reports`
- **Logs:** `target/logs` (if configured in the current Log4j2 setup)
- **Screenshots:** `target/screenshots` (if generated by the screenshot utility/listener)

Allure and Extent reporting are not assumed to be integrated. Add the reporting tool approved for your project and wire it into the test lifecycle if required.

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
- Integrate the required report publisher (for example, Allure or Extent) if the project needs it.
- Replace demo API assertions with checks against the real API contract.

## License

Add the license and usage terms applicable to your project.
