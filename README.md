[![Run Unit Testing](https://github.com/s2g090123/Nitra/actions/workflows/run_unit_tests.yml/badge.svg)](https://github.com/s2g090123/Nitra/actions/workflows/run_unit_tests.yml)

# Nitra

## Introduction
Nitra is a local credit card management app that allows users to add, view, update, and delete credit cards locally. The app also features an app lock function that requires authentication upon app launch and return to enhance security.

## Features
- Add new credit cards
- Update existing credit cards
- View saved credit cards
- Delete credit cards
- **App Lock**: Requires authentication when launching or returning to the app
- **Smooth Animations**: Enhances user experience with fluid animations
- **Offline Mode Support**: Stores data locally using a database (Card Number and CVV are encrypted)

## Tech Stack
- **Language**: Kotlin
- **Architecture**: MVI
- **Data Storage**: Room, DataStore
- **UI**: Jetpack Compose + Navigation
- **Dependency Injection**: Hilt

## Project Structure
### Directory Structure
This project is divided into multiple submodules based on functionality, offering the following benefits:
- Improved code reusability
- Faster compilation times
- Each module focuses on a single feature, reducing coupling and improving code readability & maintainability
- Independent management of dependencies and resources per module
- New features can be added as separate modules without affecting existing code

```
- app (main module)
- common (shared components, e.g., design system)
- feature (feature submodules)
   - cards (credit card management page)
   - home (home screen)
   - lock (app lock functionality)
   - more (more options screen)
```

### Clean Architecture
![Untitled diagram-2025-03-05-154456](https://github.com/user-attachments/assets/a85adaaf-6930-44db-a852-ec982d5f9e71)

The project follows **Clean Architecture**, structured into three layers: **UI, Domain, and Data**.
- **UI Layer**: Handles the presentation and user interaction without business logic.
- **Domain Layer**: Contains core business logic, independent of UI and Data layers to ensure stability.
- **Data Layer**: Manages data access, including database.

### Dependency Injection
The project uses **Hilt** for dependency injection.
To further improve flexibility and testability, this project follows **Interface-driven Design**:
- **All classes depend on interfaces instead of concrete implementations**.
- **Dependency Injection dynamically injects implementations**, making it easier to swap implementations for different environments (e.g., real database vs. memory database for testing).

### Design System
The project includes a **Design System** to maintain consistency in UI design. It supports:
- **Colors**
- **Sizes**
- **Fonts**

#### Usage
To access design tokens:
```kotlin
// access the color
LocalColors.current.XXX
DSTheme.colors.XXX

// access the size
LocalSizes.current.XXX
DSTheme.sizes.XXX

// access the font
LocalFonts.current.XXX
DSTheme.fonts.XXX
```

For overriding values, use `CompositionLocalProvider`:
```kotlin
CompositionLocalProvider(
  LocalColors provides customColors,
  LocalSizes provides customSizes,
  LocalFonts provides customFonts,
) {
    // Your UI components
}
```

## Testing
The project includes **unit tests** and **UI tests** to ensure stability and correctness.

### Unit Tests
- Verify that `CardValidator` correctly validates card data
- Ensure `ExpiredDateHelper` correctly converts expiration dates
- Test `AESEncryptor` to encrypt and decrypt data correctly

### UI Tests
- Test flow: **Card List → Add Card → Click on Card → Validate Card Data**

## CI
Every pull request triggers CI to run unit tests automatically, ensuring code quality before merging.

## Screen Recording
### Card List
View cards and delete cards by swiping left or right.

https://github.com/user-attachments/assets/7a301ce6-142d-4080-a61f-d40c51a23fcd

### Add Card
Create a new card and automatically validate the input.

https://github.com/user-attachments/assets/86250736-4557-4847-a196-98df82e9339d

### Update Card
View card information, update the card, and automatically validate the input.

https://github.com/user-attachments/assets/258707ac-1dea-4375-bffa-7c5b22855ab3

### App Lock
Require authentication when launching or returning to the app. (The screen turns black when entering the password.)

https://github.com/user-attachments/assets/0fa32659-dca0-49f8-b536-63dc7f14c9c2
