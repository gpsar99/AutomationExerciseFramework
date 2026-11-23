# Automation Exercise UI Test Automation Framework

A Java-based UI test automation framework for [Automation Exercise](https://automationexercise.com/), built with **Selenium WebDriver**, **TestNG**, and **Maven** using the **Page Object Model (POM)** design pattern.

The project covers 26 automated test scenarios across user registration, authentication, product discovery, cart operations, checkout, and site navigation.

## Technology Stack

| Technology | Version / Purpose |
| --- | --- |
| Java | 17 |
| Selenium WebDriver | 4.27.0 |
| TestNG | 7.10.2 |
| Maven | Dependency management and build tooling |
| Google Chrome / ChromeDriver | Browser automation |
| IntelliJ IDEA | Development and test execution |
| Page Object Model (POM) | Separation of page interactions from test logic |

## Project Structure

```text
AutomationExerciseFramework/
├── pom.xml
├── testng.xml
├── downloads/
└── src/
    ├── main/java/
    │   ├── pages/
    │   │   ├── BasePage.java
    │   │   ├── HomePage.java
    │   │   ├── ProductsPage.java
    │   │   ├── ProductDetailsPage.java
    │   │   ├── CartPage.java
    │   │   ├── SignupLoginPage.java
    │   │   ├── AccountInformationPage.java
    │   │   ├── AccountStatusPage.java
    │   │   ├── ContactUsPage.java
    │   │   ├── TestCasesPage.java
    │   │   ├── CheckoutPage.java
    │   │   ├── PaymentPage.java
    │   │   ├── CategoryPage.java
    │   │   ├── BrandPage.java
    │   │   └── ScrollPage.java
    │   └── components/
    │       ├── SubscriptionComponent.java
    │       └── ProductCartComponent.java
    └── test/java/
        ├── base/
        │   └── BaseTest.java
        └── tests/
            ├── RegisterTests.java
            ├── LoginTests.java
            ├── ContactUsTests.java
            ├── TestCasesTest.java
            ├── ProductTests.java
            ├── SubscriptionTests.java
            ├── CartTests.java
            ├── CategoryTests.java
            ├── BrandTests.java
            └── ScrollTests.java
```

## Test Coverage

The framework contains **26 TestNG test cases** organized into 10 test classes.

| Test Class | Test Cases | Coverage |
| --- | --- | --- |
| `RegisterTests` | TC01, TC05 | Registration and duplicate email validation |
| `LoginTests` | TC02, TC03, TC04 | Successful login, invalid login, and logout |
| `ContactUsTests` | TC06 | Contact Us form |
| `TestCasesTest` | TC07 | Test Cases page |
| `ProductTests` | TC08, TC09, TC20, TC21 | Product details, search, cart persistence after login, and reviews |
| `SubscriptionTests` | TC10, TC11 | Subscription from home and cart pages |
| `CartTests` | TC12–TC17, TC22–TC24 | Cart, quantity, checkout, ordering, removal, recommended items, addresses, and invoice download |
| `CategoryTests` | TC18 | Category navigation |
| `BrandTests` | TC19 | Brand navigation |
| `ScrollTests` | TC25, TC26 | Scroll-to-top behavior |

Scenarios are based on the [Automation Exercise test cases](https://automationexercise.com/test_cases).

## Prerequisites

- JDK 17 installed and configured.
- Maven installed, or use Maven integration in IntelliJ IDEA.
- Google Chrome installed.
- An internet connection to access the test website.
- A valid existing Automation Exercise account for tests that require login.

> Browser and driver compatibility should be checked when Chrome updates. Selenium 4.27.0 may emit a Chrome DevTools Protocol (CDP) compatibility warning with newer Chrome versions.

## Configuration

Some tests require credentials provided through environment variables:

| Variable | Description |
| --- | --- |
| `TEST_EMAIL` | Email address of an existing test account |
| `TEST_PASSWORD` | Password for that account |

**Do not commit credentials to Git.**

### IntelliJ IDEA

1. Open **Run → Edit Configurations**.
2. Select the **TestNG** run configuration that points to `testng.xml`.
3. Locate **Environment variables** (use **Modify options** if the field is hidden).
4. Add `TEST_EMAIL` and `TEST_PASSWORD` with your test account credentials.
5. Click **Apply**, then **OK**.
6. Run the selected suite configuration.

**Important:** IntelliJ uses separate run configurations for individual tests and the complete suite. Set the environment variables in the configuration you actually run.

### Windows PowerShell

To set credentials for the current terminal session:

```powershell
$env:TEST_EMAIL = "your-test-email@example.com"
$env:TEST_PASSWORD = "your-test-password"
```

## Running Tests

### Run the full regression suite in IntelliJ

1. Open the project in IntelliJ IDEA.
2. Select the TestNG configuration referencing the root-level `testng.xml`.
3. Confirm the environment variables are configured.
4. Click **Run**.

The suite uses `parallel="false"` to execute the configured test classes sequentially.

### Run the full regression suite with Maven

From the project root, after setting the required environment variables:

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml
```

This command uses Maven Surefire's suite XML option. If your `pom.xml` defines additional test execution settings, review them before running.

### Run a single test in IntelliJ

Open a test class under `src/test/java/tests/` and click the green **Run** icon next to the desired `@Test` method. Ensure the corresponding run configuration has the required credentials if that test uses them.

## Framework Design

- **Page Objects (`pages/`)** encapsulate locators and page-specific actions.
- **Reusable components (`components/`)** encapsulate interactions shared across pages.
- **`BasePage`** provides shared page interaction utilities.
- **`BaseTest`** manages WebDriver setup, browser-related handling, and teardown.
- **Test classes (`tests/`)** describe the automated scenarios and assertions.
- **`testng.xml`** defines the regression suite and test class execution order.

The framework also includes handling for cookie consent prompts and Google Vignette overlays, which may interfere with UI interactions.

## Test Execution Status

The framework contains 26 automated tests. Individual scenarios have beem passed.

A regression run on **November 23, 2026** reported:

| Result | Count |
| --- | ---: |
| Passed | 26 |
| Failed | 0 |
| Skipped | 0 |
| Configuration failures | 0 |

## Known Issues and Troubleshooting

### Intermittent Selenium timeouts

Dynamic content, navigation delays, and overlays can cause waits for elements to time out. Check the current page URL, visible content, and element locator before changing the timeout.

### Google Vignette overlays

The site may display a Google Vignette overlay during navigation. The framework contains logic to handle this, but overlays can still affect timing and element interaction.

### Chrome DevTools Protocol warning

A warning such as `Unable to find CDP implementation matching 157` may appear with Chrome 157 and Selenium 4.27.0. This warning is not necessarily the cause of a test failure. Review Selenium/Chrome compatibility when updating dependencies.

### SLF4J warning

`Failed to load class "org.slf4j.impl.StaticLoggerBinder"` indicates that no SLF4J logging backend was found. It does not, by itself, mean a test failed.

### Missing test credentials

If a test reports `TEST_EMAIL is not configured` or a similar error, confirm that **both** environment variables are defined in the run configuration used to launch that test or suite.

### Browser session closed

`NoSuchSessionException` or `NoSuchWindowException` can occur if the browser or its active window closes unexpectedly. Inspect setup/teardown behavior and the preceding test output.

## Future Improvements

- Improve stability of navigation and element interaction waits.
- Capture screenshots and browser details on failure.
- Add richer HTML test reporting.
- Isolate test accounts and test data to reduce cross-test dependencies.
- Keep Selenium and Chrome compatibility up to date.
- Integrate regression execution into CI.

## Target Application

[https://automationexercise.com/](https://automationexercise.com/)

This project is intended for learning and demonstrating automated UI testing practices with Java, Selenium, TestNG, Maven, and the Page Object Model.
