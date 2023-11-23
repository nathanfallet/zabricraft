package me.nathanfallet.zabricraft.repositories.rules

import io.mockk.mockk
import me.nathanfallet.zabricraft.usecases.rules.IWorldProtectionRuleUseCase
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
