package com.exxuslee.domain.usecases

import com.exxuslee.domain.model.Player
import com.exxuslee.domain.repositories.Repository
import com.exxuslee.domain.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UseCaseTest {

    @Mock
    private lateinit var repository: Repository
    private lateinit var useCase: UseCase.Base

    @Before
    fun setUp() {
        useCase = UseCase.Base(repository)
    }

    @Test
    fun useCase_calls_Repository() {
        runTest {
            useCase.getPlayer(1)
            Mockito.verify(repository).getPlayer(1)
        }
    }

    @Test
    fun `return data from repository`() {

        runTest {
            val expected = Result.Success(Player(id = 1, name = "1", level = 1, bonus = 1, sex = true))
            Mockito.`when`(repository.getPlayer(1)).thenReturn(expected)
            val actual = useCase.getPlayer(1)

            Assertions.assertEquals(expected, actual)
        }
    }

    @AfterEach
    fun tearDown(){
        Mockito.reset(repository)
        Mockito.reset(useCase)
    }
}