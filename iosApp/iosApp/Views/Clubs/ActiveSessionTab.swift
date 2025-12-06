import SwiftUI
import Shared

struct ActiveSessionTab: View {
    let sessionDetails: Shared.ActiveSessionDetails?

    var body: some View {
        ScrollView {
            if let session = sessionDetails {
                VStack(spacing: 12) {
                    // Session Book Card
                    VStack(alignment: .leading, spacing: 8) {
                        Text(session.book.title)
                            .font(.headline)

                        Text(String(format: NSLocalizedString("label_by_author", comment: ""), session.book.author))
                            .font(.subheadline)
                            .foregroundColor(.secondary)

                        HStack {
                            Text("label_due_date")
                                .fontWeight(.medium)
                            Text(session.dueDate)
                        }
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding()

                    Divider()

                    // Discussion Timeline
                    VStack(alignment: .leading, spacing: 0) {
                        Text("section_discussion_timeline")
                            .font(.headline)
                            .padding(.bottom, 8)

                        ForEach(Array(session.discussions.enumerated()), id: \.offset) { index, discussion in
                            DiscussionTimelineItem(
                                discussion: discussion,
                                isFirst: index == 0,
                                isLast: index == session.discussions.count - 1
                            )
                        }
                    }
                    .padding()
                }
                .padding()
            } else {
                NoTabData(text: String(localized: "empty_no_session_details"))
            }
        }
    }
}

// MARK: - Discussion Timeline Item
struct DiscussionTimelineItem: View {
    let discussion: Shared.DiscussionTimelineItemInfo
    let isFirst: Bool
    let isLast: Bool

    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            // Timeline indicator
            VStack(spacing: 0) {
                // Top line
                if !isFirst {
                    Rectangle()
                        .fill(Color.gray.opacity(0.3))
                        .frame(width: 2, height: 40)
                } else {
                    Spacer()
                        .frame(height: 40)
                }

                // Circle indicator
                ZStack {
                    Circle()
                        .fill(discussion.isPast || discussion.isNext ? Color.brandOrange.opacity(discussion.isPast ? 0.75 : 1.0) : Color.gray.opacity(0.3))
                        .frame(width: 24, height: 24)

                    if discussion.isPast {
                        Image.custom(.checkmark)
                            .font(.system(size: 8, weight: .bold))
                            .foregroundColor(Color(UIColor.systemBackground))
                    }
                }

                // Bottom line
                if !isLast {
                    Rectangle()
                        .fill(Color.gray.opacity(0.3))
                        .frame(width: 2, height: 60)
                } else {
                    Spacer()
                        .frame(height: 60)
                }
            }
            .frame(width: 32)

            // Discussion content
            VStack(alignment: .leading, spacing: 8) {
                if discussion.isNext {
                    NextDiscussionCard(
                        title: discussion.title,
                        location: discussion.location,
                        formattedDate: discussion.date
                    )
                } else {
                    VStack(alignment: .leading, spacing: 4) {
                        Text(discussion.title)
                            .font(.body)
                            .fontWeight(.medium)
                            .opacity(discussion.isPast ? 0.5 : 1.0)

                        HStack(spacing: 2) {
                            Image.custom(.location)
                                .font(.caption)
                            Text(discussion.location)
                                .font(.subheadline)
                        }
                        .foregroundColor(.secondary)
                        .opacity(discussion.isPast ? 0.5 : 1.0)

                        Text(discussion.date)
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                            .opacity(discussion.isPast ? 0.5 : 1.0)
                    }
                    .padding(.vertical, 12)
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
        }
    }
}

#Preview {
    ActiveSessionTab(sessionDetails: nil)
}
