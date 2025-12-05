import SwiftUI

struct MainView: View {
    @State private var selectedTab = 0

    var body: some View {
        TabView(selection: $selectedTab) {
            ClubsView()
                .tabItem {
                    Label {
                        Text("Clubs")
                    } icon: {
                        Image.custom(.club)
                    }
                }
                .tag(0)

            MeView()
                .tabItem {
                    Label {
                        Text("Me")
                    } icon: {
                        Image.custom(.user)
                    }
                }
                .tag(1)
        }
    }
}

#Preview {
    MainView()
}
