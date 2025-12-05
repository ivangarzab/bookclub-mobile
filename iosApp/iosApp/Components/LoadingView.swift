import SwiftUI

struct LoadingView: View {
    var body: some View {
        VStack {
            Spacer()
            HStack {
                Spacer()
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle())
                    .scaleEffect(1.5)
                Spacer()
            }
            Spacer()
        }
    }
}

#Preview {
    LoadingView()
}
