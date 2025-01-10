package dev.zabricraft.repositories.rules

import dev.zabricraft.usecases.rules.IWorldProtectionRuleUseCase

interface IWorldProtectionRuleUseCaseRepository {

    fun list(): List<IWorldProtectionRuleUseCase>
    fun add(value: IWorldProtectionRuleUseCase)
    fun clear()

}
