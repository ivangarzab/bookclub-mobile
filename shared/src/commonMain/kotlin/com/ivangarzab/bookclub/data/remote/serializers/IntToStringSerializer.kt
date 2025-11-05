package com.ivangarzab.bookclub.data.remote.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Custom serializer for auto-increment integer IDs (non-nullable).
 * Handles conversion between Int (database/JSON) and String (Kotlin code).
 */
object IntToStringSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("IntToString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        // Convert string back to int for database storage
        encoder.encodeInt(value.toInt())
    }

    override fun deserialize(decoder: Decoder): String {
        return try {
            // Try to decode as int (from database) and convert to string
            decoder.decodeInt().toString()
        } catch (e: Exception) {
            // Fallback: try to decode as string
            decoder.decodeString()
        }
    }
}

/**
 * Custom serializer for auto-increment integer IDs (nullable).
 * Handles conversion between Int? (database/JSON) and String? (Kotlin code).
 */
object NullableIntToStringSerializer : KSerializer<String?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("NullableIntToString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            // Convert string back to int for database storage
            encoder.encodeInt(value.toInt())
        }
    }

    override fun deserialize(decoder: Decoder): String? {
        return try {
            // Try to decode as int (from database) and convert to string
            decoder.decodeInt().toString()
        } catch (e: Exception) {
            // Fallback: try to decode as string
            try {
                decoder.decodeString()
            } catch (e: Exception) {
                null
            }
        }
    }
}
