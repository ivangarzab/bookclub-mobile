//
//  StatisticsSection.swift
//  iosApp
//
//  Created by Ivan Garza Bermea on 12/5/25.
//
import SwiftUI
import Shared

struct StatisticsSection: View {
    let statistics: Shared.UserStatistics

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("section_your_statistics")
                .font(.headline)
                .foregroundColor(.secondary)

            StatisticsItem(
                icon: .clubs,
                label: String(localized: "stat_number_of_clubs"),
                value: statistics.clubsCount > 0 ? "\(statistics.clubsCount)" : String(localized: "label_not_available")
            )

            StatisticsItem(
                icon: .points,
                label: String(localized: "stat_points"),
                value: statistics.totalPoints > 0 ? "\(statistics.totalPoints)" : String(localized: "label_not_available")
            )

            StatisticsItem(
                icon: .book,
                label: String(localized: "stat_books_read"),
                value: statistics.booksRead > 0 ? "\(statistics.booksRead)" : String(localized: "label_not_available")
            )
        }
        .padding()
    }
}

struct StatisticsItem: View {
    let icon: CustomIcon
    let label: String
    let value: String

    var body: some View {
        HStack(spacing: 4) {
            Image.custom(icon)
                .font(.system(size: 24))
                .foregroundColor(.brandOrange)
                .frame(width: 28, height: 28)

            VStack(alignment: .leading, spacing: 2) {
                Text(label)
                    .font(.subheadline)
                    .foregroundColor(.secondary)

                Text(value)
                    .font(.body)
                    .fontWeight(.medium)
            }

            Spacer()
        }
    }
}
