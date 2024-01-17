package com.group.libraryapp.domain.book

import javax.persistence.*

@Entity
class Book(
    val name: String,

    @Enumerated(EnumType.STRING)
    val type: BookType, // 기존엔 String

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어있을 수 없습니")
        }
    }

    // == test 를 위한 정적 팩토리 메서드 ==
    companion object {
        fun fixture(
            name: String = "책 이름",
            type: BookType = BookType.COMPUTER,
            id: Long? = null,
        ): Book {
            return Book(
                name = name,
                type = type,
                id = id,
            )
        }
    }
}