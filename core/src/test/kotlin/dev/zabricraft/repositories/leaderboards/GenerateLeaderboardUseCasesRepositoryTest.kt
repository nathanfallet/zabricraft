package dev.zabricraft.repositories.leaderboards

import dev.zabricraft.usecases.leaderboards.IGenerateLeaderboardUseCase
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class GenerateLeaderboardUseCasesRepositoryTest {

    @Test
    fun testListEmpty() {
        val repository = GenerateLeaderboardUseCasesRepository()
        assertEquals(mapOf(), repository.list())
    }

    @Test
    fun testGetNonExists() {
        val repository = GenerateLeaderboardUseCasesRepository()
        assertEquals(null, repository.get("key"))
    }

    @Test
    fun testSet() {
        val repository = GenerateLeaderboardUseCasesRepository()
        val useCase = mockk<IGenerateLeaderboardUseCase>()
        repository.set("key", useCase)
        assertEquals(mapOf("key" to useCase), repository.list())
        assertEquals(useCase, repository.get("key"))
    }

    @Test
    fun testClear() {
        val repository = GenerateLeaderboardUseCasesRepository()
        val useCase = mockk<IGenerateLeaderboardUseCase>()
        repository.set("key", useCase)
        repository.clear()
        assertEquals(mapOf(), repository.list())
    }

}
