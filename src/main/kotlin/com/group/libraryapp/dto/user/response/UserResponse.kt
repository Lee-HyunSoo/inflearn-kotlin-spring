package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User

data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int?
) {

    // 정적 팩토리 메서드 활용
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                id = user.id!!,
                name = user.name,
                age = user.age
            )
        }
    }

    // 부 생성자 활용
//    constructor(user: User): this(
//        id = user.id!!, // null 아님 단언
//        name = user.name,
//        age = user.age
//    )
}
