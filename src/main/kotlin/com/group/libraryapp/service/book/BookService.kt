package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookService(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    @Transactional
    fun saveBook(request: BookRequest) {
        val newBook = Book(request.name, request.type)
        bookRepository.save(newBook)
    }

    @Transactional
    fun loanBook(request: BookLoanRequest) {
//        val book = bookRepository.findByName(request.bookName).orElseThrow(::IllegalArgumentException)
//        val book = bookRepository.findByName(request.bookName) ?: throw IllegalArgumentException()
        val book = bookRepository.findByName(request.bookName) ?: fail()

        if (userLoanHistoryRepository.findByBookNameAndStatus(request.bookName, UserLoanStatus.LOANED) != null) {
            throw IllegalArgumentException("진작 대출되어 있는 책입니다")
        }

//        val user = userRepository.findByName(request.userName).orElseThrow(::IllegalArgumentException)
        val user = userRepository.findByName(request.userName) ?: throw IllegalArgumentException()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest) {
//        val user = userRepository.findByName(request.userName).orElseThrow(::IllegalArgumentException)
//        val user = userRepository.findByName(request.userName) ?: throw IllegalArgumentException()
        val user = userRepository.findByName(request.userName) ?: fail()
        user.returnBook(request.bookName)
    }

    // 현재 대여 중인 책
//    fun countLoanBook(): Int {
//        return userLoanHistoryRepository.findAllByStatus(UserLoanStatus.LOANED).size
//    }

    // 현재 대여 중인 책
    fun countLoanBook(): Int {
        return userLoanHistoryRepository.countByStatus(UserLoanStatus.LOANED).toInt()
    }

    // 분야 별 등록되어 있는 책의 권 수
    fun getBookStatistics(): List<BookStatResponse> {
//        val results = mutableListOf<BookStatResponse>() // 응답

//        val books = bookRepository.findAll()
//        for (book in books) {
            // book.type 을 가진 dto 찾음
            // == 1번 solution ==
//            val target = results.firstOrNull {dto -> book.type == dto.type }
//            if (targetDto == null) {
//                results.add(BookStatResponse(book.type, 1)) // 없으면 새 dto 생성, count 1
//            } else {
//                targetDto.plusOne() // 있다면 기존 dto 의 count++
//            }

            // == 2번 solution ==
//            val targetDto = results.firstOrNull { dto -> book.type == dto.type }?.plusOne() // ?. : 값이 null 이 아닌 경우 실행
//                ?: results.add(BookStatResponse(book.type, 1)) // ?: null 인 경우 실행

//        }
//        return results

        // == 3번 solution ==
//        return bookRepository.findAll() // List<Book>
//                .groupBy { book -> book.type } // Map<BookType, List<Book>>
//                .map { (type, books) -> BookStatResponse(type, books.size)} // List<BookStatResponse>

        // == 4번 solution ==
        return bookRepository.getStats()
    }
}