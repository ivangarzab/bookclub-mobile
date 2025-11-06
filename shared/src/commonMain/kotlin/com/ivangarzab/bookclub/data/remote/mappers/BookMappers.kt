package com.ivangarzab.bookclub.data.remote.mappers

import com.ivangarzab.bookclub.data.remote.dtos.BookDto
import com.ivangarzab.bookclub.domain.models.Book

/**
 * Maps a [BookDto] from the API to a [Book] domain model.
 */
fun BookDto.toDomain(): Book {
    return Book(
        id = id,
        title = title,
        author = author,
        edition = edition,
        year = year,
        isbn = isbn
    )
}
