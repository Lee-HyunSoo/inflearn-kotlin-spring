package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus

data class UserLoanHistoryResponse(
    val name: String, // 유저 이름
    val books: List<BookHistoryResponse>
) {
    // 정적 팩토리 메서드 추가
    companion object {
        fun of(user: User): UserLoanHistoryResponse {
            return UserLoanHistoryResponse(
                name = user.name,
                books = user.userLoanHistories.map(BookHistoryResponse::of)
            )
        }
    }
}

data class BookHistoryResponse(
    val name: String, // 책의 이름
    val isReturn: Boolean, // 반납 여부
) {
    // 정적 팩토리 메서드 추가
    companion object {
        fun of(history: UserLoanHistory): BookHistoryResponse {
            return BookHistoryResponse(
                name = history.bookName,
//                isReturn = history.status == UserLoanStatus.RETURNED
                isReturn = history.isReturn // custom getter 사용
            )
        }
    }
}

