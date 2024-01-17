package com.group.libraryapp.util

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TxHelper {

    @Transactional
    fun exec(block: () -> Unit) {
        block() // 어떤 함수를 받아서, 그 함수를 실행
    }
}