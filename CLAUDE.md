# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Kluvs** is a Kotlin Multiplatform mobile application for managing book clubs and reading sessions across Discord communities. The app uses Compose Multiplatform for UI and Supabase for backend services.

## Build Commands

### Android

```bash
# Build debug APK
./gradlew :composeApp:assembleDebug

# Build release APK
./gradlew :composeApp:assembleRelease

# Install and run on connected device
./gradlew :composeApp:installDebug
```

### iOS

- Open `iosApp/iosApp.xcodeproj` in Xcode
- Select target device/simulator
- Press Run (⌘R)

## Testing

### Run Unit Tests Only

```bash
# Run all unit tests (excludes integration tests)
./gradlew shared:testDebugUnitTest -PexcludeTests="**/*IntegrationTest.class"
```

### Run Integration Tests

Integration tests require a local Supabase instance. The full test suite runs integration tests:

```bash
# Run ALL tests including integration tests
./gradlew shared:testDebugUnitTest
```

**Note**: Integration tests connect to a Supabase instance specified by `TEST_SUPABASE_URL` and `TEST_SUPABASE_KEY` environment variables.

### Run Specific Test

```bash
# Run a single test class
./gradlew shared:testDebugUnitTest --tests "com.ivangarzab.bookclub.data.repositories.ClubRepositoryTest"

# Run a single test method
./gradlew shared:testDebugUnitTest --tests "com.ivangarzab.bookclub.data.repositories.ClubRepositoryTest.testGetClubById"
```

### Code Coverage

```bash
# Generate HTML coverage report
./gradlew shared:koverHtmlReport

# Generate XML coverage report (for CI)
./gradlew shared:koverXmlReport
```

Reports are generated in `shared/build/reports/kover/html/`

## Architecture

### Module Structure

The project is organized into two main modules:

- **`shared/`** - Shared business logic and data layer (Kotlin Multiplatform)
  - Domain models
  - Data repositories
  - Remote data sources and API services
  - Dependency injection setup
- **`composeApp/`** - UI layer with Compose Multiplatform
  - Android-specific UI implementations currently
  - iOS support planned

### Data Layer Architecture

The data layer follows a clean architecture pattern with three layers:

1. **Services** (`data/remote/api/`) - Direct Supabase API communication via Supabase Functions
   - `ServerService`, `ClubService`, `MemberService`, `SessionService`
   - Handle raw API requests/responses

2. **Remote Data Sources** (`data/remote/source/`) - Transform DTOs to domain models
   - Use mappers to convert between DTOs and domain models
   - Handle data source-specific error handling

3. **Repositories** (`data/repositories/`) - Abstract data access for the domain layer
   - Expose clean domain interfaces
   - Currently delegate to remote data sources only
   - Designed to support local data sources in the future for caching/offline support

### Dependency Injection with Koin

All dependency injection is managed through Koin with modular organization:

- **`platformDataModule`** - Platform-specific dependencies (Android/iOS)
  - Android: `shared/src/androidMain/kotlin/com/ivangarzab/bookclub/di/DataModule.android.kt`
  - iOS: `shared/src/iosMain/kotlin/com/ivangarzab/bookclub/di/DataModule.ios.kt`
- **`remoteDataModule`** - API services and remote data sources
- **`repositoryModule`** - Repository implementations

Koin is initialized in `shared/src/commonMain/kotlin/com/ivangarzab/bookclub/di/KoinHelper.kt`

### Configuration Management

Supabase credentials are managed via BuildKonfig:

- Production credentials: `SUPABASE_URL`, `SUPABASE_KEY`
- Test credentials: `TEST_SUPABASE_URL`, `TEST_SUPABASE_KEY`

These must be set in `~/.gradle/gradle.properties` or as environment variables. See `shared/build.gradle.kts:87-122` for the configuration logic.

## Testing Strategy

### Test Organization

- **Unit Tests** - Fast tests with mocked dependencies using Mokkery
  - Mappers, serializers, data sources, repositories
  - Located in `shared/src/commonTest/`

- **Integration Tests** - Tests against real Supabase instance
  - Suffixed with `*IntegrationTest.kt`
  - Require local Supabase instance running
  - Excluded from quick test runs via `excludeTests` property

### Logging in Tests

Tests use barK logging with a custom test rule (`BarkTestRule`) to capture and assert log output. Platform-specific implementations exist for Android and iOS.

## CI/CD

The project uses GitHub Actions:

- **Unit Tests** (`.github/workflows/unit-tests.yml`) - Fast feedback on PRs, excludes integration tests
- **Full Tests** (`.github/workflows/full-tests.yml`) - Runs on `main` branch with local Supabase instance
  - Checks out both `bookclub-mobile` and `bookclub-api` repos
  - Starts local Supabase instance
  - Applies migrations and seed data
  - Runs complete test suite
  - Uploads coverage to Codecov

## Gradle Configuration Notes

### Exclude Tests Dynamically

The `shared/build.gradle.kts` includes a custom task configuration (lines 137-149) that allows excluding tests via property:

```bash
./gradlew shared:testDebugUnitTest -PexcludeTests="**/*IntegrationTest.class"
```

### Kover Exclusions

Code coverage excludes:
- Generated code (`*.BuildConfig`, `*.BuildKonfig`)
- DTOs (`com.ivangarzab.bookclub.data.remote.dtos`)
- Dependency injection modules (`**.di`)