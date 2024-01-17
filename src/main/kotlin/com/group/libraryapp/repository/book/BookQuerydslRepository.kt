package com.group.libraryapp.repository.book

import com.group.libraryapp.domain.book.QBook.book
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class BookQuerydslRepository(
    private val queryFactory: JPAQueryFactory,
) {
    fun getStats(): List<BookStatResponse> {
        return queryFactory.select(
            Projections.constructor( // 주어진 dto 의 생성자 호출
            BookStatResponse::class.java, // 어떤 dto 인지
            book.type, // 파라미터 1
            book.id.count(), // 파라미터 2
        ))
            .from(book)
            .groupBy(book.type)
            .fetch()
    }
}