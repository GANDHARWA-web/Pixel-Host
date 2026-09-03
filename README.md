# Pixel Host

Pixel Host is an Android application prototype designed to provide a simple interface for exploring game-server and VPS hosting plans.

The application demonstrates an end-to-end hosting purchase interface including plan selection, server configuration, checkout, payment screens, and purchase history.

> **Note:** Pixel Host is a prototype project. It does not provision or operate real hosting servers.

## Features

- Game server hosting interface
- VPS hosting interface
- Multiple hosting plans
- CPU and server configuration options
- Server location selection
- Checkout workflow
- Payment interface
- Purchase history
- Invoice display
- About Us section
- Android-based responsive UI

## Technologies Used

- Java
- Android Studio
- XML
- Android SDK
- Gradle

## Application Flow

User
↓
Home Screen
↓
Choose Hosting Type
↓
Game Hosting / VPS Hosting
↓
Select Configuration
↓
Choose Hosting Plan
↓
Checkout
↓
Payment
↓
Purchase History

## Project Structure

```text
Pixel-Host/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/pixelhost/
│           │   ├── MainActivity.java
│           │   ├── GameHostingActivity.java
│           │   ├── GamePlansActivity.java
│           │   ├── VpsHostingActivity.java
│           │   ├── VpsPlansActivity.java
│           │   ├── CheckoutActivity.java
│           │   ├── PaymentActivity.java
│           │   ├── HistoryActivity.java
│           │   └── AboutUsActivity.java
│           │
│           ├── res/
│           └── AndroidManifest.xml
│
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/