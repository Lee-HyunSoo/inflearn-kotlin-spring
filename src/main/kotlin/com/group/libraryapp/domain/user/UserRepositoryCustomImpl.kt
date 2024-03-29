package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.user.QUser.user
import com.querydsl.jpa.impl.JPAQueryFactory

class UserRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : UserRepositoryCustom {

    override fun findWithHistories(): List<User> {
        return queryFactory.select(user).distinct()
            .from(user)
            .leftJoin(user.userLoanHistories)
            .fetchJoin() // 앞의 쿼리를 fetch join 으로 간주한다.
            .fetch()
    }
}