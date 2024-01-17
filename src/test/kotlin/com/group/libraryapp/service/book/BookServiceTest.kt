package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalArgumentException

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("책 등록이 정상 동작한다.")
    fun saveBookTest() {
        // given
//        var request = BookRequest("test", "Computer") // 파라미터 추가
        var request = BookRequest("test", BookType.COMPUTER) // String -> Enum 변경

        // when
        bookService.saveBook(request)

        // then
        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo("test")
        assertThat(books[0].type).isEqualTo(BookType.COMPUTER) // 검증 추가
    }

    @Test
    @DisplayName("책 대출이 정상 동작한다.")
    fun loanBookTest() {
        // given
//        bookRepository.save(Book("test")) // 생성자 사용
        bookRepository.save(Book.fixture("test")) // 정적 팩토리 메서드 사용
        val savedUser = userRepository.save(User("test", null))
        val request = BookLoanRequest("test", "test")

        // when
        bookService.loanBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("test")
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
//        assertThat(results[0].isReturn).isEqualTo(false)
        assertThat(results[0].status).isEqualTo(UserLoanStatus.LOANED)
    }

    @Test
    @DisplayName("책이 진작 대출되어있다면, 신규 대출이 실패한다.")
    fun loanBookFailTest() {
        // given
        bookRepository.save(Book.fixture("test"))
        val savedUser = userRepository.save(User("test", null))
//        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "test", false))
        userLoanHistoryRepository.save(UserLoanHistory.fixture(savedUser, "test"))
        val request = BookLoanRequest("test", "test")

        // when & then
        val message = assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.message

        assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
    }

    @Test
    @DisplayName("책 반납이 정상 동작한다.")
    fun returnBookTest() {
        // given
        bookRepository.save(Book.fixture("test"))
        val savedUser = userRepository.save(User("test", null))
        userLoanHistoryRepository.save(UserLoanHistory.fixture(savedUser, "test"))
        val request = BookReturnRequest("test", "test")

        // when
        bookService.returnBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].status).isEqualTo(UserLoanStatus.RETURNED)
    }

    @Test
    @DisplayName("책 대여 권수를 정상 확인한다.")
    fun countLoanBookTest() {
        // given
        val savedUser = userRepository.save(User("test", null))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUser, "A"), // 대출 중인 책
            UserLoanHistory.fixture(savedUser, "B", UserLoanStatus.RETURNED), // 반납한 책
            UserLoanHistory.fixture(savedUser, "C", UserLoanStatus.RETURNED), // 반납한 책
        ))

        // when
        val result = bookService.countLoanBook()

        // then
        assertThat(result).isEqualTo(1)
    }

    @Test
    @DisplayName("분야 별 책 권수를 정상 확인한다.")
    fun getBookStatisticsTest() {
        // given
        bookRepository.saveAll(listOf(
            Book.fixture("A", BookType.COMPUTER),
            Book.fixture("B", BookType.COMPUTER),
            Book.fixture("C", BookType.SCIENCE),
        ))

        // when
        val results = bookService.getBookStatistics()

        // then
        assertThat(results).hasSize(2)
//        val computerDto = results.first { result -> result.type == BookType.COMPUTER }
//        assertThat(computerDto.count).isEqualTo(2)
//        val scienceDto = results.first { result -> result.type == BookType.SCIENCE }
//        assertThat(scienceDto.count).isEqualTo(1)

        assertCount(results, BookType.COMPUTER, 2L)
        assertCount(results, BookType.SCIENCE, 1L)
    }

    private fun assertCount(results: List<BookStatResponse>, type: BookType, count: Long) {
        assertThat(results.first { result -> result.type == type }.count).isEqualTo(count)
    }

}