package me.nathanfallet.zabricraft.repositories.rules

import me.nathanfallet.zabricraft.usecases.rules.IWorldProtectionRuleUseCase

class WorldProtectionRuleUseCaseRepository : IWorldProtectionRuleUseCaseRepository {

    private val usecases: MutableList<IWorldProtectionRuleUseCase> = mutableListOf()

    override fun list(): List<IWorldProtectionRuleUseCase> {
        return usecases
    }

    override fun add(value: IWorldProtectionRuleUseCase) {
        usecases.add(value)
    }

    override fun clear() {
        usecases.clear()
    }

}
