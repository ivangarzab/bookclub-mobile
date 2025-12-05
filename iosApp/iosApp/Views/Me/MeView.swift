import SwiftUI
import Shared

struct MeView: View {
    @StateObject private var viewModel = MeViewModelWrapper()
    private let userId = "5cc30117-4f77-462a-9881-dd63f0130a09" // TODO: Get from auth

    var body: some View {
        NavigationView {
            Group {
                if viewModel.isLoading {
                    LoadingView()
                } else if let error = viewModel.error {
                    ErrorView(message: error, onRetry: {
                        viewModel.loadUserData(userId: userId)
                    })
                } else {
                    ScrollView {
                        VStack(spacing: 0) {
                            if let profile = viewModel.profile {
                                ProfileSection(profile: profile)
                            }

                            Divider()
                                .padding(.vertical, 8)

                            if let statistics = viewModel.statistics {
                                StatisticsSection(statistics: statistics)
                            }

                            Divider()
                                .padding(.vertical, 8)

                            CurrentlyReadingSection(currentReadings: viewModel.currentlyReading)

                            Divider()
                                .padding(.vertical, 8)

                            FooterSection()
                        }
                        .padding(16)
                    }
                }
            }
            .navigationTitle("Me")
            .onAppear {
                viewModel.loadUserData(userId: userId)
            }
        }
    }
}

// MARK: - Profile Section
struct ProfileSection: View {
    let profile: Shared.UserProfile

    var body: some View {
        HStack(alignment: .center, spacing: 16) {
            // Avatar placeholder
            Circle()
                .fill(Color.brandOrange)
                .frame(width: 60, height: 60)

            VStack(alignment: .leading, spacing: 4) {
                Text(profile.name)
                    .font(.body)
                    .fontWeight(.medium)

                Text(profile.handle ?? "")
                    .font(.subheadline)
                    .foregroundColor(.secondary)

                Text("Member since \(profile.joinDate)")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
            }

            Spacer()
        }
        .padding()
    }
}

// MARK: - Statistics Section
struct StatisticsSection: View {
    let statistics: Shared.UserStatistics

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Your Statistics")
                .font(.headline)
                .foregroundColor(.secondary)

            StatisticsItem(
                icon: .clubs,
                label: "No. of Clubs",
                value: statistics.clubsCount > 0 ? "\(statistics.clubsCount)" : "N/A"
            )

            StatisticsItem(
                icon: .points,
                label: "Points",
                value: statistics.totalPoints > 0 ? "\(statistics.totalPoints)" : "N/A"
            )

            StatisticsItem(
                icon: .book,
                label: "Books Read",
                value: statistics.booksRead > 0 ? "\(statistics.booksRead)" : "N/A"
            )
        }
        .padding()
    }
}

struct StatisticsItem: View {
    let icon: CustomIcon
    let label: String
    let value: String

    var body: some View {
        HStack(spacing: 12) {
            Image.custom(icon)
                .font(.system(size: 24))
                .foregroundColor(.brandOrange)
                .frame(width: 28, height: 28)

            VStack(alignment: .leading, spacing: 2) {
                Text(label)
                    .font(.subheadline)
                    .foregroundColor(.secondary)

                Text(value)
                    .font(.body)
                    .fontWeight(.medium)
            }

            Spacer()
        }
    }
}

// MARK: - Currently Reading Section
struct CurrentlyReadingSection: View {
    let currentReadings: [Shared.CurrentlyReadingBook]

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Currently Reading")
                .font(.headline)
                .foregroundColor(.secondary)

            if currentReadings.isEmpty {
                NoSectionData(text: "No books currently reading")
            } else {
                ForEach(Array(currentReadings.prefix(3).enumerated()), id: \.offset) { _, reading in
                    CurrentlyReadingItem(reading: reading)
                }

                if currentReadings.count > 3 {
                    Text("...and more")
                        .font(.body)
                        .italic()
                        .foregroundColor(.secondary)
                }
            }
        }
        .padding()
    }
}

struct CurrentlyReadingItem: View {
    let reading: Shared.CurrentlyReadingBook

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(reading.bookTitle)
                .font(.body)
                .fontWeight(.medium)

            ProgressView(value: Double(reading.progress))
                .progressViewStyle(LinearProgressViewStyle(tint: .brandOrange))
                .frame(height: 8)
        }
    }
}

// MARK: - Footer Section
struct FooterSection: View {
    var body: some View {
        VStack(spacing: 0) {
            FooterItem(label: "Settings", icon: .settings)

            Divider()

            FooterItem(label: "Help & Support", icon: .help)
            
            Divider()

            HStack {
                Spacer()
                Text("Version 0.0.1")
                    .font(.caption)
                    .italic()
                    .foregroundColor(.secondary)
                    .padding(.top, 8)
            }
        }
        .padding()
    }
}

struct FooterItem: View {
    let label: String
    let icon: CustomIcon

    var body: some View {
        HStack(spacing: 12) {
            Image.custom(icon)
                .font(.system(size: 20))
                .foregroundColor(.brandOrange)
                .frame(width: 24, height: 24)

            Text(label)
                .font(.body)
                .foregroundColor(.primary)

            Spacer()
        }
    }
}

#Preview {
    MeView()
}
