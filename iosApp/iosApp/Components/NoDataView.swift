import SwiftUI

struct NoTabData: View {
    let text: String

    var body: some View {
        VStack {
            Spacer()
            Text(text)
                .font(.body)
                .foregroundColor(.secondary)
                .italic()
                .multilineTextAlignment(.center)
                .padding()
            Spacer()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct NoSectionData: View {
    let text: String

    var body: some View {
        Text(text)
            .font(.body)
            .foregroundColor(.secondary)
            .italic()
            .padding(.vertical, 8)
    }
}

#Preview("NoTabData") {
    NoTabData(text: "No data available")
}

#Preview("NoSectionData") {
    NoSectionData(text: "No section data")
}
