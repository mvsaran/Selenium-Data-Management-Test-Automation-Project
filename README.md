# Data Management Test Automation Project

## 📋 Project Overview

This is a **Selenium-based Test Automation Framework** that demonstrates automated user registration testing on the ParaBank website using **dynamically generated test data with JavaFaker library**.

### Key Features
- ✅ **Page Object Model (POM)** design pattern
- ✅ **Dynamic test data generation** using JavaFaker
- ✅ **TestNG framework** for test execution
- ✅ **Modular and maintainable** code structure
- ✅ **Smart waiting strategies** for reliable tests
- ✅ **Data-driven approach** with POJO classes

---

## 🗂️ Project Structure

```
DataManagement/
│
├── src/main/java/
│   ├── base/
│   │   └── BaseTest.java           # Base setup for all tests
│   │
│   ├── pages/
│   │   └── RegistrationPage.java  # Page Object for Registration Page
│   │
│   ├── pojo/
│   │   └── UserData.java           # Data model for user information
│   │
│   └── utils/
│       ├── DataGenerator.java      # Faker data generation utility
│       └── LoggerConfig.java       # Logger configuration (optional)
│
├── src/test/java/
│   └── test/
│       └── RegistrationTest.java   # Test cases for registration
│
└── pom.xml                          # Maven dependencies
```

---

## 📦 How Faker Data is Used

### What is JavaFaker?
**JavaFaker** is a library that generates fake but realistic-looking data for testing purposes. Instead of hardcoding test data, Faker creates:
- Random names (first name, last name)
- Random addresses (street, city, state, zip code)
- Random phone numbers
- Random SSNs
- Random usernames and passwords

### Why Use Faker?
1. **Unique Test Data**: Every test run uses different data, avoiding conflicts
2. **Realistic Data**: Data looks like real user information
3. **No Manual Data Creation**: Automated data generation saves time
4. **Better Test Coverage**: Tests multiple data scenarios automatically

---

## 🔧 Detailed Class Explanation

### 1️⃣ **BaseTest.java** (base package)

**Purpose**: Sets up and tears down the browser for each test

```java
@BeforeMethod  // Runs before each test
public void setup() {
    // Initialize Chrome browser
    // Maximize window
}

@AfterMethod   // Runs after each test
public void tearDown() {
    // Close browser
}
```

**Key Responsibilities**:
- Creates WebDriver instance
- Manages browser lifecycle
- Provides driver to child test classes

---

### 2️⃣ **UserData.java** (pojo package)

**Purpose**: Data model to store user registration information

**POJO** = Plain Old Java Object (a simple class with fields, getters, and setters)

```java
public class UserData {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    // ... more fields
    
    // Getters and Setters
}
```

**Why Use POJO?**
- ✅ Organizes related data together
- ✅ Makes code more readable
- ✅ Easy to pass data between methods
- ✅ Follows Object-Oriented Programming principles

---

### 3️⃣ **DataGenerator.java** (utils package)

**Purpose**: Generates fake user data using JavaFaker library

**How It Works**:

```java
public static UserData generateUserData() {
    Faker faker = new Faker();  // Create Faker instance
    
    UserData user = new UserData();
    
    // Generate random data
    user.setFirstName(faker.name().firstName());
    user.setLastName(faker.name().lastName());
    user.setAddress(faker.address().streetAddress());
    user.setCity(faker.address().city());
    user.setState(faker.address().state());
    user.setZipCode(faker.address().zipCode());
    user.setPhone(faker.phoneNumber().phoneNumber());
    user.setSsn(faker.idNumber().ssnValid());
    
    // Generate username from name
    user.setUsername(faker.name().username());
    user.setPassword("SecurePass" + faker.number().digits(3) + "!");
    
    return user;  // Return populated UserData object
}
```

**Faker Methods Used**:
| Faker Method | Example Output |
|--------------|----------------|
| `faker.name().firstName()` | "Lyman" |
| `faker.name().lastName()` | "Ondricka" |
| `faker.address().streetAddress()` | "56171 Carl Gateway" |
| `faker.address().city()` | "Lynchchester" |
| `faker.address().state()` | "Colorado" |
| `faker.address().zipCode()` | "42422" |
| `faker.phoneNumber().phoneNumber()` | "355-401-1841" |
| `faker.idNumber().ssnValid()` | "112-61-9792" |
| `faker.name().username()` | "quincy.wunsch" |

---

### 4️⃣ **RegistrationPage.java** (pages package)

**Purpose**: Page Object Model for the Registration Page

**Page Object Model (POM)**:
- Separates page elements from test logic
- Makes code reusable and maintainable
- Changes to UI only require updates in one place

```java
public class RegistrationPage {
    WebDriver driver;
    
    // Web Elements (Page Locators)
    @FindBy(id = "customer.firstName")
    WebElement firstNameField;
    
    @FindBy(id = "customer.lastName")
    WebElement lastNameField;
    
    // ... more elements
    
    // Constructor
    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    // Page Actions
    public void fillRegistrationForm(UserData user) {
        firstNameField.sendKeys(user.getFirstName());
        lastNameField.sendKeys(user.getLastName());
        // ... fill all fields
    }
    
    public void clickRegister() {
        registerButton.click();
    }
}
```

**Key Concepts**:
- `@FindBy`: Selenium annotation to locate web elements
- `PageFactory.initElements()`: Initializes all web elements
- **Methods**: Actions that can be performed on the page

---

### 5️⃣ **RegistrationTest.java** (test package)

**Purpose**: Contains the actual test cases

