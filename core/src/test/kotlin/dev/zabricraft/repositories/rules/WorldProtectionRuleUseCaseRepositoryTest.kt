package dev.zabricraft.repositories.rules

import dev.zabricraft.usecases.rules.IWorldProtectionRuleUseCase
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class WorldProtectionRuleUseCaseRepositoryTest {

    @Test
    fun testListEmpty() {
        val repository = WorldProtectionRuleUseCaseRepository()
        assertEquals(listOf(), repository.list())
    }

    @Test
    fun testAdd() {
        val repository = WorldProtectionRuleUseCaseRepository()
        val useCase = mockk<IWorldProtectionRuleUseCase>()
        repository.add(useCase)
        assertEquals(listOf(useCase), repository.list())
    }

    @Test
    fun testClear() {
        val repository = WorldProtectionRuleUseCaseRepository()
        val useCase = mockk<IWorldProtectionRuleUseCase>()
        repository.add(useCase)
        repository.clear()
        assertEquals(listOf(), repository.list())
    }

}
