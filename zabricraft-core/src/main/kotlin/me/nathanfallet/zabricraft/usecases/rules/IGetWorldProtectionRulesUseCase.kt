package me.nathanfallet.zabricraft.usecases.rules

import me.nathanfallet.usecases.base.IUnitUseCase

interface IGetWorldProtectionRulesUseCase : IUnitUseCase<MutableList<IWorldProtectionRuleUseCase>> {

    fun clear()

}
