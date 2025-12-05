import SwiftUI
import Shared

struct MeView: View {
    @StateObject private var viewModel = MeViewModelWrapper()
    private let userId = "5cc30117-4f77-462a-9881-dd63f0130a09" // TODO: Get from auth

    var body: some View {
        ZStack {
            if viewModel.isLoading {
                LoadingView()
                    .transition(.opacity)
            } else if let error = viewModel.error {
                ErrorView(message: error, onRetry: {
                    viewModel.loadUserData(userId: userId)
                })
                .transition(.opacity)
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

                            Divider()
                                .padding(.vertical, 8)
                        }

                        CurrentlyReadingSection(currentReadings: viewModel.currentlyReading)

                        Divider()
                            .padding(.vertical, 8)

                        FooterSection()
                    }
                    .padding(16)
                }
                .transition(.opacity)
            }
        }
        .animation(.easeInOut(duration: 0.3), value: viewModel.isLoading)
        .animation(.easeInOut(duration: 0.3), value: viewModel.error)
        .onAppear {
            viewModel.loadUserData(userId: userId)
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

// MARK: - Footer Section
struct FooterSection: View {
    var body: some View {
        VStack(spacing: 0) {
            FooterItem(label: "Settings", icon: .settings)

            Divider()
                .padding(.vertical, 8)

            FooterItem(label: "Help & Support", icon: .help)

            Divider()
                .padding(.vertical, 8)

            HStack {
                Spacer()
                Text("Version 0.0.1") //TODO: Get actual version from KMP
                    .font(.caption)
                    .italic()
                    .foregroundColor(.secondary)
                    .padding(.top, 8)
            }
            .padding(.horizontal, 16)
        }
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
        .padding(.horizontal, 16)
    }
}

#Preview {
    MeView()
}
