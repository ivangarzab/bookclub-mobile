package com.ivangarzab.bookclub.data.remote.dtos.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Serializer for List<Int> to List<String> conversion (non-nullable).
 * Used for shame_list which contains member IDs as integers in the database.
 */
object IntListToStringListSerializer : KSerializer<List<String>> {
    private val intListSerializer = ListSerializer(Int.serializer())

    override val descriptor: SerialDescriptor = intListSerializer.descriptor

    override fun serialize(encoder: Encoder, value: List<String>) {
        val intList = value.map { it.toInt() }
        encoder.encodeSerializableValue(intListSerializer, intList)
    }

    override fun deserialize(decoder: Decoder): List<String> {
        val intList = decoder.decodeSerializableValue(intListSerializer)
        return intList.map { it.toString() }
    }
}

/**
 * Serializer for List<Int>? to List<String>? conversion (nullable).
 */
object NullableIntListToStringListSerializer : KSerializer<List<String>?> {
    private val intListSerializer = ListSerializer(Int.serializer()).nullable

    override val descriptor: SerialDescriptor = intListSerializer.descriptor

    override fun serialize(encoder: Encoder, value: List<String>?) {
        if (value == null) {
            encoder.encodeSerializableValue(intListSerializer, null)
        } else {
            val intList = value.map { it.toInt() }
            encoder.encodeSerializableValue(intListSerializer, intList)
        }
    }

    override fun deserialize(decoder: Decoder): List<String>? {
        val intList = decoder.decodeSerializableValue(intListSerializer)
        return intList?.map { it.toString() }
    }
}
