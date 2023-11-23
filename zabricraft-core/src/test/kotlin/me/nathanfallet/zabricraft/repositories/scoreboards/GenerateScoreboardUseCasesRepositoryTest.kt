package me.nathanfallet.zabricraft.repositories.scoreboards

import io.mockk.mockk
import me.nathanfallet.zabricraft.usecases.scoreboards.IGenerateScoreboardUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

class GenerateScoreboardUseCasesRepositoryTest {

    @Test
    fun testListEmpty() {
        val repository = GenerateScoreboardUseCasesRepository()
        assertEquals(listOf(), repository.list())
    }

    @Test
    fun testAdd() {
        val repository = GenerateScoreboardUseCasesRepository()
        val useCase = mockk<IGenerateScoreboardUseCase>()
        repository.add(useCase)
        assertEquals(listOf(useCase), repository.list())
    }

    @Test
    fun testClear() {
        val repository = GenerateScoreboardUseCasesRepository()
        val useCase = mockk<IGenerateScoreboardUseCase>()
        repository.add(useCase)
        repository.clear()
        assertEquals(listOf(), repository.list())
    }

}