**Test Flow**:

```
1. Generate fake user data (using DataGenerator)
   ↓
2. Print generated data to console
   ↓
3. Navigate to ParaBank registration page
   ↓
4. Fill registration form (using RegistrationPage)
   ↓
5. Click Register button
   ↓
6. Wait for page to process registration
   ↓
7. Verify registration success
   ↓
8. Assert test result (Pass/Fail)
```

**Code Breakdown**:

```java
@Test
public void testSmartDataRegistration() {
    // STEP 1: Generate test data
    UserData user = DataGenerator.generateUserData();
    
    // STEP 2: Print data for debugging
    System.out.println("First Name: " + user.getFirstName());
    // ... print all fields
    
    // STEP 3: Navigate to page
    driver.get("https://parabank.parasoft.com/parabank/register.htm");
    
    // STEP 4: Create page object
    RegistrationPage regPage = new RegistrationPage(driver);
    
    // STEP 5: Fill form with generated data
    regPage.fillRegistrationForm(user);
    
    // STEP 6: Submit form
    regPage.clickRegister();
    
    // STEP 7: Wait for result
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.urlContains("overview.htm"));
    
    // STEP 8: Verify success
    boolean isSuccess = driver.getCurrentUrl().contains("overview.htm");
    Assert.assertTrue(isSuccess, "Registration should succeed");
}
```

---

## 🚀 How to Run the Project

### Prerequisites
- Java 8 or higher
- Maven
- Chrome browser
- IDE (Eclipse/IntelliJ)

### Steps to Execute

1. **Clone/Open Project**
   ```bash
   Open the project in Eclipse/IntelliJ
   ```

2. **Maven Dependencies** (in pom.xml)
   ```xml
   <dependencies>
       <dependency>
           <groupId>org.seleniumhq.selenium</groupId>
           <artifactId>selenium-java</artifactId>
           <version>4.15.0</version>
       </dependency>
       
       <dependency>
           <groupId>org.testng</groupId>
           <artifactId>testng</artifactId>
           <version>7.8.0</version>
       </dependency>
       
       <dependency>
           <groupId>com.github.javafaker</groupId>
           <artifactId>javafaker</artifactId>
           <version>1.0.2</version>
       </dependency>
   </dependencies>
   ```

3. **Run Test**
   ```
   Right-click on RegistrationTest.java → Run As → TestNG Test
   ```

---

## 📊 Test Output Example

```
===== Test Started: Smart Test Data Registration =====

📋 Generated Test Data:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
First Name    : Lyman
Last Name     : Ondricka
Address       : 56171 Carl Gateway
City          : Lynchchester
State         : Colorado
Zip Code      : 42422
Phone         : 355-401-1841
SSN           : 112-61-9792
Username      : quincy.wunsch
Password      : ezxscj3xnoa
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🌐 Navigated to ParaBank registration page.
✍️ Filling registration form...
✅ Registration form filled successfully with smart data.
🖱️ Clicking Register button...

📊 Registration Result:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✅ Registration completed successfully!
✅ User 'quincy.wunsch' has been registered.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

===== Test Completed: Smart Test Data Registration =====

PASSED: test.RegistrationTest.testSmartDataRegistration
Tests run: 1, Passes: 1, Failures: 0, Skips: 0
```

---

## 🎯 Design Patterns Used

### 1. **Page Object Model (POM)**
- Separates page structure from test logic
- Improves code reusability and maintenance

### 2. **Factory Pattern**
- `DataGenerator.generateUserData()` creates UserData objects
- Centralizes object creation logic

### 3. **Builder Pattern (implicit in UserData)**
- POJO with setters allows flexible object construction

---

## 🔄 Data Flow Diagram

```
┌─────────────────┐
│ RegistrationTest│
│   (Test Class)  │
└────────┬────────┘
         │
         │ 1. Request data
         ▼
┌─────────────────┐
│ DataGenerator   │
│ (Faker Utility) │
└────────┬────────┘
         │
         │ 2. Create UserData with Faker
         ▼
┌─────────────────┐
│   UserData      │
│   (POJO)        │
└────────┬────────┘
         │
         │ 3. Pass UserData
         ▼
┌─────────────────┐
│ RegistrationPage│
│ (Page Object)   │
└────────┬────────┘
         │
         │ 4. Fill form fields
         ▼
┌─────────────────┐
│  ParaBank Web   │
│  Application    │
└─────────────────┘
```

---

## ✅ Benefits of This Framework

1. **Maintainability**: Changes to UI affect only Page Objects
2. **Reusability**: DataGenerator can be used for multiple tests
3. **Scalability**: Easy to add new tests and pages
4. **Readability**: Clear separation of concerns
5. **Reliability**: Dynamic data prevents test data conflicts
6. **Debugging**: Console output shows exactly what data was used

---

## 🎓 Learning Outcomes

By studying this project, you'll understand:
- ✅ How to use JavaFaker for test data generation
- ✅ Page Object Model implementation
- ✅ POJO design pattern
- ✅ TestNG test structure
- ✅ Selenium WebDriver automation
- ✅ Explicit waits and synchronization
- ✅ Modular test framework architecture

---

## 📞 Support

For questions or issues, refer to:
- **Selenium Docs**: https://www.selenium.dev/documentation/
- **TestNG Docs**: https://testng.org/doc/documentation-main.html
- **JavaFaker GitHub**: https://github.com/DiUS/java-faker

---

## 📝 License

This project is for educational purposes.

---

**Author**
Saran Kumar
