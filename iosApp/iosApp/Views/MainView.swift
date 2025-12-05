import SwiftUI

struct MainView: View {
    @State private var selectedTab = 0

    var body: some View {
        TabView(selection: $selectedTab) {
            ClubsView()
                .tabItem {
                    Label("Clubs", systemImage: "book.circle.fill")
                }
                .tag(0)

            MeView()
                .tabItem {
                    Label("Me", systemImage: "person.circle.fill")
                }
                .tag(1)
        }
    }
}

#Preview {
    MainView()
}
