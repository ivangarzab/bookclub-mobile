package com.ivangarzab.bookclub.data.remote.serializers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive

/**
 * Custom serializer for Discord Snowflake IDs (non-nullable).
 * Handles conversion between Long (database/JSON) and String (Kotlin code).
 * Discord Snowflake IDs are 64-bit integers that can lose precision in JavaScript,
 * so we use JsonPrimitive.content to preserve the exact string representation.
 */
object DiscordSnowflakeSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DiscordSnowflake", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        // Convert string back to long for database storage
        encoder.encodeLong(value.toLong())
    }

    override fun deserialize(decoder: Decoder): String {
        // Use JsonDecoder to get the raw content without precision loss
        val jsonDecoder = decoder as? JsonDecoder
        if (jsonDecoder != null) {
            val element = jsonDecoder.decodeJsonElement()
            if (element is JsonPrimitive) {
                // Get the raw string content which preserves large numbers
                return element.content
            }
        }

        // Fallback for non-JSON decoders
        return try {
            decoder.decodeString()
        } catch (e: Exception) {
            decoder.decodeLong().toString()
        }
    }
}

/**
 * Custom serializer for Discord Snowflake IDs (nullable).
 * Handles conversion between Long? (database/JSON) and String? (Kotlin code).
 */
@OptIn(ExperimentalSerializationApi::class)
object NullableDiscordSnowflakeSerializer : KSerializer<String?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("NullableDiscordSnowflake", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            // Convert string back to long for database storage
            encoder.encodeLong(value.toLong())
        }
    }

    override fun deserialize(decoder: Decoder): String? {
        // Use JsonDecoder to get the raw content without precision loss
        val jsonDecoder = decoder as? JsonDecoder
        if (jsonDecoder != null) {
            val element = jsonDecoder.decodeJsonElement()
            if (element is JsonPrimitive) {
                if (element.isString && element.content == "null") {
                    return null
                }
                // Get the raw string content which preserves large numbers
                return element.content
            }
        }

        // Fallback for non-JSON decoders
        return try {
            decoder.decodeString()
        } catch (e: Exception) {
            try {
                decoder.decodeLong().toString()
            } catch (e: Exception) {
                null
            }
        }
    }
}