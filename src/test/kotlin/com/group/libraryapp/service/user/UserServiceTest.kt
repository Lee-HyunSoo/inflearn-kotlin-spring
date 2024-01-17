package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun clean() {
        println("clean 시작")
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저 저장이 정상 동작한다.")
    fun saveUserTest() {
        // given
        val request = UserCreateRequest("test", null)

        // when
        userService.saveUser(request)

        // then
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("test")
        // java 의 getAge 호출 -> 코틀린은 이게 null 이 들어갈 수 있는지 없는지 판단하지 못한다. (플랫폼 타입)
        // 가져올 때 일단 null 이 아니겠지 하고 가져오지만 값이 현재 null 이기 때문에 예외가 터진다.
        // 자바 쪽 User 클래스에 @Nullable 을 붙여 해결할 수 있다.
        assertThat(results[0].age).isNull() // NPE
    }

    @Test
    @DisplayName("유저 조회가 정상 동작한다.")
    fun getUsersTest() {
        // given
        userRepository.saveAll(listOf(
            User("A", 20),
            User("B", null),
            ))
        // when
        val results = userService.getUsers()

        // then
        assertThat(results).hasSize(2)
        assertThat(results)
            .extracting("name") // ["A", "B"]
            .containsExactlyInAnyOrder("A", "B") // 순서 상관 x
        assertThat(results)
            .extracting("age") // [20, null]
            .containsExactlyInAnyOrder(20, null) // 순서 상관 x
    }

    @Test
    @DisplayName("유저 수정이 정상 동작한다.")
    fun updateUserNameTest() {
        // given
        val savedUser = userRepository.save(User("A", null))
        val request = UserUpdateRequest(savedUser.id!!, "B")

        // when
        userService.updateUserName(request)

        // then
        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")
    }

    @Test
    @DisplayName("유저 삭제가 정상 동작한다.")
    fun deleteUserTest() {
        // given
        val savedUser = userRepository.save(User("A", null))

        // when
        userService.deleteUser("A")
        
        // then
        assertThat(userRepository.findAll()).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다.")
    fun getUserLoanHistoriesTest1() {
        // given
        userRepository.save(User("test", null))

        // when
        val results = userService.getUserLoanHistories()

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("test")
        assertThat(results[0].books).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 많은 유저의 응답이 정상 동작한다.")
    fun getUserLoanHistoriesTest2() {
        // given
        val savedUser = userRepository.save(User("test", null))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser, "책2", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser, "책3", UserLoanStatus.RETURNED),
        ))

        // when
        val results = userService.getUserLoanHistories()

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("test")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("책1", "책2", "책3")
        assertThat(results[0].books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)
    }

    @Test
    @DisplayName("방금 두 경우가 합쳐진 테스트")
    fun getUserLoanHistoriesTest3() {
        // given
        val savedUsers = userRepository.saveAll(listOf(
            User("A", null),
            User("B", null),
        ))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUsers[0], "책1", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUsers[0], "책2", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUsers[0], "책3", UserLoanStatus.RETURNED),
        ))

        // when
        val results = userService.getUserLoanHistories()

        // then
        assertThat(results).hasSize(2)

        val userAResult = results.first { it.name == "A" } // user 이름이 A 인 user 를 가져온다.
        assertThat(userAResult.books).hasSize(3)
        assertThat(userAResult.books).extracting("name")
            .containsExactlyInAnyOrder("책1", "책2", "책3")
        assertThat(userAResult.books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)

        val userBResult = results.first { it.name == "B" }
        assertThat(userBResult.books).isEmpty()
    }
}