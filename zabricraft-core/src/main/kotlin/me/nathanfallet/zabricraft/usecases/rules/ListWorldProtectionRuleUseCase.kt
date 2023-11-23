package me.nathanfallet.zabricraft.usecases.rules

import me.nathanfallet.zabricraft.repositories.rules.IWorldProtectionRuleUseCaseRepository

class ListWorldProtectionRuleUseCase(
    private val repository: IWorldProtectionRuleUseCaseRepository
) : IListWorldProtectionRuleUseCase {

    override fun invoke(): List<IWorldProtectionRuleUseCase> {
        return repository.list()
    }

}
