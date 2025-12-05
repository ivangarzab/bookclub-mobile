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
            Text("Your Statistics")
                .font(.headline)
                .foregroundColor(.secondary)

            StatisticsItem(
                icon: .clubs,
                label: "Number of Clubs",
                value: statistics.clubsCount > 0 ? "\(statistics.clubsCount)" : "N/A"
            )

            StatisticsItem(
                icon: .points,
                label: "Points",
                value: statistics.totalPoints > 0 ? "\(statistics.totalPoints)" : "N/A"
            )

            StatisticsItem(
                icon: .book,
                label: "Books Read",
                value: statistics.booksRead > 0 ? "\(statistics.booksRead)" : "N/A"
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
