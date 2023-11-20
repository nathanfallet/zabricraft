package me.nathanfallet.zabricraft.usecases.rules

class GetWorldProtectionRulesUseCase : IGetWorldProtectionRulesUseCase {

    private val usecases: MutableList<IWorldProtectionRuleUseCase> = mutableListOf()

    override fun invoke(): MutableList<IWorldProtectionRuleUseCase> {
        return usecases
    }

    override fun clear() {
        usecases.clear()
    }

}
