# 📚 Kluvs

> A Kotlin Multiplatform mobile application for managing book clubs and reading sessions across Discord communities.

[![Full Tests](https://github.com/ivangarzab/bookclub-mobile/actions/workflows/integration-tests.yml/badge.svg)](https://github.com/ivangarzab/bookclub-mobile/actions/workflows/integration-tests.yml)
[![codecov](https://codecov.io/gh/ivangarzab/bookclub-mobile/branch/main/graph/badge.svg)](https://codecov.io/gh/ivangarzab/bookclub-mobile)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-blue.svg?logo=kotlin)](https://kotlinlang.org)

## 🎯 About

**Kluvs** mobile is a companion app for Discord-based book clubs, allowing members to track their reading progress, manage sessions, and engage with their community on the go.

## ✨ Features

- 📖 **Book Club Management** - Create and join book clubs
- 👥 **Member Profiles** - Track participation
- 📅 **Session Tracking** - Keep up with reading schedules and discussions
- 🌐 **Cross-Platform** - Native apps for Android and iOS
- 🤖 **Companion Bot** - Discord companion bot available 
- 🔄 **Real-time Sync** - Powered by Supabase for live updates

## 🏗️ Tech Stack

- **Kotlin Multiplatform** - Shared business logic across platforms
- **Compose Multiplatform** - Modern declarative UI
- **Supabase** - Backend-as-a-Service for data and real-time features
- **Ktor** - Networking and API communication
- **Koin** - Dependency injection
- **barK** - Logging strategy for KMP
- **Kover** - Code coverage
- **Mokkery** - Testing framework

## 📂 Project Structure

```
bookclub-mobile/
├── composeApp/       # Compose Multiplatform UI code
│   ├── commonMain/   # Shared UI components
│   ├── androidMain/  # Android-specific code
│   └── iosMain/      # iOS-specific code
├── shared/           # Shared business logic
│   ├── commonMain/   # Core domain & data layers
│   ├── androidMain/  # Android-specific implementations
│   └── iosMain/      # iOS-specific implementations
└── iosApp/           # iOS application entry point
```

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (latest stable)
- **Xcode** 15+ (for iOS development)
- **JDK** 17+
- **Kotlin** 2.2.0+

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/ivangarzab/bookclub-mobile.git
   cd bookclub-mobile
   ```

2. **Configure Supabase credentials**

   Create a `gradle.properties` file in your home directory (`~/.gradle/gradle.properties`) or in the project root:
   ```properties
   SUPABASE_URL=your_supabase_url
   SUPABASE_KEY=your_supabase_anon_key
   TEST_SUPABASE_URL=your_test_supabase_url
   TEST_SUPABASE_KEY=your_test_supabase_anon_key
   ```

3. **Run the Android app**
   ```bash
   ./gradlew :composeApp:assembleDebug
   ```

4. **Run the iOS app**
   - Open `iosApp/iosApp.xcodeproj` in Xcode
   - Select your target device/simulator
   - Press Run (⌘R)

## 🧪 Testing

### Run Unit Tests
```bash
./gradlew shared:testDebugUnitTest
```

### Run Integration Tests (requires local Supabase)
```bash
./gradlew shared:testDebugUnitTest --tests "*IntegrationTest"
```

### Generate Coverage Report
```bash
./gradlew shared:koverHtmlReport
```
Reports are generated in `shared/build/reports/kover/html/`

## 🔄 CI/CD

The project uses GitHub Actions for continuous integration:

- **Unit Tests** - Fast feedback on every PR
- **Full Tests Suite** - Full test suite with Supabase on push to `main`
- **Code Coverage** - Tracked via Codecov

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Ivan Garza**

- GitHub: [@ivangarzab](https://github.com/ivangarzab)

## 🙏 Acknowledgments

- Built with [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- Powered by [Supabase](https://supabase.com)
- Backend API: [bookclub-api](https://github.com/ivangarzab/bookclub-api)

---

<p align="center">Made with ❤️ using Kotlin Multiplatform</p>
