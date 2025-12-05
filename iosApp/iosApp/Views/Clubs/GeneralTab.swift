import SwiftUI
import Shared

struct GeneralTab: View {
    let clubDetails: Shared.ClubDetails?

    var body: some View {
        ScrollView {
            if let clubDetails = clubDetails {
                VStack(spacing: 12) {
                    // Club Info Card
                    VStack(alignment: .leading, spacing: 8) {
                        Text(clubDetails.clubName)
                            .font(.headline)

                        Text("\(clubDetails.memberCount) members")
                            .font(.subheadline)
                            .foregroundColor(.secondary)

                        if let foundedYear = clubDetails.foundedYear {
                            Text("Founded in \(foundedYear)")
                                .font(.subheadline)
                                .foregroundColor(.secondary)
                        }
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding()

                    Divider()

                    // Current Book Card
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Current Book")
                            .font(.headline)
                            .foregroundColor(.secondary)

                        if let book = clubDetails.currentBook {
                            Text(book.title)
                                .font(.body)
                                .fontWeight(.medium)

                            Text(book.author)
                                .font(.subheadline)
                                .foregroundColor(.secondary)

                            HStack(spacing: 4) {
                                Text(book.year ?? "N/A")
                                    .font(.subheadline)
                                    .foregroundColor(.secondary)

                                if let pageCount = book.pageCount {
                                    Text("•")
                                        .foregroundColor(.secondary)
                                    Text("\(pageCount) pages")
                                        .font(.subheadline)
                                        .foregroundColor(.secondary)
                                }
                            }
                        } else {
                            NoSectionData(text: "No book data")
                        }
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding()

                    Divider()

                    // Next Discussion Card
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Next Discussion")
                            .font(.headline)
                            .foregroundColor(.secondary)

                        if let discussion = clubDetails.nextDiscussion {
                            NextDiscussionCard(
                                title: discussion.title,
                                location: discussion.location,
                                formattedDate: discussion.formattedDate
                            )
                        } else {
                            NoSectionData(text: "No upcoming discussion")
                        }
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding()
                }
                .padding()
            } else {
                NoTabData(text: "No club details")
            }
        }
    }
}

#Preview {
    GeneralTab(clubDetails: nil)
}
