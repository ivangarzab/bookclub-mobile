import SwiftUI
import Shared

struct MembersTab: View {
    let members: [Shared.MemberListItemInfo]

    var body: some View {
        ScrollView {
            if members.isEmpty {
                NoTabData(text: "No members in club")
            } else {
                VStack(alignment: .leading, spacing: 0) {
                    Text("Members (\(members.count))")
                        .font(.headline)
                        .padding(.bottom, 8)

                    ForEach(Array(members.enumerated()), id: \.offset) { index, member in
                        MemberListItem(member: member)

                        if index < members.count - 1 {
                            Divider()
                                .padding(.vertical, 4)
                        }
                    }
                }
                .padding()
            }
        }
    }
}

// MARK: - Member List Item
struct MemberListItem: View {
    let member: Shared.MemberListItemInfo

    var body: some View {
        HStack(spacing: 12) {
            // Avatar placeholder
            Circle()
                .fill(Color.brandOrange)
                .frame(width: 40, height: 40)

            VStack(alignment: .leading, spacing: 2) {
                Text(member.name)
                    .font(.body)
                    .fontWeight(.medium)

                Text(member.handle)
                    .font(.subheadline)
                    .foregroundColor(.secondary)
            }

            Spacer()

            Text("\(member.points) points")
                .font(.subheadline)
                .foregroundColor(.secondary)
        }
        .padding(.vertical, 12)
    }
}

#Preview {
    MembersTab(members: [])
}
