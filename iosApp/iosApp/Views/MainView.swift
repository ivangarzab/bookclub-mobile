import SwiftUI

struct MainView: View {
    @State private var selectedTab = 0

    var body: some View {
        VStack(spacing: 0) {
            // Content area
            Group {
                if selectedTab == 0 {
                    ClubsView()
                } else {
                    MeView()
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)

            MaterialBottomNavBar(selectedTab: $selectedTab)
        }
        .ignoresSafeArea(edges: .bottom)
    }
}

#Preview {
    MainView()
}
