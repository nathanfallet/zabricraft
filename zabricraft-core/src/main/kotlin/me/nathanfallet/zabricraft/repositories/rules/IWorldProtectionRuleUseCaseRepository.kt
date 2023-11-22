package me.nathanfallet.zabricraft.repositories.rules

import me.nathanfallet.zabricraft.usecases.rules.IWorldProtectionRuleUseCase

interface IWorldProtectionRuleUseCaseRepository {

    fun list(): List<IWorldProtectionRuleUseCase>
    fun add(value: IWorldProtectionRuleUseCase)
    fun clear()

}
