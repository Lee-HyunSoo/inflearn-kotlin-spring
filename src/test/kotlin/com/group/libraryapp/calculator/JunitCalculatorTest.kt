package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class JunitCalculatorTest {

    @Test
    fun assertTest() {
        val isNew = true
        // 주어진 값이 true 인지 검증
        assertThat(isNew).isTrue
        // 주어진 값이 false 인지 검증
//        assertThat(isNew).isFalse

        val people = listOf(Person("A"), Person("B"))
        // 주어진 컬렉션의 크기가 원하는 값인지 검증
        assertThat(people).hasSize(2)
        // 주어진 컬렉션 안의 item 들에서 name 이라는 프로퍼티를 추출, 그 값이 A 와 B 인지 검증 (순서 상관 x)
        assertThat(people).extracting("name").containsExactlyInAnyOrder("A", "B")
        // 주어진 컬렉션 안의 item 들에서 name 이라는 프로퍼티를 추출, 그 값이 A 와 B 인지 검증 (순서 상관 o)
        assertThat(people).extracting("name").containsExactly("A", "B")
    }

    @Test
    fun assertThrowsTest() {
        // function1 함수를 실행했을 때 IllegalArgumentException 이 터지나 검증
//        assertThrows<IllegalArgumentException> {
//            function1()
//        }

        // 메세지를 가져와 예외 메세지를 확인
//        val message = assertThrows<IllegalArgumentException> {
//            function1()
//        }.message
//        assertThat(message).isEqualTo("잘못 된 값이 들어왔습니다.")
    }

    private fun function1() {
        println("잘못 된 값이 들어왔습니다.")
    }

    @Test
    fun addTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.add(3)

        // then
        assertThat(calculator.number).isEqualTo(8) // 단언문
    }

    @Test
    fun minusTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.minus(3)

        // then
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun multiplyTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.multiply(3)

        // then
        assertThat(calculator.number).isEqualTo(15)
    }

    @Test
    fun divideTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.divide(2)

        // then
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun divideExceptionTest() {
        // given
        val calculator = Calculator(5)

        // when & then
//        val message = assertThrows<IllegalArgumentException> {
//            calculator.divide(0)
//        }.message
//        assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")

        // scope function 활용
        assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.apply {
            assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")
        }
    }
}

class Person(
    val name: String
)