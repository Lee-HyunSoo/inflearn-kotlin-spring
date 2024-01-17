package com.group.libraryapp.domain.user.loanhistory

import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory, Long> {

    fun findByBookName(bookName: String): UserLoanHistory?
    
    fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus): UserLoanHistory?

    // 현재 대여 중인 책
    fun findAllByStatus(status: UserLoanStatus): List<UserLoanHistory>

    // 현재 대여 중인 책
    fun countByStatus(status: UserLoanStatus): Long
}