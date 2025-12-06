import SwiftUI

struct NextDiscussionCard: View {
    let title: String
    let location: String
    let formattedDate: String

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(title)
                .font(.body)
                .fontWeight(.medium)

            HStack(spacing: 2) {
                Image.custom(.location)
                    .font(.caption)
                    .foregroundColor(.secondary)
                Text(location)
                    .font(.subheadline)
                    .foregroundColor(.secondary)
            }

            Text(formattedDate)
                .font(.subheadline)
                .foregroundColor(.secondary)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(12)
        .background(Color.brandOrange.opacity(0.2))
        .overlay(
            RoundedRectangle(cornerRadius: 4)
                .stroke(Color.brandOrange, lineWidth: 1)
        )
    }
}

#Preview {
    NextDiscussionCard(
        title: "Discussion #1",
        location: "Discord",
        formattedDate: "Tomorrow at 7:00 PM"
    )
    .padding()
}
