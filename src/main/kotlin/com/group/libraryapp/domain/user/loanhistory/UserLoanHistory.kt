package com.group.libraryapp.domain.user.loanhistory

import com.group.libraryapp.domain.user.User
import javax.persistence.*

@Entity
class UserLoanHistory(
    @ManyToOne
    val user: User,

    val bookName: String,
    var status: UserLoanStatus = UserLoanStatus.LOANED,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    // 추가
    val isReturn: Boolean
        get() = this.status == UserLoanStatus.RETURNED

    fun doReturn() {
        this.status = UserLoanStatus.RETURNED
    }

    // == test 용 정적 팩토리 메서드 ==
    companion object {
        fun fixture(
            user: User,
            bookName: String = "test",
            status: UserLoanStatus = UserLoanStatus.LOANED,
            id: Long? = null,
        ): UserLoanHistory {
            return UserLoanHistory(
                user = user,
                bookName = bookName,
                status = status,
                id = id,
            )
        }
    }
}