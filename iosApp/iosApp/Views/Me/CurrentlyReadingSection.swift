//
//  CurrentlyReadingSection.swift
//  iosApp
//
//  Created by Ivan Garza Bermea on 12/5/25.
//
import SwiftUI
import Shared


struct CurrentlyReadingSection: View {
    let currentReadings: [Shared.CurrentlyReadingBook]

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("section_currently_reading")
                .font(.headline)
                .foregroundColor(.secondary)

            if currentReadings.isEmpty {
                NoSectionData(text: String(localized: "empty_no_books_reading"))
            } else {
                ForEach(Array(currentReadings.prefix(3).enumerated()), id: \.offset) { _, reading in
                    CurrentlyReadingItem(reading: reading)
                }

                if currentReadings.count > 3 {
                    Text("label_and_more")
                        .font(.body)
                        .italic()
                        .foregroundColor(.secondary)
                }
            }
        }
        .padding()
    }
}

struct CurrentlyReadingItem: View {
    let reading: Shared.CurrentlyReadingBook

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(reading.bookTitle)
                .font(.body)
                .fontWeight(.medium)

            ProgressView(value: Double(reading.progress))
                .progressViewStyle(LinearProgressViewStyle(tint: .brandOrange))
                .frame(height: 8)
        }
    }
}
