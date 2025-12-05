import SwiftUI
import UIKit

extension Color {
    // Brand colors with automatic dark mode support from Asset Catalog
    // These reference color assets in Assets.xcassets with light/dark variants
    static let brandOrange = Color("BrandOrange")
    static let brandGreen = Color("BrandGreen")
    static let brandBlue = Color("BrandBlue")

    // Fallback hex-based colors (for when asset catalog colors aren't set up yet)
    static let brandOrangeFallback = Color(hex: 0xD16D30)
    static let brandGreenFallback = Color(hex: 0x48A480)
    static let brandBlueFallback = Color(hex: 0x006781)
}

// Helper extensions
extension UIColor {
    convenience init(hex: UInt, alpha: CGFloat = 1.0) {
        self.init(
            red: CGFloat((hex >> 16) & 0xFF) / 255.0,
            green: CGFloat((hex >> 8) & 0xFF) / 255.0,
            blue: CGFloat(hex & 0xFF) / 255.0,
            alpha: alpha
        )
    }
}

extension Color {
    init(hex: UInt, alpha: Double = 1.0) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xFF) / 255.0,
            green: Double((hex >> 8) & 0xFF) / 255.0,
            blue: Double(hex & 0xFF) / 255.0,
            opacity: alpha
        )
    }
}
