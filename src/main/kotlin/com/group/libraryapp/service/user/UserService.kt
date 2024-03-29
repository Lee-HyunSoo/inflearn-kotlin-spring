package com.group.libraryapp.service.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.BookHistoryResponse
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.util.fail
import com.group.libraryapp.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
) {

    // 예시
    @Transactional
    fun saveUserAndLoanTwoBooks() {
        val newUser = User("A", 123)
        val books = bookRepository.saveAll(
            listOf(
                Book("책1", BookType.COMPUTER), Book("책2", BookType.COMPUTER)
            )
        )
        books.forEach { book -> newUser.loanBook(book) } // UserLoanHistory 2개가 생긴다.
        userRepository.save(newUser)
    }

    @Transactional
    fun saveUser(request: UserCreateRequest) {
        val user = User(request.name, request.age)
        userRepository.save(user)
    }

    fun getUsers(): List<UserResponse> {
        return userRepository.findAll()
            .map {
//            user -> UserResponse(user)
            user -> UserResponse.of(user)
//            UserResponse(it)
        }
//            .map(::UserResponse) // 생성자 사용
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest) {
//        val user = userRepository.findById(request.id).orElseThrow(::IllegalArgumentException)
//        val user = userRepository.findByIdOrNull(request.id) ?: fail()
        val user = userRepository.findByIdOrThrow(request.id)
        user.updateName(request.name)
    }

    @Transactional
    fun deleteUser(name: String) {
//        val user = userRepository.findByName(name).orElseThrow(::IllegalArgumentException)
//        val user = userRepository.findByName(name) ?: throw IllegalArgumentException()
        val user = userRepository.findByName(name) ?: fail()
        userRepository.delete(user)
    }

    // 유저 대출 현황
    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
//        return userRepository.findAllWithHistories()
//            .map(UserLoanHistoryResponse::of)
        return userRepository.findWithHistories()
            .map(UserLoanHistoryResponse::of)
//        return userRepository.findAllWithHistories().map { user -> // left join
//            UserLoanHistoryResponse(
//                name = user.name,
////                books = user.userLoanHistories.map { history ->
////                    BookHistoryResponse.of(history)
////                }
//                books = user.userLoanHistories.map(BookHistoryResponse::of)
//            )
//        }
    }
}