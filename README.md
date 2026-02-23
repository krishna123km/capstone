🎨 Paint Store – Capstone Project

End-to-End Quality Engineering Implementation

A complete paint e-commerce web application built using HTML, CSS, and Vanilla JavaScript, with no backend or frameworks.
The project also includes a full Selenium automation test suite implemented using Java, TestNG, Maven, and Page Object Model (POM).

All application data (users, cart, orders) is stored in Browser Local Storage, making this project ideal for frontend QA, automation testing, and CI/CD demonstration.

🚀 Application Features
🔐 User Registration & Authentication

Signup and Login using Email & Password

Credentials stored in Local Storage

Authenticated users redirected to Home page

Logout clears session data

🏠 Home Page

Paint product grid with image, title, price, and rating

Product search by name

Sticky navigation bar:

Home

Cart

Orders

Logout

🧾 Product Details

Large product image

Product title, description, price, rating

Add to Cart functionality

Quantity increases if product already exists in cart

🛒 Cart Management

Cart item list with:

Increase / Decrease quantity

Remove item

Line total calculation

Grand total displayed

Proceed to Checkout button

💳 Checkout & Payment

Order summary

Payment form with:

Name on Card

Card Number

Expiry (MM/YY)

CVV

Billing Address

On successful payment:

Order saved to Order History

Cart cleared

Success message with Order ID, Date, and Total

📦 Order History

Displays all past orders

Order ID, items purchased, total amount, date

Data stored in Local Storage

🧪 Automation Testing Overview
🔧 Testing Tools & Technologies

Java

Selenium WebDriver

TestNG

Maven

Page Object Model (POM)

📂 Automation Framework Structure
src/test/java/
├── base/
│   └── BaseTest.java
├── pages/
│   ├── HomePage.java
│   ├── ProductPage.java
│   ├── CartPage.java
│   ├── CheckoutPage.java
│   └── OrdersPage.java
├── tests/
│   ├── LoginRegistrationTests.java
│   ├── ProductSearchListingTests.java
│   ├── CartManagementTests.java
│   ├── OrderPlacementTests.java
│   ├── PaymentProcessingTests.java
│   └── OrderHistoryTrackingTests.java
✅ Automated Test Modules (60+ Test Cases)
🔐 Login & Registration Tests

Valid signup and login

Invalid credential handling

Session persistence

Logout functionality

Page redirections

🔍 Product Search & Listing Tests

Product grid rendering

Search functionality

Product details navigation

Add to Cart from product page

UI validations

🛒 Cart Management Tests

Empty cart behavior

Add single and multiple products

Quantity increase/decrease

Remove items

Cart persistence

Total price calculation

📦 Order Placement Tests

Checkout page access

Order summary validation

Empty cart checkout restriction

Successful order placement

Order ID generation

Cart clearance after order

💳 Payment Processing Tests

Valid payment submission

Invalid card number handling

Invalid expiry and CVV validation

Required field checks

Transaction success confirmation

📜 Order History & Tracking Tests

Orders page navigation

Empty order history message

Order listing after purchase

Order ID, date, total validation

Multiple order handling

⚙️ Running the Application Locally
Option 1 – Node
npx serve .
Option 2 – Python
python -m http.server 8080
Option 3 – VS Code

Use Live Server and open index.html

🧪 Running Automation Tests
mvn clean test

Tests run using TestNG

Browser: Chrome (via WebDriver)

Reports generated in target/surefire-reports

📁 Project Structure (Frontend)
paint-store/
├── index.html
├── home.html
├── product.html
├── cart.html
├── checkout.html
├── orders.html
├── css/
│   └── style.css
├── js/
│   ├── storage.js
│   ├── data.js
│   ├── auth.js
│   ├── nav.js
│   ├── home.js
│   ├── product.js
│   ├── cart.js
│   ├── checkout.js
│   └── orders.js
└── README.md
🌐 Deployment

Hosted using GitHub Pages

Fully static frontend

No backend dependencies

⚠️ Notes

Passwords are stored in plain text (demo purpose only)

No real payment gateway integration

Designed for testing, QA, and automation practice
