package com.hekmatullahamin.taskminder.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.hekmatullahamin.taskminder.R

/**
 * A custom typography configuration for the application, based on Google's Material 3 design system,
 * with custom font families for body and display text.
 *
 * This configuration applies the "Roboto" font for body text and the "Poppins" font for display text.
 * These fonts are loaded from Google's font provider service to ensure proper integration across devices.
 *
 * The typography values for each text style (e.g., `displayLarge`, `headlineMedium`, `bodySmall`) are
 * based on the default Material 3 typography, but they have been customized to use the `bodyFontFamily`
 * and `displayFontFamily` for different text categories.
 *
 * @see [GoogleFont.Provider] for font loading configuration.
 * @see [Typography] for Material 3 typography and the typography scaling system.
 *
 * Usage example:
 * ```
 * MaterialTheme(
 *     typography = Typography,
 *     content = { ... }
 * )
 * ```
 */
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

/**
 * The font family used for body text. This uses the "Roboto" font from Google Fonts.
 */
val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Roboto"),
        fontProvider = provider,
    )
)

/**
 * The font family used for display text. This uses the "Poppins" font from Google Fonts.
 */
val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

// Default Material 3 typography values
val baseline = Typography()

/**
 * Custom typography values based on Material 3 design system with the selected font families.
 *
 * Each text style (e.g., `displayLarge`, `bodyLarge`, etc.) uses the custom font families defined
 * above, while maintaining the default Material 3 typography scaling and styles.
 */
val Typography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
)